package com.bookdata.bean;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.chart.PieChartModel;

import com.bookdata.db.DBQuery;
import com.bookdata.db.DBUpdate;

@ManagedBean
@ViewScoped
public class AuthorISBNBean {

	private List<AuthorISBN> authorISBNs;

	private String restricteditionNo;
	
	private String columnName;
	private String tableName;
	private String columnType;
	private String restrcitionType;
	private String likeValue;
	private String numericValue;
	
	private int betweenMinValue = 90;
	private int betweenMaxValue = 500;
	
	private Map<String,String> columnNames;
	
	private DBUpdate dbUpdate = new DBUpdate();
	private DBQuery dbQuery = new DBQuery();
	
	private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    
	@PostConstruct
    public void init() {
		
		String authorISBNSql = "select * from authorisbn";
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

		authorISBNs = dbQuery.getAuthorISBNs(authorISBNSql, (Connection)request.getSession(true).getAttribute("dbConnection")); 
    }
	
	public void onTableChange(){
		
		if(tableName.equals("titles")){
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			
			DBQuery dbQuery = new DBQuery();
			columnNames = dbQuery.getColumnNames(tableName, (Connection)request.getSession(true).getAttribute("dbConnection"));
			
		}else if(tableName.equals("authors")){
			
			columnNames = new HashMap<String, String>();
			columnNames.put("FirstName", "FirstName");
			columnNames.put("LastName", "LastName");
		}
	}
	
	public void updateSearchData(){
		
		String select  = "SELECT authorisbn.AuthorID, authorisbn.ISBN FROM titles join authorisbn on titles.ISBN = authorisbn.ISBN join  authors on  authors.AuthorID = authorisbn.AuthorID ";
		
		String where = "where ";
		
		where = where + tableName +"."+ columnName +" ";
		
		if(columnType.equals("numericType")){
			
			if(restrcitionType.equals("BETWEEN")){
				
				where = where + (restrcitionType +" "+ betweenMinValue +" AND "+ betweenMaxValue);
			}else if(restrcitionType.equals("0")){
				
				
						
			}else{
				where = where + restrcitionType + numericValue;
			}
			
		}else if(columnType.equals("stringType")){
			
			where = where + " LIKE '%" + likeValue +"%'";
		}
		
		System.out.println("where : "+where);
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		
		DBQuery dbQuery = new DBQuery();
		System.out.println(select + where);
		authorISBNs = dbQuery.getAuthorISBNs(select + where, (Connection)request.getSession(true).getAttribute("dbConnection"));
		
	}
	
	public void removeAuthorISBN(AuthorISBN authorISBN){
		
		String authorISBNSql = "select * from authorisbn";
		
    	FacesContext context = FacesContext.getCurrentInstance();

		try {
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();

			dbQuery.deleteAuthorISBN(authorISBN.getAuthor().getAuthorId(), authorISBN.getTitle().getISBN(), (Connection)request.getSession(true).getAttribute("dbConnection"));
			authorISBNs = dbQuery.getAuthorISBNs(authorISBNSql, (Connection)request.getSession(true).getAttribute("dbConnection"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
        FacesMessage msg = new FacesMessage("AuthorIsbn Deleted", authorISBN.getAuthor().getFirstName() +" "+ authorISBN.getAuthor().getLastName()+" with ISBN "+authorISBN.getTitle().getISBN());
        FacesContext.getCurrentInstance().addMessage(null, msg);

	}
	
	public void onRowEdit(RowEditEvent event) {
		
		AuthorISBN authorISBN = (AuthorISBN) event.getObject();
		authorISBN.getAuthor().setAuthorId(authorISBN.getAuthor().getAuthorId());
		
		Title title = authorISBN.getTitle();
		
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();

			dbUpdate.updateTitle(title, (Connection) request.getSession(true).getAttribute("dbConnection"));
			dbUpdate.updateAuthor(authorISBN.getAuthor(), (Connection) request.getSession(true).getAttribute("dbConnection"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
        FacesMessage msg = new FacesMessage("AuthorISBN Edited", authorISBN.getAuthor().getFirstName() +" "+ authorISBN.getAuthor().getLastName()+" with ISBN "+title.getISBN());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
    	
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void getFirstChart(){
    	
    	FacesContext context = FacesContext.getCurrentInstance();
    	HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    	
    	int [] chartData = dbQuery.getChartData((Connection) request.getSession(true).getAttribute("dbConnection"));
    	
    	int authorsCount = chartData[0];
    	int titlesCount = chartData[1];
    	int authorisbnAuthorIDCount = chartData[2];
    	int authorisbnISBNCount = chartData[3];
    	
    	pieModel1 = new PieChartModel();
        
        pieModel1.set("Total Authors: "+authorsCount, authorsCount);
        pieModel1.set("Total Books: "+titlesCount, titlesCount);
        pieModel1.set("Total Authors with atleast a single Book: "+authorisbnAuthorIDCount, authorisbnAuthorIDCount);
        pieModel1.set("Total Books with atleast a single Author: "+authorisbnISBNCount, authorisbnISBNCount);
        
        pieModel1.set("Total Authors with No Book: "+(authorsCount - authorisbnAuthorIDCount), authorsCount - authorisbnAuthorIDCount);
        pieModel1.set("Total Books with No Author: "+(titlesCount - authorisbnISBNCount), titlesCount - authorisbnISBNCount);
         
        pieModel1.setTitle("Book Database Statistics");
        pieModel1.setLegendPosition("w");
        pieModel1.setDiameter(400);
        pieModel1.setShowDataLabels(true);
        pieModel1.setDataFormat("value");
    }
    
    /*public void getSecondChart(){
    	
    	FacesContext context = FacesContext.getCurrentInstance();
    	HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    	
    	int [] chartData = dbQuery.getChartData((Connection) request.getSession(true).getAttribute("dbConnection"));
    	
    	int authorsCount = chartData[0];
    	int titlesCount = chartData[1];
    	int authorisbnAuthorIDCount = chartData[2];
    	int authorisbnISBNCount = chartData[3];
 
        pieModel2 = new PieChartModel();
        
        pieModel2.set("Total Author ", authorsCount);
        pieModel2.set("Total Title ", titlesCount);
        pieModel2.set("Total Author with Title ", authorisbnAuthorIDCount);
        pieModel2.set("Total Title with Author ", authorisbnISBNCount);
        
        pieModel2.set("Total Author with No Title ", authorsCount - authorisbnAuthorIDCount);
        pieModel2.set("Total Title with No Author ", titlesCount - authorisbnISBNCount);
         
        pieModel2.setTitle("Book Data Statistics with Count");
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(false);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(150);
        pieModel2.setShowDataLabels(true);
        
        
    }*/
    
    public String getChart(){
    	return "success";
    }
    
    public String maintainAuthorISBN(){
    	return "success";
    }

	public String searchAuthorISBN(){
		return "success";
	}
	
	public List<AuthorISBN> getAuthorISBNs() {
		return authorISBNs;
	}

	public void setAuthorISBNs(List<AuthorISBN> authorISBNs) {
		this.authorISBNs = authorISBNs;
	}

	public String getRestricteditionNo() {
		return restricteditionNo;
	}

	public void setRestricteditionNo(String restricteditionNo) {
		this.restricteditionNo = restricteditionNo;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Map<String, String> columnNames) {
		this.columnNames = columnNames;
	}

	public int getBetweenMinValue() {
		return betweenMinValue;
	}

	public void setBetweenMinValue(int betweenMinValue) {
		this.betweenMinValue = betweenMinValue;
	}

	public int getBetweenMaxValue() {
		return betweenMaxValue;
	}

	public void setBetweenMaxValue(int betweenMaxValue) {
		this.betweenMaxValue = betweenMaxValue;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getRestrcitionType() {
		return restrcitionType;
	}

	public void setRestrcitionType(String restrcitionType) {
		this.restrcitionType = restrcitionType;
	}

	public String getLikeValue() {
		return likeValue;
	}

	public void setLikeValue(String likeValue) {
		this.likeValue = likeValue;
	}

	public String getNumericValue() {
		return numericValue;
	}

	public void setNumericValue(String numericValue) {
		this.numericValue = numericValue;
	}

	public PieChartModel getPieModel1() {
		getFirstChart();
		return pieModel1;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}

	public PieChartModel getPieModel2() {
		//getSecondChart();
		return pieModel2;
	}

	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
	}
}
