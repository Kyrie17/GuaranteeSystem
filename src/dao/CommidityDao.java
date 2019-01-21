package dao;

import java.util.List;

import model.Commidity;
import model.PersonCommidity;

public interface CommidityDao {
	//输出所有的商品
	public List<Commidity> findAllCommidity();
	
	//查询用户购物车是否已有商品
	public int judgeIfExist(String stu_id,String commidity_id);
	
	//添加至购物车
	public int insertCart(String stu_id,String id,int quantity,String name,double price);
	
	//继续添加物品
	public int addCart(String stu_id,int quantity,String commidity_id);
	
	//查询购物车
	public List<PersonCommidity> getAllPersonCommidity(String stu_id);
	
	//删除一件购物车里的商品
	public int deleteCommidity(String stu_id,String commidity_name);
	
	//清空购物车
	public int deleteChart(String stu_id);
}
