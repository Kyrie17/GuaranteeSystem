package dao;

import java.util.List;

import model.NoticeInform;
import model.Pager;

public interface NoticeInformDao {
	/**
	 * 查询所有公告
	 */
	public List<NoticeInform> getAllNoticeInform();

	/**
	 * 查询报修单分页信息
	 * 
	 * @param pageNum  查询第几页数据
	 * @param pageSize 每页显示多少条记录
	 * @return 查询结果
	 */
	public Pager<NoticeInform> findNoticeForm(int pageNum, int pageSize);
	
	/*
	 * 根据文件名查找文件路径
	 */
	public String findPath(String fileName);
	
	/*
	 * 获取最近几条保修信息
	 */
	public List<NoticeInform> getLastestNI();
	
	/**
	 * 上传所有公告
	 */
	public int upLoadNoticeInform(NoticeInform model);

}
