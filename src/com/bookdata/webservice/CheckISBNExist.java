package com.bookdata.webservice;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.bookdata.db.DBQuery;

@Path("/checkForISBN/{ISBN}")
public class CheckISBNExist {
	  
	@GET
	public String abcd(@PathParam ("ISBN") String ISBN, @Context HttpServletRequest request){
		
		System.out.println("Inside Web Service, ISBN Value: "+ISBN);
		DBQuery dbQuery = new DBQuery();
		boolean isISBNExist = dbQuery.checkISBNExist(ISBN, (Connection)request.getSession().getAttribute("dbConnection"));
		return String.valueOf(isISBNExist);
	}
}
