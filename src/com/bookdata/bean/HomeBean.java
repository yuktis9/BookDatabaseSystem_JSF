package com.bookdata.bean;

import java.io.IOException;

import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.bookdata.db.DBConnection;

@ManagedBean
@SessionScoped	
public class HomeBean {

	public void loginPage() throws ServletException, IOException{
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		
		DBConnection dbConnection = new DBConnection();
	     
	    request.getSession(true).setAttribute("dbConnection", dbConnection.createDBConnection()); 
	    
	    FacesContext.getCurrentInstance().getExternalContext().redirect("homePage.xhtml");
	}
	
	public void makeMeLogut() throws IOException{
		
		// get response from JSF itself instead of coming from filter.
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dbConnection", "false");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        FacesContext.getCurrentInstance().getExternalContext().redirect("loginPage.xhtml");
        
	}
	
	public void checkSession() {
		
		System.out.println("Hi body wasup>>>>>>>>>");
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		
		request.getSession().getAttribute("dbConnection");
		
	    if (request.getSession() == null || request.getSession().getAttribute("dbConnection") == null) {
	    	
	    	System.out.println("Hi body wasup>>>>>>>>>?2");
	    	
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        NavigationHandler navigationHandler =
	            facesContext.getApplication().getNavigationHandler();
	        navigationHandler.handleNavigation(facesContext, null, "seesionOut");
	    }
	}
}
