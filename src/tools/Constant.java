package tools;

public class Constant {
	/**
	 * 报修类型
	 */
	//水
	public static final int SERTYPE_WATER = 1;
	
	//木
	public static final int SERTYPE_WOOD = 2;
	
	//电
	public static final int SERTYPE_ELECTRIC = 3;
	
	//其他
	public static final int SERTYPE_ORTHER = 4;
	
	//全部
	public static final int SERTYPE_ALL = 0;
	
	
	/**
	 * 评价表提交标志
	 */
	//未提交
	public static final int UNSUBMIT = 0;
	//已提交
	public static final int SUBMIT = 1;
	
	
	/**
	 * 审核状态
	 */
	//未审核
	public static final int JUDGESTATE_UNCHECK = -1;
	
	//已受理
	public static final int JUDGESTATE_CHECKED = 1;
	
	//已完工
	public static final int JUDGESTATE_FINISH = 2;
	
	//全部
	public static final int JUDGESTATE_ALL = 0;
	
	
	/**
	 * 满意度选项
	 */
	//不满意
	public static final int UNSATISFIED = 1;
	//一般
	public static final int NORMAL = 2;
	//满意
	public static final int SATISFIED = 3;
	//全部
	public static final int SATISFIED_ALL = 0;
	
	
	/**
	 * 星级评分
	 */
	//一星
	public static final int CLASS_ONE = 1;
	//二星
	public static final int CLASS_TWO = 2;
	//三星
	public static final int CLASS_THREE = 3;
	//四星
	public static final int CLASS_FOUR = 4;
	//五星
	public static final int CLASS_FIVE = 5;
	//全部
	public static final int CLASS_ALL = 0;
	
	
	/**
	 * 默认每页显示多少条记录
	 */
	public static final int DEFAULT_PAGE_SIZE = 7;
	
	
	/**
	 * 默认显示第几页记录
	 */
	public static final int DEFAULT_PAGE_NUM = 1;
	
	
	/**
	 * HomePage默认显示几条报修单信息
	 */
	public static final int DEFAULT_REPAIRFORM_NUM = 5;
	
	/**
	 * HomePage默认显示几条公告栏信息
	 */
	public static final int DEFAULT_NoticeInform_NUM = 5;
}
