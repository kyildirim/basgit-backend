package today.printandgo.basgit.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {
	@JsonIgnore
	private Long id;
	private String username;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private String passwordConfirm;
	private String nationalid;
	private String firstname;
	private String lastname;
	private Date dob;
	private String cc;
	private String sex;
	@JsonIgnore
	private boolean haspassport;
	@JsonIgnore
	private boolean isrevoked;
	private String pob;
	@JsonIgnore
	private String currentpassport;
	@Transient
	private String roleName;

	@JsonIgnore
	@Transient
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@JsonIgnore
	private Set<Role> roles;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getNationalid() {
		return nationalid;
	}

	public void setNationalid(String nationalid) {
		this.nationalid = nationalid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public boolean isHaspassport() {
		return haspassport;
	}

	public void setHaspassport(boolean haspassport) {
		this.haspassport = haspassport;
	}

	public boolean isIsrevoked() {
		return isrevoked;
	}

	public void setIsrevoked(boolean isrevoked) {
		this.isrevoked = isrevoked;
	}
	
	@JsonIgnore
	@Transient
	public String getUserStatus() {
		return isIsrevoked()?"#ff3333":isHaspassport()?"#33ff33":"#ffcc00";
	}
	
	@JsonIgnore
	@Transient
	public String getUserStatusText() {
		return isIsrevoked()?"Passport Revoked":isHaspassport()?"Passport Issued":"Passport Not Issued";
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public String getCurrentpassport() {
		return currentpassport;
	}

	public void setCurrentpassport(String currentpassport) {
		this.currentpassport = currentpassport;
	}
}
