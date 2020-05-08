package com.example.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domein.Order;
import com.example.repository.JoinOrderRepository;

/**
 * 注文履歴を表示するサービスクラス.
 * @author fuka
 *
 */
@Service
public class JoinShowOrderHistoryService {

	@Autowired
	private JoinOrderRepository repository;
	
	/**
	 * 
	 * ユーザーIDに紐付く注文履歴を表示します.
	 * 
	 * @param userId　ユーザーID
	 * @return 注文情報リスト
	 */
	public List<Order> showOrderHistory(Integer userId){
		List<Order> orderList = new ArrayList<>();
		orderList = repository.findByUserIdAndStatus(userId, 2);
		return orderList;
	}
	
	
	
	
}
