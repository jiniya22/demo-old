package me.jiniworld.demo.models.entities;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser extends User implements UserDetails {

	private static final long serialVersionUID = 8666468119299100306L;
	
	public SecurityUser(User user, List<GrantedAuthority> userRoles) {
		super();
		setId(user.getId());
		setEmail(user.getEmail());
		setName(user.getName());
		setPassword(user.getPassword());
		setDel(user.isDel());
		setUserRoles(userRoles);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getUserRoles();
	}
	
	public List<String> getAuthorityNames() {
		return getUserRoles().stream().map(f -> f.getAuthority()).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
