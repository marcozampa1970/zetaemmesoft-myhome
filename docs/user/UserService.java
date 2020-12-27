package com.zetaemmesoft.services.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zetaemmesoft.services.integration.dto.Role;
import com.zetaemmesoft.services.integration.dto.User;
import com.zetaemmesoft.services.integration.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	User user = userRepository.findUser(username);

	if (!user.getUsername().equals(username)) {
	    throw new UsernameNotFoundException("Invalid username or password!");
	}

	return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(username));
    }

    private List<SimpleGrantedAuthority> getAuthority(String username) {
	List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

	List<Role> roles = userRepository.findRoles(username);

	for (Role r : roles) {
	    authorities.add(new SimpleGrantedAuthority(r.getRolename()));
	}

	return authorities;
    }

    public List<User> findAll() {
	List<User> list = new ArrayList<>();
	userRepository.findAll().iterator().forEachRemaining(list::add);
	return list;
    }
}