package me.jiniworld.demo.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "user_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_name"})})
@DynamicUpdate
public class UserRole extends BaseEntity implements Serializable, GrantedAuthority {
	
	private static final long serialVersionUID = 7943607393308984161L;
	
	@JsonManagedReference
	@JsonIgnoreProperties({"userRoles", "createTimestamp", "updateTimestamp", "del"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ROLE_USER"))
	private User user;
	
	@Column(name="role_name", nullable=false, length = 20)
	@Enumerated(EnumType.STRING)
	private RoleType roleName;
	
	public enum RoleType {
		ROLE_ADMIN, ROLE_VIEW
	}
	
	@Override
	public String getAuthority() {
		return this.roleName.name();
	}
	
}
