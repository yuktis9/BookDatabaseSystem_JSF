package com.bookdata.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.RowEditEvent;

import com.bookdata.db.DBQuery;
import com.bookdata.db.DBUpdate;

@ManagedBean
@ViewScoped
public class TitleBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Title> titles;

	private DBQuery dbQuery = new DBQuery();
	private DBUpdate dbUpdate = new DBUpdate();
	
	@PostConstruct
    public void init() {
        
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();

			
			titles = dbQuery.getTitles((Connection) request.getSession(true)
					.getAttribute("dbConnection"));

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public void removeTitle(Title title){
		
    	FacesContext context = FacesContext.getCurrentInstance();
		try {
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();

			dbQuery.deleteRow("titles", "ISBN", title.getISBN(),(Connection) request.getSession(true)
					.getAttribute("dbConnection"));
			titles = dbQuery.getTitles((Connection) request.getSession(true)
					.getAttribute("dbConnection"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
        FacesMessage msg = new FacesMessage("Title Deleted", title.getISBN());
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowEdit(RowEditEvent event) {
		
		Title title = (Title) event.getObject();
		
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			dbUpdate.updateTitle(title, (Connection) request.getSession(true)
					.getAttribute("dbConnection"));

		} catch (Exception e) {
			e.printStackTrace();
		}
        FacesMessage msg = new FacesMessage("Title Edited", String.valueOf((title.getISBN())));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(((Title) event.getObject()).getISBN()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public String maintainTitle() {
		return "success";
	}
    
    public String addTitle() {
		return "success";
	}
    
	public List<Title> getTitles() {
		return titles;
	}

	public void setTitles(List<Title> titles) {
		this.titles = titles;
	}
	
	
}
