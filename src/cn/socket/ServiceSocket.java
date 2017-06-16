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
		System.out.println("****************ͨѶ�Ѿ���");
		String queryString = session.getQueryString() ;
		username = queryString.split("=")[1];
		
		System.out.println("$$$$$$$$$$$$$$$$" + java.net.URLDecoder.decode(this.username,"UTF-8"));
		
		this.names.add(java.net.URLDecoder.decode(this.username,"UTF-8")) ;
		this.sessions.add(session) ;
		
		this.map.put(java.net.URLDecoder.decode(this.username,"UTF-8"), session) ;
		String msg = "" ;
		msg = "�ͻ�" + "&nbsp;'" +java.net.URLDecoder.decode(this.username,"UTF-8") +  "'&nbsp;" +"����ͷ���ѯ���ģ�����<br/>�ȴ��ͷ�����......<br/>"  ;
		
		if(java.net.URLDecoder.decode(this.username,"UTF-8").equals("admin")){
			msg = "�ͷ�" + "&nbsp;'Emma'&nbsp;" +"���ӳɹ����߳�Ϊ������.....<br/>"  ;
		}
		Message message = new Message() ;
		message.setWelcome(msg);
		message.setUsernames(this.names);
		
		this.broadcast(this.sessions,message.toJson()) ;
	}

	private static Gson gson = new Gson();

	@OnMessage
	public void message(Session session, String json) throws Exception{
		System.out.println("****************���뷢��Ϣͨ��");
		ContentVo vo = gson.fromJson(json, ContentVo.class);
		Message message = new Message();
		message.setContent(java.net.URLDecoder.decode(this.username, "UTF-8"), vo.getMsg());
		System.out.println("this.sessions*******************===>>>" + this.sessions);
		broadcast(this.sessions, message.toJson());
	}

	@OnClose
	public void close() {
		System.out.println("***********************����ر�ͨ��ҳ��");
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
