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

@ServerEndpoint("/chatSocket")
public class ChatSocket {
	private String username ;
	private static List<Session> sessions = new ArrayList<Session>() ;
	private static List<String> names = new ArrayList<String>() ;
	private static Map<String,Session> map = new HashMap<>() ;
	@OnOpen
	public void open(Session session) throws Exception{
		String queryString = session.getQueryString() ;
		System.out.println("**************" + queryString);
		username = queryString.split("=")[1];
		
		this.names.add(java.net.URLDecoder.decode(this.username,"UTF-8")) ;
		this.sessions.add(session) ;
		
		this.map.put(java.net.URLDecoder.decode(this.username,"UTF-8"), session) ;
		
		String msg = "»¶Ó­" + java.net.URLDecoder.decode(this.username,"UTF-8") + "½øÈëÁÄÌìÊÒ£¡£¡£¡<br/>"  ;
		Message message = new Message() ;
		message.setWelcome(msg);
		message.setUsernames(this.names);
		
		this.broadcast(this.sessions,message.toJson()) ;
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
	@OnClose
	public void close (Session session) throws Exception{
		this.sessions.remove(session) ;
		this.names.remove(java.net.URLDecoder.decode(this.username,"UTF-8")) ;
		
		String msg = "»¶ËÍ" + java.net.URLDecoder.decode(this.username,"UTF-8") + "ÍË³öÁÄÌìÊÒ£¡£¡£¡<br/>"  ;
		Message message = new Message() ;
		message.setWelcome(msg);
		message.setUsernames(this.names);
		
		broadcast(this.sessions,message.toJson());
	}
	private static Gson gson = new Gson() ;
	@OnMessage
	public void message(Session session,String json) throws Exception{
		ContentVo vo = gson.fromJson(json,ContentVo.class) ;
		if(vo.getType() == 1){
			//ÈºÁÄ
			Message message = new Message() ;
			message.setContent(java.net.URLDecoder.decode(this.username,"UTF-8"), vo.getMsg());
			broadcast(this.sessions,message.toJson());
		}else{
			//Ë½ÁÄ
			String to = vo.getTo() ;
			Session to_session = this.map.get(to) ; 
			
			Message message = new Message() ;
			message.setContent(java.net.URLDecoder.decode(this.username,"UTF-8"),"<font color=red>Ë½ÁÄ£º"+vo.getMsg()+"</font>");
			
			try {
				to_session.getBasicRemote().sendText(message.toJson());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
}
