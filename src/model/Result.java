/**  
*/
package model;


public class Result<T> {
	//状态码
	private double code;//-1,....
	// 返回数据
	private T data;
	// 返回提示信息
	private String msg;

	public Result() {
		super();
	}

	public Result(double code, T data, String msg) {

		this.code = code;
		this.data = data;
		this.msg = msg;
	}

	public double getCode() {
		return code;
	}

	public void setCode(double code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", data=" + data + ", msg=" + msg + "]";
	}

}
