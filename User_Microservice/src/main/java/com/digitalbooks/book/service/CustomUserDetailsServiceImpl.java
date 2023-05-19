package com.digitalbooks.book.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digitalbooks.book.entity.Role;
import com.digitalbooks.book.entity.User;
import com.digitalbooks.book.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	public CustomUserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(usernameOrEmail).orElseThrow(
				() -> new UsernameNotFoundException("User not found with email:" + usernameOrEmail));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRole()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String string) {
		ArrayList<String> a=new ArrayList<String>();
		a.add(string);
		return a.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}
}
