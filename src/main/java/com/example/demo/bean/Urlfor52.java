package com.example.demo.bean;

public class Urlfor52 {
	private String parent;//主键标识
	private String path;
	/**
	 * 0000-主页
	 * 0011-方案超市；0012-方案分类; 0112-分类分页；0013-方案列表
	 * 0021-方案讯；0022-方案分页；0023-方案讯列表
	 * */
	private String type;
	
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Urlfor52(String path, String type) {
		super();
		this.path = path;
		this.type = type;
	}
	
	public Urlfor52(String parent, String path, String type) {
		super();
		this.parent = parent;
		this.path = path;
		this.type = type;
	}
	public Urlfor52() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Urlfor52 [parent=" + parent + ", path=" + path + ", type=" + type + "]";
	}
	

}
