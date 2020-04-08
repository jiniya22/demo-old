package me.jiniworld.demo.models.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Entity
@Table(name = "user")
@DynamicUpdate @DynamicInsert
public class User extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -563329217866858622L;
	
	@ColumnDefault(value = "0")
	@Column(nullable = false, length = 1, columnDefinition = "CHAR(1)")
	private String type;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@ColumnDefault(value = "1")
	@Column(nullable = false, length = 1, columnDefinition = "CHAR(1)")
	private String sex;
	
	@Column(nullable = false, length = 6)
	private String birthDate;
	
	@Column(nullable = false, length = 20)
	private String phoneNumber;
	
	@JsonIgnore
	@Column(nullable = false, length = 150)
	private String password;
	
	@Singular("userRoles")
	@JsonIgnoreProperties({"createTimestamp", "updateTimestamp", "del"})
	@JsonManagedReference
	@OneToMany(mappedBy="user")
	@Where(clause = "del = false")
	private Set<UserRole> userRoles;
	
	@Builder
	public User(String type, String name, String email, String sex, String birthDate, String phoneNumber, String password) {
		this.type = type;
		this.name = name;
		this.email = email;
		this.sex = sex;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}
	
}
