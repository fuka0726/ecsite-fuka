package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domein.User;
import com.example.repository.UserRepository;

@Service
@Transactional
public class ResisterUserService {
	
	@Autowired
	private UserRepository repository;
	
	public User findByMail(String email) {
		return repository.findByMail(email);
	}
	
	
	
	
}
