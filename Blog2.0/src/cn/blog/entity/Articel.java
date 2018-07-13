package cn.blog.entity;

public class Articel {
	private String id = null;
	private String title;
	private String content;
	private String date;
	public Articel(String title, String content,String date) {
		this.title = title;
		this.content = content;
		this.date = date;
	}
	
	public Articel(String id, String title, String content,String date) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getDate() {
		return date;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Articel [id=" + id + ", title=" + title + date+"]";
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
