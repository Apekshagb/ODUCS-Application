package com.example.oducs_mobile;

public class NewsStructure {
	
	private String news_title;
	private String keyword;
	private String author;
	private String highlights;
	private String picture;
	
	public String getNews_title() {
		return news_title;
	}
	
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getHighlights() {
		return highlights;
	}
	
	public void setHighlights(String highlights) {
		this.highlights = highlights;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public String toString(){
		return("NewsList[news_title=" + news_title + ", keyword=" + keyword + ", author="+ author + ", highlights=" + highlights + ", picture="+ picture + "]");
	}

}
