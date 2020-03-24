package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domein.Item;
import com.example.domein.LoginUser;
import com.example.service.ShowItemListService;

@Controller
@RequestMapping("")
public class ShowItemListController {
	
	@Autowired
	private ShowItemListService showItemListService;
	
	@RequestMapping("/showItemList")
	public String showItemList(String searchName,Model model,@AuthenticationPrincipal LoginUser loginUser) {
		List<Item> itemList = showItemListService.showItemList();
	
		if (searchName != null) {
			itemList = showItemListService.searchByName(searchName);	
			model.addAttribute("itemList", itemList);
		}
		if(itemList.size() == 0) {
				itemList = showItemListService.showItemList();
				model.addAttribute("errormessage", "該当する商品がありません");
			}				
		model.addAttribute("itemList", itemList);
		return "item_list_coffee";
	}
		
}
