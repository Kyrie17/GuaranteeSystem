package model;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 购物车实体
 */
public class Cart {
	
	//关键字是商品的id，值是商品
	private Map<String ,CartItem> commidityMap=new LinkedHashMap<>();
	
	//代表着购物车的总价
	private double price;
	
	//把购物项(用户传递进来的商品)加入到购物车里去，也应该是购物车的功能
	public void addCommdity(Commidity commidity) {
		
		//获取得到购物项
		CartItem cartItem=commidityMap.get(commidity.getId());
		
		//判断购物车是否存在该购物项，如果不存在
		if(cartItem==null) {
			
			//创建购物项对象
			cartItem=new CartItem();
			
			//将用户传递过来的商品作为购物项
			cartItem.setCommidity(commidity);
			
			//把该购物项的数量置为1
			cartItem.setQuantity(1);
			
			//把购物项加入到购物车去
			commidityMap.put(commidity.getId(), cartItem);
		}else {
			//如果存在该购物项，将购物项的数量+1
			cartItem.setQuantity(cartItem.getQuantity()+1);
		}
	}
	
	//购物车的总价就是所有购物项的价格加起来
	public double getPrice() {
		
		double totalPrice =0;
		for(Map.Entry<String, CartItem> me:commidityMap.entrySet()) {
			
			//得到每个购物项
			CartItem cartItem=me.getValue();
			
			//将每个购物项的钱加起来，就是购物车的总价了
			totalPrice+=cartItem.getPrice();
		}
		return totalPrice;
	}
	
	public void setCommidityMap(Map<String, CartItem> commidityMap) {
		this.commidityMap = commidityMap;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Map<String,CartItem> getCommidityMap(){
		return commidityMap;
	}
	
	
}
