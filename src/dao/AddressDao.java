package dao;

import java.util.List;

import model.Area;
import model.Build;

public interface AddressDao {
	//获取所有区域名
	public List<Area> getAllArea();
	
	//获取栋数
	public List<Build> getAllBuildByArea(int code);
}
