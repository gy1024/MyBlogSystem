package cn.blogServer.entity;

import java.io.Serializable;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1375884970905075868L;
	private String id;
	private String userName;
	private String userPwd;
	public User(String id, String userName, String userPwd) {
		super();
		this.id = id;
		this.userName = userName;
		this.userPwd = userPwd;
	}
	//登录用
	public User(String userName, String userPwd){
		this.userName = userName;
		this.userPwd = userPwd;
	}
	public String getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {//预留修改密码的方法 
		this.userPwd = userPwd;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", userPwd=" + userPwd + "]";
	}
}
