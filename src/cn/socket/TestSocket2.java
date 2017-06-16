package cn.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import cn.vo.ContentVo;
import cn.vo.Message;

@ServerEndpoint("/testSocket2")
public class TestSocket2 {
	private String username ;
	private static List<Session> sessions = new ArrayList<Session>() ;
	private static List<String> names = new ArrayList<String>() ;
	private static Map<String,Session> map = new HashMap<>() ;
	
	public static Map<String,Session> userSessionMap = new HashMap<String,Session>(1000);
	@OnOpen
	public void open(Session session) throws Exception{
		System.out.println("ͨѶ�����Ѵ򿪣����Խ���ͨ����");
//		System.out.println("****************testSocketͨѶ�Ѿ���");
//		String queryString = session.getQueryString() ;
//		username = queryString.split("=")[1];
//		
//		System.out.println("$$$$$$$$$$$$$$$$" + java.net.URLDecoder.decode(this.username,"UTF-8"));
//		
//		this.names.add(java.net.URLDecoder.decode(this.username,"UTF-8")) ;
//		this.sessions.add(session) ;
//		
//		this.map.put(java.net.URLDecoder.decode(this.username,"UTF-8"), session) ;
//		String msg = "" ;
//		
//		msg = "�ͷ�" + "&nbsp;'Emma'&nbsp;" +"���ӳɹ����߳�Ϊ������.....<br/>"  ;
//		Message message = new Message() ;
//		message.setWelcome(msg);
//		message.setUsernames(this.names);
//		
//		this.broadcast(this.sessions,message.toJson()) ;
	}

	private static Gson gson = new Gson();

	@SuppressWarnings("unused")
	@OnMessage
	public void message(Session session, String message) throws Exception{
		System.out.println("后端进入发消息************" + message);
		if(message.equals("login")){
            TestSocket.userSessionMap.put("admin", session);
        }else{
            JSONObject json = JSON.parseObject(message);
            String name = json.getString("name");
            String reply = json.getString("reply");
            Message chat = null ;
            Message vo = new Message() ;
            vo.setId(33333l);
            vo.setName("123");
            vo.setContent("345");
            
            Long chatId = null;
            if(json.containsKey("chatId")){
            	chatId = json.getLongValue("chatId");
            }
            try {
                if(chatId!=null){
                    chat = vo;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(chat==null){
                String content = json.getString("content");
                chat = vo;
            }
            if(chat!=null){
                chat.setName(name);
                chat.setReply(reply);
                chat.setEndDate(new Date());
                if( TestSocket.userSessionMap.containsKey(name)){
                    Session userSession = TestSocket.userSessionMap.get(name);
                    try {
                        boolean state = userSession.isOpen();
                        if(state){
                            userSession.getBasicRemote().sendText(json.toJSONString());
                            chat.setIsRead("δ��");
                            chat.setType("�ѽ���");
                        }else{
                        	TestSocket.userSessionMap.remove(name);
                            chat.setIsRead("δ��");
                            chat.setType("�û����뿪");
                            JSONObject rejson = new JSONObject();
                            rejson.put("name", name);
                            rejson.put("content", "5245485247545$%^&2343--=90096833674&*^&%%#75");
                            session.getBasicRemote().sendText(rejson.toJSONString());
                        }
                    } catch (IOException e) {
                        chat.setIsRead("δ��");
                        chat.setType("�û����뿪");
                        e.printStackTrace();
                    }
                }else{
                    chat.setIsRead("δ��");
                    chat.setType("�ѽ���");
                }
//                chatInformationService.updateById(chat);
//                chatInformationService.updateContentByReply(name);
            }
        }
    }

	@OnClose
	public void close() {
		System.out.println("��̨ͨѶ�����ѶϿ�������");
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
