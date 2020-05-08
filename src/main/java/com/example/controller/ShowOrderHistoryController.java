package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domein.LoginUser;
import com.example.domein.Order;
import com.example.service.JoinShowOrderHistoryService;
import java.util.List;

/**
 * 注文履歴を表示するコントローラー.
 * @author fuka
 *
 */

@Controller
@RequestMapping("")
public class ShowOrderHistoryController {
	
	@Autowired
	private JoinShowOrderHistoryService service;
	
	/**
	 * 注文履歴を表示します.
	 * @param model リクエストスコープ
	 * @param loginUser　ユーザーID
	 * @return　注文一覧画面
	 */
	@RequestMapping("/show-order-history")
	public String showOrderHistory(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		//管理者ユーザーのログインIDを渡す
		List<Order> orderList = service.showOrderHistory(loginUser.getUser().getId());
		if (orderList.size() == 0) {
			model.addAttribute("message", "注文履歴がありません");
		}
		model.addAttribute("orderList", orderList);
		return "order_history";
	
	}
	
	
	
	
	
}
