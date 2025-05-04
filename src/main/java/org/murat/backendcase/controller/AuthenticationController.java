package org.murat.backendcase.controller;

import org.murat.backendcase.dto.LoginResponse;
import org.murat.backendcase.dto.LoginUserDto;
import org.murat.backendcase.dto.RegisterUserDto;
import org.murat.backendcase.dto.RegisterUserResponseDto;
import org.murat.backendcase.entity.User;
import org.murat.backendcase.security.JwtService;
import org.murat.backendcase.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	private final JwtService jwtService;

	private final AuthenticationService authenticationService;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/signup")
	public ResponseEntity<RegisterUserResponseDto> register(@RequestBody RegisterUserDto registerUserDto) {
		User registeredUser = authenticationService.signup(registerUserDto);
		RegisterUserResponseDto registerUserResponse = new RegisterUserResponseDto(registeredUser.getId(),
				registeredUser.getFullName(), registeredUser.getEmail(), registeredUser.getPassword(),
				registeredUser.getRole(), registeredUser.isCredentialsNonExpired(),
				registeredUser.isAccountNonExpired(), registeredUser.isAccountNonLocked(), registeredUser.getUsername(),
				registeredUser.isEnabled());

		return ResponseEntity.ok(registerUserResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
		User authenticatedUser = authenticationService.authenticate(loginUserDto);

		String jwtToken = jwtService.generateToken(authenticatedUser);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());

		return ResponseEntity.ok(loginResponse);
	}
}