package org.murat.backendcase.dto;

import java.math.BigDecimal;

public class AssetRequestDto {

	private Long customerId;

	private String assetName;

	private BigDecimal size = BigDecimal.ZERO;

	private BigDecimal usableSize = BigDecimal.ZERO;

	public AssetRequestDto(Long customerId, String assetName, BigDecimal size, BigDecimal usableSize) {
		super();
		this.customerId = customerId;
		this.assetName = assetName;
		this.size = size;
		this.usableSize = usableSize;
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
