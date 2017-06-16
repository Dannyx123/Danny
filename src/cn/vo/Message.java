package cn.vo;

import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

public class Message {
	private Long id ;
	
	private String Welcome ;
	
	private List<String> usernames ;
	
	private String name ;
	
	private String content ;
	
	/**
     * 回复的内容
     */
    private String reply;
    /**
     * 是否结束
     */
    private String type;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 回复的时间
     */
    private Date endDate;
    /**
     * 用户是否已读
     */
    private String isRead;
	
	
    
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public void setContent(String name,String msg) {
		this.content = name + ":" + new Date().toLocaleString() + ":<br/>" +msg+ "<br/>";
	}

	public String getWelcome() {
		return Welcome;
	}

	public void setWelcome(String welcome) {
		Welcome = welcome;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
	public String toJson(){
		return gson.toJson(this) ;
	}
	private static Gson gson = new Gson () ;
	
 }
