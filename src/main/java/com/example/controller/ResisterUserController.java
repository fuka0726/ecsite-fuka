package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domein.User;
import com.example.form.UserRegisterForm;
import com.example.service.ResisterUserService;

/**
 * ユーザー登録の情報制御をするコントローラーです.
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
	
	
	/**
	 * ユーザー登録画面に遷移します. 
	 * @return　ユーザー登録画面
	 */
	@RequestMapping("/show-register")
	public String showRegister() {
		return "register_user";
	}
	
	
	/**
	 * 入力した値をフォームで受け取り、そこからユーザードメインを作成してDBに新規作成する.
	 * @param form ユーザー登録のフォーム
	 * @param result
	 * @return パスワードと確認用パスワードが一致していればログイン画面。
	 * そうでなければ、登録画面。
	 */
	@RequestMapping("/register-user")
	public String resisterUser(@Validated UserRegisterForm form, BindingResult result) {
		if(!(form.getPassword().equals(form.getConfirmPassword()))) {
			System.out.println(form.getName());
		//パスワード確認
		result.rejectValue("password", "", "パスワードが一致してません" );
		result.rejectValue("confirmPassword", "", "");
		}
		
		//メールアドレスが重複している場合の処理
		if (service.findByMail(form.getEmail()) != null) {
			result.rejectValue("email", "", "そのメールアドレスは既に登録されています");
		}
		
		//エラーがある場合は登録画面に遷移
		if (result.hasErrors()) {
			return showRegister();
		}
		
		User user = new User();
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setZipcode(form.getZipcode());
		user.setAddress(form.getAddress());
		user.setTelephone(form.getTelephone());
		user.setPassword(form.getPassword());
		service.insert(user);

		return "login";
	}
	
	
	
}
