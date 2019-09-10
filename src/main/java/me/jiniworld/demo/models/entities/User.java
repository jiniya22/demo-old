package me.jiniworld.demo.models.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@DynamicUpdate
@DynamicInsert
public class User implements Serializable {
	
	private static final long serialVersionUID = -563329217866858622L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
	private Long id;
	
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
	
	@Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0", length = 1)
	private boolean del;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createTimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true, columnDefinition = "TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
	protected Date updateTimestamp;
	
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
	
	@PrePersist
	protected void onCreate() {
		createTimestamp = Timestamp.valueOf(LocalDateTime.now());
	}
	
	@PreUpdate
	protected void onUpdate() {
		updateTimestamp = Timestamp.valueOf(LocalDateTime.now());
	}
	
}
