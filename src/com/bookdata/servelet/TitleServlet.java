package com.bookdata.servelet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookdata.db.DBQuery;

/**
 * Servlet implementation class TitleServlet
 */
@WebServlet("/addTitle")
public class TitleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TitleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("indise get");
		RequestDispatcher view = request.getRequestDispatcher("viewPage/addTitle.html");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DBQuery dbQuery = new DBQuery();
		
		String ISBN = request.getParameter("ISBN");
		String title = request.getParameter("title");
		String editionNo = request.getParameter("editionNo");
		String copyRight = request.getParameter("copyRight");
		
		dbQuery.addTitle(ISBN, title, editionNo, copyRight,(Connection) request.getSession().getAttribute("dbConnection"));
		
		RequestDispatcher view = request.getRequestDispatcher("viewPage/addTitle.xhtml");
		request.setAttribute("Success", "Title has been added successfully!");
		view.forward(request, response);
	}

}
