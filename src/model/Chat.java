package model;

import java.util.Map;

public class Chat {
	private String username;
	private String roomid;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public Chat(Map<String, Object> map){
		this.roomid = (String)map.get("roomid");
		this.username = (String)map.get("username");
		
	}
}
