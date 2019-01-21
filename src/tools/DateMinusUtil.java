package tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMinusUtil {

	public static long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
		Date date = null; // 定义时间类型
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			System.out.println("inVal = " + inVal);
			date = inputFormat.parse(inVal); // 将字符型转换成日期型
		} catch (Exception e) {
		e.printStackTrace();
		}
		return date.getTime(); // 返回毫秒数
		}
	
	
}
