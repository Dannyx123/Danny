package cn.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import cn.vo.ContentVo;
import cn.vo.Message;

@ServerEndpoint("/serviceSocket")
public class ServiceSocket {
	private String username ;
	private static List<Session> sessions = new ArrayList<Session>() ;
	private static List<String> names = new ArrayList<String>() ;
	private static Map<String,Session> map = new HashMap<>() ;
	@OnOpen
	public void open(Session session) throws Exception{
		System.out.println("****************通讯已经打开");
		String queryString = session.getQueryString() ;
		username = queryString.split("=")[1];
		
		System.out.println("$$$$$$$$$$$$$$$$" + java.net.URLDecoder.decode(this.username,"UTF-8"));
		
		this.names.add(java.net.URLDecoder.decode(this.username,"UTF-8")) ;
		this.sessions.add(session) ;
		
		this.map.put(java.net.URLDecoder.decode(this.username,"UTF-8"), session) ;
		String msg = "" ;
		msg = "客户" + "&nbsp;'" +java.net.URLDecoder.decode(this.username,"UTF-8") +  "'&nbsp;" +"进入客服咨询中心！！！<br/>等待客服连接......<br/>"  ;
		
		if(java.net.URLDecoder.decode(this.username,"UTF-8").equals("admin")){
			msg = "客服" + "&nbsp;'Emma'&nbsp;" +"连接成功，竭诚为您服务.....<br/>"  ;
		}
		Message message = new Message() ;
		message.setWelcome(msg);
		message.setUsernames(this.names);
		
		this.broadcast(this.sessions,message.toJson()) ;
	}

	private static Gson gson = new Gson();

	@OnMessage
	public void message(Session session, String json) throws Exception{
		System.out.println("****************进入发消息通道");
		ContentVo vo = gson.fromJson(json, ContentVo.class);
		Message message = new Message();
		message.setContent(java.net.URLDecoder.decode(this.username, "UTF-8"), vo.getMsg());
		System.out.println("this.sessions*******************===>>>" + this.sessions);
		broadcast(this.sessions, message.toJson());
	}

	@OnClose
	public void close() {
		System.out.println("***********************进入关闭通道页面");
	}
	public void broadcast(List<Session> ss, String msg)  {
		for(Iterator iterator = ss.iterator();iterator.hasNext();){
			Session session = (Session)iterator.next() ;
			try {
				session.getBasicRemote().sendText(msg) ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
