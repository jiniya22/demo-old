package me.jiniworld.demo.models.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@Entity(name = "store")
@DynamicUpdate
public class Store implements Serializable{
	
	private static final long serialVersionUID = 3321044622977739271L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false, columnDefinition = "INT(11)")
	private Long id;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_STORE_USER"))
	private User user;
	
	@Column(nullable=false, length = 100)
	private String name;

	@Column(length = 30)
	private String storeBusiness;
	
	@Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0", length = 1)
	private boolean del;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date createTimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true, columnDefinition = "TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
	protected Date updateTimestamp;	
	
	@Builder
	private Store(User user, String name, String storeBusiness) {
		this.user = user;
		this.name = name;
		this.storeBusiness = storeBusiness;
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
