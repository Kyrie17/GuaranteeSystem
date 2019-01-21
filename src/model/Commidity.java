package model;

import java.util.Map;

/*
 * 商品实体
 */
public class Commidity {
	//商品编号
	private String id;
	//商品名称
	private String name;
	//商品描述
	private String description;
	//商品价格
	private double price;
	
	public Commidity() {
		
	}
	public Commidity(Map<String, Object> map){
		this.id = (String)map.get("commidity_id");
		this.name = (String)map.get("name");
		this.description = (String)map.get("description");
		this.price = (double)map.get("price");
	}
	
	public Commidity(String id,String name,String description,double price) {
		this.id=id;
		this.name=name;
		this.description=description;
		this.price=price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
