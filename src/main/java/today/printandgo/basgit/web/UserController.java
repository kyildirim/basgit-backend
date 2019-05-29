package today.printandgo.basgit.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import today.printandgo.basgit.generator.HMACGenerator;
import today.printandgo.basgit.generator.HashGenerator;
import today.printandgo.basgit.generator.PassportGenerator;
import today.printandgo.basgit.model.User;
import today.printandgo.basgit.repository.UserRepository;
import today.printandgo.basgit.service.SecurityService;
import today.printandgo.basgit.service.UserService;
import today.printandgo.basgit.validator.UserValidator;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    private String serverRoot = new File("").getAbsolutePath()+"/";
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Bad username/password combination.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
    	model.addAttribute("user", userRepository.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
        return "welcome";
    }
    
    @RequestMapping(value = "/passport", method = RequestMethod.GET)
    public ResponseEntity<byte[]> mypassports(Model model) {
    	try {
    		PassportGenerator gen = new PassportGenerator();
    		User u = userRepository.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    		String uuid = gen.generatePDF(u);
    		if(uuid!=null) {
    			u.setHaspassport(true);
    			u.setCurrentpassport(uuid);
    			userRepository.save(u);
    			byte[] contents = Files.readAllBytes(Paths.get(serverRoot + "Passports/"+u.getUsername()+"_"+u.getCurrentpassport()+".pdf"));
    			HttpHeaders headers = new HttpHeaders();
    		    headers.setContentType(MediaType.parseMediaType("application/pdf"));
    		    headers.setContentDispositionFormData("Passport.pdf", "Passport.pdf");
    		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    		    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
    		    return response;
    		} else {
    			return null;
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public ResponseEntity<byte []> photo(){
    	User u = userRepository.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		try {
			byte[] imageBytes;
			InputStream in = new BufferedInputStream(new FileInputStream(serverRoot + "Photos/" + u.getUsername() + ".jpeg"));
			imageBytes = IOUtils.toByteArray(in);
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.parseMediaType("image/jpeg"));
	    	headers.setContentLength(imageBytes.length);
		    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

	    	ResponseEntity<byte[]> response = new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
	    	return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    @RequestMapping(value = "/me", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String me() {
    	try {
			return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(userRepository.findByUsername((((UserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUsername())));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    @RequestMapping(value = "/currentpassport", method = RequestMethod.GET)
    @ResponseBody
    public String currentPassport() {
    	return userRepository.findByUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).getCurrentpassport();
    }
    
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public ResponseEntity<byte[]> readPassport(@RequestParam("uuid") String uuid) {
    	try {
    		User u = userRepository.findByCurrentpassport(uuid);
    		byte[] contents = Files.readAllBytes(Paths.get(serverRoot + "Passports/"+u.getUsername()+"_"+u.getCurrentpassport()+".pdf"));
    		HttpHeaders headers = new HttpHeaders();
    	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
    	    headers.setContentDispositionFormData("Passport.pdf", "Passport.pdf");
    	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
    	    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
    	    return response;
    	}catch(IOException e) {
    		return null;
    	}
		
    }
    
    @Deprecated
    @RequestMapping(value = "/readhmac", method = RequestMethod.GET)
    @ResponseBody
    public String readPassportHMAC(@RequestParam("uuid") String uuid) {
    	try {
    		User u = userRepository.findByCurrentpassport(uuid);
    		byte[] contents = Files.readAllBytes(Paths.get(serverRoot + "Passports/"+u.getUsername()+"_"+u.getCurrentpassport()+".pdf"));
    		return HMACGenerator.generateHMAC(contents, "BasGit");
    	} catch (IOException e) {
    		return "";
    	}
    }
    
    @RequestMapping(value = "/readhash", method = RequestMethod.GET)
    @ResponseBody
    public String readPassportHash(@RequestParam("uuid") String uuid) {
    	try {
    		User u = userRepository.findByCurrentpassport(uuid);
    		byte[] contents = Files.readAllBytes(Paths.get(serverRoot + "Passports/"+u.getUsername()+"_"+u.getCurrentpassport()+".pdf"));
    		byte[] hash = HashGenerator.generateHash(contents);
    		System.out.println(HashGenerator.bytesToHex(hash));
    		return HashGenerator.encryptHash(hash);
    	} catch (IOException e) {
    		return "";
    	}
    }
    
    @RequestMapping(value = "/video", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView video() {
    	return new ModelAndView("redirect:https://www.youtube.com/watch?v=Wtf_N1wvKDU");
    }
}
