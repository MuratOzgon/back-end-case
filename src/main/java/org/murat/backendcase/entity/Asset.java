package org.murat.backendcase.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
public class Asset {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	@JsonIgnore
	private User user;

	@Column(nullable = false)
	private String assetName;

	@Column(nullable = false)
	private BigDecimal size = BigDecimal.ZERO;

	@Column(nullable = false)
	private BigDecimal usableSize = BigDecimal.ZERO;
	
	public Asset() {
		
	}

	public Asset(User user, String assetName, BigDecimal size, BigDecimal usableSize) {
		super();
		this.user = user;
		this.assetName = assetName;
		this.size = size;
		this.usableSize = usableSize;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}

	public BigDecimal getUsableSize() {
		return usableSize;
	}

	public void setUsableSize(BigDecimal usableSize) {
		this.usableSize = usableSize;
	}

}