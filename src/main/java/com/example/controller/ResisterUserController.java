package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.UserRegisterForm;
import com.example.service.ResisterUserService;

/**
 * @author fuka
 *
 */
@Controller
@RequestMapping("")
public class ResisterUserController {
	
	@ModelAttribute
	public UserRegisterForm setUpForm() {
		return new UserRegisterForm();
	
}
	@Autowired
	private ResisterUserService service;
	
	
	@RequestMapping("/show-register")
	public String showRegister() {
		return "register_user";
	}
	
	
	
}
