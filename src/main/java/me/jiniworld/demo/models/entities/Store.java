package me.jiniworld.demo.models.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Store extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 3321044622977739271L;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_STORE_USER"))
	private User user;
	
	@Column(nullable=false, length = 100)
	private String name;

	@Column(length = 30)
	private String storeBusiness;	
	
	@Builder
	private Store(User user, String name, String storeBusiness) {
		this.user = user;
		this.name = name;
		this.storeBusiness = storeBusiness;
	}
}
