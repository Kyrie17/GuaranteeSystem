package model;

import java.util.Date;
import java.util.Map;

/*
 * 公告栏
 */
public class NoticeInform {
	//上传者
	private String upLoader;
	//文件路径
	private String filePath;
	//文件名称
	private String fileName;
	//文件下载数
	private int downLoadNum;
	//文件上传日期
	private String upLoadDate;

	
	public NoticeInform(Map<String, Object> map){
		this.upLoader = (String)map.get("UpLoader");
		this.filePath = (String)map.get("FilePath");
		this.fileName = (String)map.get("FileName");
		this.downLoadNum = (int)map.get("DownLoadNum");
		this.upLoadDate=(String)map.get("UpLoadDate");
	}
	
	public NoticeInform() {
		// TODO Auto-generated constructor stub
	}

	public String getUpLoader() {
		return upLoader;
	}
	public void setUpLoader(String upLoader) {
		this.upLoader = upLoader;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getDownLoadNum() {
		return downLoadNum;
	}
	public void setDownLoadNum(int downLoadNum) {
		this.downLoadNum = downLoadNum;
	}
	public String getUpLoadDate() {
		return upLoadDate;
	}
	public void setUpLoadDate(String upLoadDate) {
		this.upLoadDate = upLoadDate;
	}

	
	
	
}
