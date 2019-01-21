package model;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 购物项实体，表示该商品出现了几次
 */
public class CartItem {
	
	private Commidity commidity;
	private int quantity;
	
	//该购物项的价钱等于商品的数量*价格
	private double price;

	public Commidity getCommidity() {
		return commidity;
	}

	public void setCommidity(Commidity commidity) {
		this.commidity = commidity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
}
