package cn.blog.changeHtml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import cn.blog.entity.Articel;


public class ChangeHtml {
	List<Articel> list;
	private static String templateHtml;
	private static String indexTemplate;
	static{
		File templatePath = new File("webroot/PageTemplate/template.html");
		File indexPath = new File("webroot/PageTemplate/indexTemplate.html");
		FileInputStream htmlInputstream = null;
		FileInputStream indexInputstream = null;
		try {
			htmlInputstream = new FileInputStream(templatePath);
			int lenght = htmlInputstream.available();
			byte[] bytes = new byte[lenght];
			htmlInputstream.read(bytes);
			htmlInputstream.close();
			templateHtml = new String(bytes);
			indexInputstream = new FileInputStream(indexPath);
			lenght = indexInputstream.available();
			bytes = new byte[lenght];
			indexInputstream.read(bytes);
			indexInputstream.close();
			indexTemplate = new String(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ChangeHtml(List<Articel> list){
		this.list = list;
	}
	public void changeHtml(Articel articel){
		String waitSplitContent = articel.getContent();
		StringBuffer content = new StringBuffer();
		String[] beSplitedContect = waitSplitContent.split("\n");
		for (int i = 0; i < beSplitedContect.length; i++) {
			content.append(beSplitedContect[i]).append("<br/>");
		}
		String template = templateHtml;
		template = template.replaceAll("###title###", articel.getTitle());
		template = template.replaceAll("###content###", content.toString());
		File html = new File("webroot/ArticleLists/"+articel.getId()+".html");
		FileOutputStream htmlOutputstream;
		try {
			htmlOutputstream = new FileOutputStream(html);
			htmlOutputstream.write(template.getBytes());
			htmlOutputstream.close();
			System.out.println(articel.getId()+"转换成功!"+html.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void batchChange(){
		Iterator<Articel> ite = list.iterator();
		Articel articel;
		/**
		 <div class="fh5co-entry padding">
			<img src="images/project-1.jpg">
				<div><span class="fh5co-post-date">日期</span>
				<h2><a href="single.html">标题</a></h2></div>
		</div>
		 */
		StringBuffer contents = new StringBuffer();
		int num ;
		while(ite.hasNext()){
			num = (int)(Math.random()*7)+1;
			articel = ite.next();
			changeHtml(articel);
			System.out.println(articel);
			contents.append("<div class=\"fh5co-entry padding\">");
			contents.append("<img src=\"images/project-"+num+".jpg\">");
			contents.append("<div><span class=\"fh5co-post-date\">"+articel.getDate()+"</span>");
			contents.append("<h2><a href=\"ArticleLists/"+articel.getId()+".html\">"+articel.getTitle()+"</a></h2></div></div>\n");
		}
		File file = new File("webroot/index.html");
		String template = indexTemplate;
		template = template.replaceAll("###content###",contents.toString());
		FileOutputStream htmlOutputstream;
		try {
			htmlOutputstream = new FileOutputStream(file);
			htmlOutputstream.write(template.getBytes());
			htmlOutputstream.close();
			System.out.println("目录以生成！");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
