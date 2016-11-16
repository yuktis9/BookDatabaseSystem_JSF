package com.bookdata.converter;

import java.sql.Connection;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpServletRequest;

import com.bookdata.bean.Title;
import com.bookdata.db.DBQuery;
 
@FacesConverter("titleConverter")
public class TitleConverter implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
            	
            	FacesContext context = FacesContext.getCurrentInstance();
            	HttpServletRequest request = (HttpServletRequest) context
    					.getExternalContext().getRequest();
            	
            	DBQuery dbQuery = new DBQuery();
                return dbQuery.getTitle(Integer.parseInt(value),  (Connection) request.getSession(true)
    					.getAttribute("dbConnection"));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Title) object).getISBN());
        }
        else {
            return null;
        }
    }   
}   