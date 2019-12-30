package me.jiniworld.demo.models.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser extends User implements UserDetails {

	private static final long serialVersionUID = 8666468119299100306L;
	
	private List<GrantedAuthority> userRoles;
	
	public SecurityUser(User user, List<UserRole> userRoles) {
		super();
		setId(user.getId());
		setEmail(user.getEmail());
		setName(user.getName());
		setPassword(user.getPassword());
		setDel(user.isDel());
		this.userRoles = convertGrantedAuthorities(userRoles);
	}
	
	private List<GrantedAuthority> convertGrantedAuthorities(List<UserRole> roles){
		List<GrantedAuthority> userRoles = new ArrayList<>();
		roles.forEach(role -> userRoles.add(new SimpleGrantedAuthority(role.getRoleName().name())));
		return userRoles;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.userRoles;
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !isDel();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isDel();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !isDel();
	}

	@Override
	public boolean isEnabled() {
		return !isDel();
	}

}
