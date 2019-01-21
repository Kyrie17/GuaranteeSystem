package tools;

import java.util.List;

import dao.RepairFormDao;
import dao.RepairFormDaoImpl;
import dao.RepairManDao;
import dao.RepairManDaoImpl;
import model.RepairForm;
import model.RepairMan;

public class AutomaticAssignUtil {

	/**
	 * 自动分配维修人员
	 */
	public static String automaticAssign(int serType) {
		RepairFormDao repairFormDao = new RepairFormDaoImpl();
		RepairManDao repairManDao = new RepairManDaoImpl();
		
		
		//分配维修工
		List<RepairForm> list =  repairFormDao.getRepairMan(serType);	//维修单中出现同报修类型的维修工
		List<RepairMan> list2 = repairManDao.getRepairMan(serType);		//维修团队出现同报修类型的维修工
		String repairMan = "";
		
		if(list.size() < list2.size()) {	//如果报修单中没有报修类型的记录
			int index = 0;	//记录报修单中没有的维修工
			for(int i = 0; i < list2.size(); i++) {
				boolean flag = false;
				for(int j = 0; j < list.size(); j++) {
					if(list2.get(i).getUsername().equals(list.get(j).getRepairMan())) {
						flag = true;
					}
				}
				if(flag == false) {
					repairMan = list2.get(i).getUsername();
				}
			}
		}else {
			
			int[] array = new int[10];
			for(int i = 0; i < list.size(); i++) {
				String repairman = list.get(i).getRepairMan();
				array[i] = repairFormDao.getRepairManNum(repairman);
			}
			int min = 100000;
			int index = 0;
			for(int i = 0; i < list.size(); i++) {
				if(array[i] < min) {
					min = array[i];
					index = i;
				}
			}
			repairMan = list.get(index).getRepairMan();
		
		}
				
		return repairMan;
		
	}
	
}
