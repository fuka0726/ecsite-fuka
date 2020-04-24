package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domein.Item;

/**
 * itemsテーブルを表すリポジトリ.
 * 
 * @author fuka
 *
 */
@Repository
public class ItemRepository {

	/**
	 * Itemオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 商品を全件検索します.
	 * 
	 * @return 商品一覧
	 */
	public List<Item> findAll() {
		String sql = "SELECT id, name, description, price_m, price_l, image_path, deleted FROM items ORDER BY id";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * IDから商品を検索します.
	 * 
	 * @param id ID
	 * @return アイテム情報
	 */
	public Item load(Integer id) {
		String sql = "SELECT id, name, description, price_m, price_l, image_path, deleted"
				+ " FROM items WHERE id = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}

	/**
	 * 商品名を曖昧検索します.
	 * 
	 * @param name
	 * @return 検索された商品の名前.該当商品が存在しない場合は
	 */
	public List<Item> findByLikeName(String name) {
		String sql = "SELECT id, name, description, price_m,price_l,image_path,deleted "
				+ " FROM items WHERE name ilike :name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 安い商品順に並び替え
	 * @param priceM　Mサイズ商品
	 * @return　商品情報を返す
	 */
	public List<Item> orderByLowerMsizePrice() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_m";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 高い商品順に並び替え
	 * @param priceM　Mサイズ商品
	 * @return　商品情報を返す
	 */
	public List<Item> orderByHigherMsizePrice() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_m DESC";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}	
	
	/**
	 * Lサイズの価格が安い順番で取得します.
	 * 
	 * @param priceL Lサイズの価格
	 * @return 商品一覧
	 */
	public List<Item> orderByLowerLsizePrice() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_l";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * Lサイズの価格が高い順番で取得します.
	 * 
	 * @param priceL Lサイズの価格
	 * @return 商品一覧
	 */
	public List<Item> orderByHigherLsizePrice() {
		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items ORDER BY price_l DESC";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
		
		
	}
	
}