package org.murat.backendcase.dto;

import java.math.BigDecimal;

import org.murat.backendcase.entity.Side;

public class OrderRequest {
	private Long customerId;
	private String assetName;
	private Side side;
	private BigDecimal size;
	private BigDecimal price;
	

	public OrderRequest(Long customerId, String assetName, Side side, BigDecimal size, BigDecimal price) {
		super();
		this.customerId = customerId;
		this.assetName = assetName;
		this.side = side;
		this.size = size;
		this.price = price;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}