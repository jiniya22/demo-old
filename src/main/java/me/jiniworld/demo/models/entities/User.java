package me.jiniworld.demo.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Entity
@Table(name = "user")
@DynamicUpdate @DynamicInsert
public class User extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -563329217866858622L;
	
	@Column(nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT '0'")
	private String type;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT '1'")
	private String sex;
	
	@Column(nullable = false, length = 6)
	private String birthDate;
	
	@Column(nullable = false, length = 20)
	private String phoneNumber;
	
	@Column(nullable = false, length = 150)
	private String password;
	
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
