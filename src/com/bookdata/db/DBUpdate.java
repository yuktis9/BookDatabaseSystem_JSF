package com.bookdata.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bookdata.bean.Author;
import com.bookdata.bean.Title;

public class DBUpdate {

    private PreparedStatement preparedStatement = null;

    public int updateAuthor(Author author, Connection connect){
        
    	String updateSQL = "UPDATE authors SET FirstName=?, LastName=? where AuthorID= ?";
    	
         try {
             preparedStatement = (PreparedStatement) connect.prepareStatement(updateSQL);
             preparedStatement.setString(1, author.getFirstName());
             preparedStatement.setString(2, author.getLastName());
             preparedStatement.setString(3, author.getAuthorId());
             
             return preparedStatement.executeUpdate();
             
         } catch (SQLException ex) {
             Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
         }
         return 0;
     }
    
    public int updateTitle(Title title, Connection connect){
        
    	String updateSQL = "UPDATE titles SET title=?, EditionNumber=?, Copyright=? where ISBN= ?";

         try {
             preparedStatement = (PreparedStatement) connect.prepareStatement(updateSQL);
             preparedStatement.setString(1, title.getTitle());
             preparedStatement.setInt(2, title.getEditionNo());
             preparedStatement.setInt(3, title.getCopyRight());
             preparedStatement.setString(4, title.getISBN());
             
             return preparedStatement.executeUpdate();
             
         } catch (SQLException ex) {
             Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
         }
         return 0;
     }
}
