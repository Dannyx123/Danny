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

@ServerEndpoint("/testSocket")
public class TestSocket {
	private String username ;
	private static List<Session> sessions = new ArrayList<Session>() ;
	private static List<String> names = new ArrayList<String>() ;
	private static Map<String,Session> map = new HashMap<>() ;
	
	public static Map<String,Session> userSessionMap = new HashMap<String,Session>(1000);
	@OnOpen
	public void open(Session session) throws Exception{
		System.out.println("****************testSocketͨѶ�Ѿ���");
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
//		msg = "�ͻ�" + "&nbsp;'" +java.net.URLDecoder.decode(this.username,"UTF-8") +  "'&nbsp;" +"����ͷ���ѯ���ģ�����<br/>�ȴ��ͷ�����......<br/>"  ;
		
//		if(java.net.URLDecoder.decode(this.username,"UTF-8").equals("admin")){
//			msg = "�ͷ�" + "&nbsp;'Emma'&nbsp;" +"���ӳɹ����߳�Ϊ������.....<br/>"  ;
//		}
//		Message message = new Message() ;
//		message.setWelcome(msg);
//		message.setUsernames(this.names);
		
//		this.broadcast(this.sessions,message.toJson()) ;
	}

	private static Gson gson = new Gson();

	@OnMessage
	public void message(Session session, String json) throws Exception{
		System.out.println("****************���뷢��Ϣͨ��" + json);
		JSONObject jsonObj = JSON.parseObject(json) ;
		Message message = new Message() ;
		String name = jsonObj.getString("name") ;
		message.setName(name); 
		message.setContent(message.getContent());
		jsonObj.put("chatId", message.getId()+"");
		TestSocket.userSessionMap.put(name, session);
		if(TestSocket.userSessionMap.containsKey("admin")){
			Session adminSession = TestSocket.userSessionMap.get("admin");
			try {
                boolean state = adminSession.isOpen();
                if(state){
                    adminSession.getBasicRemote().sendText(jsonObj.toJSONString());
                }else{
                    this.sendAuot(session);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            this.sendAuot(session);
        }
//        if(hour>16||hour<9){
//            this.sendAuot(session);
//        }
		
//		ContentVo vo = gson.fromJson(json, ContentVo.class);
//		Message message = new Message();
//		message.setContent(java.net.URLDecoder.decode(this.username, "UTF-8"), vo.getMsg());
//		System.out.println("this.sessions*******************===>>>" + this.sessions);
//		broadcast(this.sessions, message.toJson());
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
	private void sendAuot(Session session){
        String reply = "���ã��ͷ���Ա��ʱ���ڣ��������������Ѿ���¼�����Ժ��ٴ�ѯ�ʣ������µ绰�����Ա�ͷ���ϵ��";
        Date nowTime = new Date();
        @SuppressWarnings("deprecation")
		int hour = nowTime.getHours();
        if(hour>20||hour<9){
            reply = "���ã��ͷ���Ա��ʱ���ڣ������ڹ�����9��������20��֮��ѯ�ʡ�";
        }
        try {
            session.getBasicRemote().sendText(reply); //��Ӧ������
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
