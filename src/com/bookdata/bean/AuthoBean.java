package com.bookdata.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.bookdata.db.DBQuery;
import com.bookdata.db.DBUpdate;
import com.bookdata.security.EncryptionDecryptionAES;

@ManagedBean(name = "authorBean")
@ViewScoped
public class AuthoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Author> authors;

	private DBQuery dbQuery = new DBQuery();
	
	private DBUpdate dbUpdate = new DBUpdate();
	
	
	public void abcd(){
		System.out.println("sdsd>>>>>>>> ");
		
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			HttpServletRequest request   = 
	                   (HttpServletRequest) context.getExternalContext().getRequest();
	     HttpServletResponse response = 
	                   (HttpServletResponse) context.getExternalContext().getResponse();
	     RequestDispatcher dispatcher = 
	                   request.getRequestDispatcher("/addTitle");
	     
	     dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@PostConstruct
    public void init() {
        
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();

			
			authors = dbQuery.getAuthors((Connection) request.getSession(true)
					.getAttribute("dbConnection"));

		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void removeAuthor(Author author){
		
    	FacesContext context = FacesContext.getCurrentInstance();
    	EncryptionDecryptionAES encryptionDecryptionAES = new EncryptionDecryptionAES();
		try {
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();

			dbQuery.deleteRow("authors", "AuthorID", encryptionDecryptionAES.decrypt(author.getAuthorId()),(Connection) request.getSession(true)
					.getAttribute("dbConnection"));
			authors = dbQuery.getAuthors((Connection) request.getSession(true)
					.getAttribute("dbConnection"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
        FacesMessage msg = new FacesMessage("Author Deleted", author.getFirstName() +" "+ author.getLastName());
        FacesContext.getCurrentInstance().addMessage(null, msg);

	}
	
	public void onRowEdit(RowEditEvent event) {
		
		EncryptionDecryptionAES encryptionDecryptionAES = new EncryptionDecryptionAES();
		Author author = (Author) event.getObject();
		author.setAuthorId(encryptionDecryptionAES.decrypt(author.getAuthorId()));
		
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();

			
			dbUpdate.updateAuthor(author, (Connection) request.getSession(true).getAttribute("dbConnection"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
        FacesMessage msg = new FacesMessage("Author Edited", author.getFirstName() +" "+ author.getLastName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
    	Author author = (Author) event.getObject();
    	
        FacesMessage msg = new FacesMessage("Edit Cancelled", author.getFirstName() +" "+ author.getLastName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
	public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
	
	public void save(AjaxBehaviorEvent event) {

		System.out.println(event.getSource().getClass());
		System.out.println(">>>>>> "+authors.get(0).getFirstName());
	}
	
	public void addAuthor() {

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		DBQuery dbQuery = new DBQuery();
		dbQuery.addAuthor("authors", firstName, lastName, (Connection) request
				.getSession(true).getAttribute("dbConnection"));
		
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information", "Author " + firstName + " " + lastName +" added");
		RequestContext.getCurrentInstance().showMessageInDialog(message);
		firstName = "";
		lastName = "";
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public void someaction(AjaxBehaviorEvent event) {

	}

	public void editAction() {

		setEditable(true);
	}

	
	Integer authorId;
	String firstName;
	String lastName;

	private boolean editable = false;

	public void ediAuthor(Author author) {

	}

	public DBQuery getDbQuery() {
		return dbQuery;
	}

	public void setDbQuery(DBQuery dbQuery) {
		this.dbQuery = dbQuery;
	}

	public List<Author> getAuthors() {
	
		return authors;
	}

	public String maintainAuthor() {

		return "success";
	}

	public String addAuthorPage(){
		return "success";
	}
	
	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
