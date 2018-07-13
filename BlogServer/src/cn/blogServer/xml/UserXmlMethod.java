package cn.blogServer.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.blogServer.entity.User;

public class UserXmlMethod {
	private File xmlFile;
	private SAXReader saxReader;
	private XMLWriter xmlWriter;
	private UserXmlMethod(String xmlPath){
		xmlFile = new File(xmlPath);
		saxReader = new SAXReader();
	}
	public static UserXmlMethod ininstance(String xmlPath){
		return new UserXmlMethod(xmlPath);
	}
	private Document readXml(){ //读取xml文件获取document对象
		Document document = null;
			try {
				document = saxReader.read(xmlFile);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		return document;
	}
	private boolean writeXml(Document document){//把document对象写入xml文件
		boolean flag = true;
		try {
			xmlWriter = new XMLWriter(new OutputStreamWriter(new FileOutputStream(xmlFile),"UTF-8"));
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	//添加用户
	public void addUser(User user){
		Document document = readXml();
		Element root = document.getRootElement();
		Element content = root.addElement("user");
		Element name = content.addElement("name");
		Element password = content.addElement("password");
		content.addAttribute("id",user.getId());
		name.setText(user.getUserName());
		password.setText(user.getUserPwd());
		writeXml(document);
	}
	//查询的方法,找到返回User对象,没有找到返回NULL
	public User searchUser(String userName){
		User user = null;
		Document document = readXml();
		List<Node> list = document.selectNodes("//name");
		Iterator<Node> ite = list.iterator();
		while(ite.hasNext()){
			Element seek = (Element)ite.next();
			if(userName.equals(seek.getText())){
				Element userE = seek.getParent();
				Element password = userE.element("password");
				user = new User(userE.attributeValue("id"), seek.getText(), password.getText());
			}
		}
		return user;
	}
	//获取用户链表
	public List<User> getUserList(){
		List<User> userList = new LinkedList<User>();
		Document document = readXml();
		List<Node> list = document.selectNodes("//user[@id]");
		Iterator<Node> ite = list.iterator();
		while(ite.hasNext()){
			Element seek = (Element)ite.next();
			Element name = seek.element("name");
			Element password = seek.element("password");
			userList.add(new User(seek.attributeValue("id"), name.getText(),password.getText()));
		}
		return userList;
	}
}
