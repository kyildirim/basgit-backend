package today.printandgo.basgit.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import today.printandgo.basgit.model.Role;
import today.printandgo.basgit.model.User;
import today.printandgo.basgit.repository.RoleRepository;
import today.printandgo.basgit.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        for(Role r : roleRepository.findAll()) {
        	if(r.getName().equals("ROLE_" + user.getRoleName().toUpperCase())) {
        		Set<Role> tmp = new HashSet<Role>();
        		tmp.add(r);
        		user.setRoles(tmp);
        	} 	
        }
        //user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
