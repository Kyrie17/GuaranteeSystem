package servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.PathParam;

import net.sf.json.JSONObject;

@ServerEndpoint("/websocket")
//@ServerEndpoint("/chatSocket")
public class WebSocketService {
	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");// 创建时间格式对象
	// concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketService对象。
	// 创建一个房间的集合，用来存放房间
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketService>> roomList = new ConcurrentHashMap<String, ConcurrentHashMap<String, WebSocketService>>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	// 重新加入房间的标示；
	private int rejoin = 0;
	/*static {
		roomList.put("room1", new ConcurrentHashMap<String, WebSocketService>());
		roomList.put("room2", new ConcurrentHashMap<String, WebSocketService>());
	}*/

	/**
	 * 用户接入
	 * 
	 * @param session
	 */
	@OnOpen
	public void onOpen(Session session) {
		
		try {
			String queryString = session.getQueryString();
			this.session = session;
			String flag = queryString.substring(queryString.indexOf("=") + 1, queryString.indexOf("&")); // 标识
			String member = queryString.substring(queryString.indexOf("*") + 2, queryString.indexOf("!")); // 成员名
			if(roomList.get(member)==null) {
				roomList.put(member, new ConcurrentHashMap<String, WebSocketService>());
				}
			if (flag.equals("join")) {
				String user = queryString.substring(queryString.indexOf("_") + 2);
				joinRoom(member, user);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 加入房间
	public void joinRoom(String member, String user) {
		ConcurrentHashMap<String, WebSocketService> r = roomList.get(member);
		if (r.get(user) != null) { // 该用户有没有出
			this.rejoin = 1;
		}
		r.put(user, this);// 将此用户加入房间中
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * 接收到来自用户的消息
	 * 
	 * @param message
	 * @param session
	 * @throws IOException
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		// 把用户发来的消息解析为JSON对象
		JSONObject obj = JSONObject.fromObject(message);
		if (obj.get("flag").toString().equals("exitroom")) { // 退出房间操作
			String roomid = obj.get("roomid").toString();
			// 将用户从聊天室中移除
			int f2 = 1;
			roomList.get(roomid).remove(obj.get("nickname").toString());// 将用户直接移除
			if (roomList.get(roomid).size() == 0) {// 判断房间该房间是否还有用户，如果没有，则将此房间也移除
				f2 = 2;
			}
			if (f2 == 1) { // 证明该房间还有其它成员，则通知其它成员更新列表
				obj.put("flag", "exitroom");
				String m = obj.get("nickname").toString() + " 退出了房间";
				obj.put("message", m);
				ConcurrentHashMap<String, WebSocketService> r = roomList.get(roomid);
				List<String> uname = new ArrayList<String>();
				for (String u : r.keySet()) {
					uname.add(u);
				}
				obj.put("uname", uname.toArray());
				for (String i : r.keySet()) { // 遍历该房间
					r.get(i).sendMessage(obj.toString());// 调用方法 将消息推送
				}
			}
		} else if (obj.get("flag").toString().equals("chatroom")) { // 聊天室的消息 加入房间/发送消息
			// 向JSON对象中添加发送时间
			obj.put("date", df.format(new Date()));
			// 获取客户端发送的数据中的内容---房间�? 用于区别该消息是来自于哪个房间
			String roomid = obj.get("target").toString();
			// 获取客户端发送的数据中的内容---用户
			String username = obj.get("nickname").toString();
			// 从房间列表中定位到该房间
			try {
				ConcurrentHashMap<String, WebSocketService> r = roomList.get(roomid);
				List<String> uname = new ArrayList<String>();
				for (String u : r.keySet()) {
					uname.add(u);
				}
				obj.put("uname", uname.toArray());
				if (r.get(username).rejoin == 0) { // 证明不是退出重连
					for (String i : r.keySet()) { // 遍历该房间
						obj.put("isSelf", username.equals(i));// 设置消息是否为自己的
						r.get(i).sendMessage(obj.toString());// 调用方法 将消息推送
					}
				} else {
					obj.put("isSelf", true);
					r.get(username).sendMessage(obj.toString());
				}

				r.get(username).rejoin = 0;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 用户断开
	 * 
	 * @param session
	 */
	@OnClose
	public void onClose(Session session) {
		System.out.println("退出");
	}

	/**
	 * 用户连接异常
	 * 
	 * @param t
	 */
	@OnError
	public void onError(Throwable t) {

	}
}
