package org.pureacc.betcentral.infra.security.web;

import static java.util.Collections.emptyList;

import java.util.Collection;

import org.pureacc.betcentral.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserDetailsImpl implements UserDetails {
	private final String username;
	private final String password;
	private final long id;

	UserDetailsImpl(User user) {
		this.username = user.getUsername()
				.getValue();
		this.password = "{bcrypt}" + new BCryptPasswordEncoder().encode("password");
		this.id = user.getId()
				.getValue();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return emptyList();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public long getId() {
		return id;
	}
}
