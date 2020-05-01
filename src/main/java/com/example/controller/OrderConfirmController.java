package com.example.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domein.LoginUser;
import com.example.domein.Order;
import com.example.domein.User;
import com.example.form.OrderForm;
import com.example.service.OrderConfirmService;
import com.example.service.ShoppingCartService;
import com.example.service.UserDetailServiceImpl;

/**
 * 注文前の商品を表示するコントローラー.
 * @author fuka
 *
 */
@Controller
@RequestMapping("")
public class OrderConfirmController {
	
	@Autowired
	private OrderConfirmService orderConfirmService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@ModelAttribute
	public OrderForm SetUpForm() {
		return new OrderForm();
	}
	
	
	/**
	 *注文前の商品を取得し、注文確認ページに画面遷移
	 * 
	 * @param userId　ユーザーid
	 * @param model リクエストスコープ
	 * @return　注文確認ページに画面遷移
	 */
	@RequestMapping("/orderConfirm")
	public String orderConfirm(Integer userId, Model model, @AuthenticationPrincipal LoginUser loginUser) {
		List<Order> orderList = orderConfirmService.showOrderList(loginUser.getUser().getId());
		User user = userDetailServiceImpl.findByUserId(loginUser.getUser().getId());
		model.addAttribute("orderList",orderList);
		model.addAttribute("user",user);
		return "order_confirm";
	}
	
	@RequestMapping("/completeOrder")
	public String completeOrder(@Validated OrderForm form, BindingResult resultset, Integer userId, Model model, 
			@AuthenticationPrincipal LoginUser loginUser) {
		System.out.println(form);
		LocalDateTime localDateTime = null;
		LocalDateTime timeLimit = null;
		
		//日付が入力されていない場合
		if("".equals(form.getDeliveryDate())) {
			FieldError deliveryDateError = new FieldError(resultset.getObjectName(), "deliveryDate", "配達日が未入力です");
			resultset.addError(deliveryDateError);
			//日付が入力されている場合は、注文時間の12時間後の日時が代入された変数を作成する(配達時間のルール設定のため)
		} else {
			//配達日をSQL用のTimeStamp型に変更
			Date date = Date.valueOf(form.getDeliveryDate());
			LocalDate localDate = date.toLocalDate();  //date型にしている
			LocalTime localTime = LocalTime.of(Integer.parseInt(form.getDeliveryHour()), 00);
			//localDateTimeは発注日時
			localDateTime = LocalDateTime.of(localDate, localTime);
			timeLimit = LocalDateTime.now();
			timeLimit = timeLimit.plusHours(12);
		}
		
		//日付が入っているかつ、配達時間を注文日時の12時間以内に設定したらエラーが発生
		if(!"".equals(form.getDeliveryDate()) && timeLimit.isAfter(localDateTime)) {
			FieldError deliveryHourError = new FieldError(resultset.getObjectName(), "deliveryHour",
					"配達時間注文日時より12時間以上後のお時間帯をお選びください");
			resultset.addError(deliveryHourError);
		}
		
		//エラーがある場合は注文確認画面に遷移
		if (resultset.hasErrors()) {
			return orderConfirm(userId, model, loginUser);
		}
		
		//データベースの総額をアップデートするために、カートリストの商品一覧を呼ぶsql実行
		Order ordered = shoppingCartService.showCartList(loginUser.getUser().getId());
		Order order = new Order();
		BeanUtils.copyProperties(form, order);
		//注文日をセット(localDateTimeは72行目からのif文前で生成している)
		Timestamp timeStamp = Timestamp.valueOf(localDateTime);
		order.setDeliveryTime(timeStamp);
		//ユーザーidをセット
		order.setUserId(loginUser.getUser().getId());
		//総額をセット
		order.setTotalPrice(ordered.getCalcTotalPrice() + ordered.getTax());
		//支払方法によって入金情報を変更
		if (order.getPaymentMethod() == 1) {
			order.setStatus(1);
		} else {
			order.setStatus(2);
		}
		//カード決済用のオブジェクト生成
		
		
		
		
		//注文情報を更新する
		orderConfirmService.updateStatus(order);
		
		return "redirect:/tocomplete";
		
	}
	
	//注文完了画面を表示する
	@RequestMapping("/tocomplete")
	public String tocomplete() {
		return "order_finished";
	}
	
	
}
	




