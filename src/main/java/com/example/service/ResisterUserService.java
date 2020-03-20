package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domein.User;
import com.example.repository.UserRepository;

@Service
@Transactional
public class ResisterUserService {
	
	@Autowired
	private UserRepository repository;
	
	/**
	 * パスワードハッシュ化用のクラスをインスタンス化
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//パスワードをハッシュ化(encode)してドメインへ保存
	public void insert(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repository.insert(user);
	}
	
	/**
	 * emailでuser情報を検索します.
	 * @param email Eメール
	 * @return　User情報
	 */
	public User findByMail(String email) {
		return repository.findByMail(email);
	}
	
	
	
	
	
}
