package com.bookdata.bean;

import java.sql.Connection;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import com.bookdata.db.DBQuery;

@ManagedBean(name = "authorISBN")
@ViewScoped
public class AuthorISBN {

	private Author authorForFirstName;
	private Title titleForISBN;
	
	private Author authorForLastName;
	private Title titleForTitle;
	
	private Author author;
	private Title title;
	
	String searchAuthorType;
	String searchTitleType;
	
	public String addAuthorISBN(){
		return "success";
	}

	public void submitForm(){
		
		String authorID = null;
		String ISBN = null;
		
		if("FirstName".equals(searchAuthorType)){
			authorID = authorForFirstName.getAuthorId();
		}else if("LastName".equals(searchAuthorType)){
			authorID = authorForLastName.getAuthorId();
		}
		
		if("Title".equals(searchTitleType)){
			ISBN = titleForTitle.getISBN();
		}else if("ISBN".equals(searchTitleType)){
			ISBN = titleForISBN.getISBN();
		}

		if(ISBN == null || authorID == null){
			
			FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Please search the Author and Title."));
		}else{
			
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			
			DBQuery dbQuery = new DBQuery();
			boolean checkAuthorExist = dbQuery.checkAuthorISBNExist(authorID, ISBN, (Connection) request.getSession(true)
					.getAttribute("dbConnection"));
			
			if(checkAuthorExist){
				/*FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage("Author and Title already linked."));*/
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Warning", "Author and Title already linked.");
				RequestContext.getCurrentInstance().showMessageInDialog(message);
			}else{
				dbQuery.addAuthorISBN(authorID, ISBN, (Connection) request.getSession(true)
						.getAttribute("dbConnection"));
				
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "Author and Title linked successfully.");
				RequestContext.getCurrentInstance().showMessageInDialog(message);
				/*FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage("Author and Title linked successfully."));*/
			}
		}
	}
	
	
	public List<Author> searchAuthor(String searchChar){

		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		
		String searchBy = (String) UIComponent.getCurrentComponent(context).getAttributes().get("searchBy");
		
		DBQuery dbQuery = new DBQuery();
		return dbQuery.searchAuthors(searchBy, searchChar, (Connection) request.getSession(true)
				.getAttribute("dbConnection"));
	}
	
	public List<Title> searchTitle(String searchChar){

		FacesContext context = FacesContext.getCurrentInstance();
		
		String searchBy = (String) UIComponent.getCurrentComponent(context).getAttributes().get("searchBy");
		
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		
		DBQuery dbQuery = new DBQuery();
		return dbQuery.searchTitles(searchBy, searchChar, (Connection) request.getSession(true)
				.getAttribute("dbConnection"));
	}

	public Author getAuthorForFirstName() {
		return authorForFirstName;
	}

	public void setAuthorForFirstName(Author authorForFirstName) {
		this.authorForFirstName = authorForFirstName;
	}

	public Title getTitleForISBN() {
		return titleForISBN;
	}

	public void setTitleForISBN(Title titleForISBN) {
		this.titleForISBN = titleForISBN;
	}

	public Author getAuthorForLastName() {
		return authorForLastName;
	}

	public void setAuthorForLastName(Author authorForLastName) {
		this.authorForLastName = authorForLastName;
	}

	public Title getTitleForTitle() {
		return titleForTitle;
	}

	public void setTitleForTitle(Title titleForTitle) {
		this.titleForTitle = titleForTitle;
	}

	public String getSearchAuthorType() {
		return searchAuthorType;
	}

	public void setSearchAuthorType(String searchAuthorType) {
		this.searchAuthorType = searchAuthorType;
	}

	public String getSearchTitleType() {
		return searchTitleType;
	}

	public void setSearchTitleType(String searchTitleType) {
		this.searchTitleType = searchTitleType;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}
	
	
}
