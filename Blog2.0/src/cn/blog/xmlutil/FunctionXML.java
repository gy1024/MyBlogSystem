package cn.blog.xmlutil;

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

import cn.blog.entity.Articel;

public class FunctionXML {
	private File xmlFile;
	private SAXReader saxReader;
	private XMLWriter xmlWriter;
	private FunctionXML(String xmlPath){
		xmlFile = new File(xmlPath);
		saxReader = new SAXReader();
	}
	public static FunctionXML ininstance(String xmlPath){
		return new FunctionXML(xmlPath);
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
	private String id(){
		Integer id =(int)(Math.random()*90000)+10000;
		return id.toString();
	}
	public void addArticel(Articel articel){//添加文章
		Document document = readXml();
		Element root = document.getRootElement();
		Element content = root.addElement("content");
		String id = id();
		articel.setId(id);
		content.addAttribute("id",id);//随机添加ID号
		Element title = content.addElement("title");
		Element body = content.addElement("body");
		Element time = content.addElement("time");
		title.setText(articel.getTitle());
		body.setText(articel.getContent());
		time.setText(articel.getDate());
		writeXml(document);
	}
	public boolean removeArticel(Articel articel){//删除文章
		boolean flag = false;
		Document document = readXml();
		List<Node> list = document.selectNodes("//content[@id]");
		Iterator<Node> ite = list.iterator();
		while(ite.hasNext()){
			Element seek = (Element)ite.next();
			if(seek.attributeValue("id").equals(articel.getId())){
				seek.getParent().remove(seek);
				writeXml(document);
				flag = true;
			}
		}
		return flag;
	}
	public Articel searchArticel(String id){//搜索文章
		Articel articel = null;
		Document document = readXml();
		List<Node> list = document.selectNodes("//content[@id]");
		Iterator<Node> ite = list.iterator();
		while(ite.hasNext()){
			Element seek = (Element)ite.next();
			if(seek.attributeValue("id").equals(id)){
				Element title = seek.element("title");
				Element body = seek.element("body");
				Element time = seek.element("time");
				articel = new Articel(title.getText(), body.getText(),time.getText());
				articel.setId(id);
			}
		}
		return articel;
	}
	public boolean midifArticel(Articel articel){//修改文章
		boolean flag = false;
		Document document = readXml();
		List<Node> list = document.selectNodes("//content[@id]");
		Iterator<Node> ite = list.iterator();
		while(ite.hasNext()){
			Element seek = (Element)ite.next();
			if(seek.attributeValue("id").equals(articel.getId())){
				Element title = seek.element("title");
				Element body = seek.element("body");
				title.setText(articel.getTitle());
				body.setText(articel.getContent());
				writeXml(document);
				flag = true;
			}
		}
		return flag;
	}
	public List<Articel> getArticelList(){//获取文章列表
		List<Articel> aList = new LinkedList<Articel>();
		Document document = readXml();
		List<Node> list = document.selectNodes("//content[@id]");
		Iterator<Node> ite = list.iterator();
		while(ite.hasNext()){
			Element seek = (Element)ite.next();
			Element title = seek.element("title");
			Element body = seek.element("body");
			Element time = seek.element("time");
			aList.add(new Articel(seek.attributeValue("id"), title.getText(),body.getText(),time.getText()));
		}
		return aList;
	}
}
