package me.jiniworld.demo.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity(name = "user_role")
@DynamicUpdate
public class UserRole extends BaseEntity implements Serializable, GrantedAuthority {
	
	private static final long serialVersionUID = 7943607393308984161L;
	
	@Column(name="user_id", nullable=false, columnDefinition="INT(11)")
	private long userId;
	
	@Column(name="role_name", nullable=false, length = 20)
	@Enumerated(EnumType.STRING)
	private RoleType roleName;
	
	public enum RoleType {
		ROLE_ADMIN, ROLE_OWNER, ROLE_SWAGGER, ROLE_BASIC, ROLE_VIEW
	}
	
	public UserRole() {
		super();
	}
	public UserRole(long userId, RoleType roleName) {
		this();
		this.userId = userId;
		this.roleName = roleName;
	}

	@Override
	public String getAuthority() {
		return this.getRoleName().name();
	}
}
