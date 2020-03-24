package com.example.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domein.OrderItem;

@Repository
public class OrderItemRepository {

	private final static RowMapper<OrderItem> ORDER_ITEM_ROWMAPPER = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setQuantity(rs.getInt("quantity"));
		orderItem.setSize(rs.getString("size").toCharArray()[0]);
		return orderItem;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert insert;
	
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	/**
	 * オーダーアイテム情報をDB上に挿入します.
	 * 
	 * @param orderItem オーダーアイテム情報
	 * @return オーダーアイテム情報
	 */
	public OrderItem insert(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		Number key = insert.executeAndReturnKey(param);
		orderItem.setId(key.intValue());
		return orderItem;
	}
	
	/**
	 * 注文IDから該当する注文アイテム（商品の概要と注文内容）を取得します.
	 * 
	 * @param orderId
	 * @return 注文アイテムリスト
	 */
	public List<OrderItem> findListByOrderId(Integer orderId) {
		String sql = "SELECT id, item_id, order_id, quantity, size FROM order_items WHERE order_id = :orderId ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		List<OrderItem> orderItemList = template.query(sql, param, ORDER_ITEM_ROWMAPPER);
		return orderItemList;
	}
}
