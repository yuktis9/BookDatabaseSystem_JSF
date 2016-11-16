package com.bookdata.bean;

public class Title {

	private String ISBN;
	private String title;
	private Integer copyRight;
	private Integer editionNo;
	
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCopyRight() {
		return copyRight;
	}
	public void setCopyRight(Integer copyRight) {
		this.copyRight = copyRight;
	}
	public Integer getEditionNo() {
		return editionNo;
	}
	public void setEditionNo(Integer editionNo) {
		this.editionNo = editionNo;
	}
	
	
	
}
