package com.zetaemmesoft.myhome.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderGenerator {

    public static void main(String[] args) {

	int i = 0;
	while (i < 5) {
	    String password = "guest";
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String hashedPassword = passwordEncoder.encode(password);

	    System.out.println(hashedPassword);
	    i++;
	}
    }
}