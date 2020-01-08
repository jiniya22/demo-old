package me.jiniworld.demo.models.entities;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import me.jiniworld.demo.models.entities.UserRole.RoleType;

public class SecurityUser extends User implements UserDetails {

	private static final long serialVersionUID = 8666468119299100306L;
	
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;
	
	public SecurityUser(User user, Set<UserRole> userRoles) {
		super();
		setId(user.getId());
		setEmail(user.getEmail());
		setName(user.getName());
		setPassword(user.getPassword());
		setDel(user.isDel());
		setUserRoles(userRoles);
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
	}
	
	public Set<RoleType> getRoleTypes() {
		return getUserRoles().stream().map(f -> f.getRoleName()).collect(Collectors.toSet());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getUserRoles();
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

}
