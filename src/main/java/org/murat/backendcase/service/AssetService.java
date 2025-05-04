package org.murat.backendcase.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.murat.backendcase.entity.Asset;
import org.murat.backendcase.entity.User;
import org.murat.backendcase.entity.UserRole;
import org.murat.backendcase.exception.CustomerNotFoundException;
import org.murat.backendcase.exceptions.InvalidUserRoleException;
import org.murat.backendcase.repository.AssetRepository;
import org.murat.backendcase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetService {
	@Autowired
	private AssetRepository assetRepository;

	@Autowired
	private UserRepository userRepository;

	public List<Asset> getAssetsByCustomerId(Long customerId) {
		return assetRepository.findByCustomerId(customerId);
	}

	public Optional<Asset> getAssetByCustomerIdAndAssetName(Long customerId, String assetName) {
		return assetRepository.findByCustomerIdAndAssetName(customerId, assetName);
	}

	public Asset createAsset(Long customerId, String assetName, BigDecimal size, BigDecimal usableSize) {
		User user = userRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException(customerId));

		if (!user.getRole().equals(UserRole.CUSTOMER)) {
			throw new InvalidUserRoleException("Assets can only be created for customers.");
		}

		Asset asset = new Asset(user, assetName, size, usableSize);
		return assetRepository.save(asset);
	}

}