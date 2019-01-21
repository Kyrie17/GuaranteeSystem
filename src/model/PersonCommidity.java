package model;

import java.util.Map;

public class PersonCommidity {
	//商品名
	private String commidity_name;
	//数量
	private int quantity;
	//单价
	private double price;
	
	public PersonCommidity(Map<String, Object> map){
		this.commidity_name = (String)map.get("commidity_name");
		this.quantity = (int)map.get("quantity");
		this.price = (double)map.get("price");
	}
	
	public String getCommidity_name() {
		return commidity_name;
	}
	public void setCommidity_name(String commidity_name) {
		this.commidity_name = commidity_name;
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
