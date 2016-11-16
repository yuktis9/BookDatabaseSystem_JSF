package com.bookdata.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bookdata.bean.Author;
import com.bookdata.bean.AuthorISBN;
import com.bookdata.bean.Title;
import com.bookdata.security.EncryptionDecryptionAES;

public class DBQuery {

	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public boolean checkISBNExist(String ISBN, Connection connect) {

		boolean isISBNExist = false;
		String url = "select * from titles where ISBN=?";
		try {
			preparedStatement = (PreparedStatement) connect
					.prepareStatement(url);
			preparedStatement.setString(1, ISBN);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				isISBNExist = true;
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}

		return isISBNExist;
	}

	public String[] getTableName(Connection connect) {

		String tableNames[] = new String[3];

		String url = "select table_name from information_schema.tables where table_schema='books'";
		try {
			statement = (Statement) connect.createStatement();
			resultSet = statement.executeQuery(url);

			int i = 0;
			while (resultSet.next()) {
				tableNames[i] = resultSet.getString(1);
				i++;
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}

		return tableNames;
	}

	public Author getAuthor(Integer authorId, Connection connect) {

		Author author = new Author();
		String url = "select * from authors where authorID=" + authorId;
		try {
			statement = (Statement) connect.createStatement();
			resultSet = statement.executeQuery(url);

			int i = 0;
			while (resultSet.next()) {
				author.setAuthorId(resultSet.getString(1));
				author.setFirstName(resultSet.getString(2));
				author.setLastName(resultSet.getString(3));
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}

		return author;
	}

	public Title getTitle(Integer isbn, Connection connect) {

		Title title = new Title();
		String url = "select * from titles where ISBN=" + isbn;
		try {
			statement = (Statement) connect.createStatement();
			resultSet = statement.executeQuery(url);

			int i = 0;
			while (resultSet.next()) {
				title.setISBN(resultSet.getString(1));
				title.setTitle(resultSet.getString(2));
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}

		return title;
	}

	public List<Title> searchTitles(String columnName, String searchChar,
			Connection connect) {

		List<Title> titles = new ArrayList<Title>();
		String searchSql = "SELECT * FROM titles WHERE " + columnName
				+ " LIKE ?";
		try {
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			preparedStatement = (PreparedStatement) connect
					.prepareStatement(searchSql);
			preparedStatement.setString(1, searchChar + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Title title = new Title();

				title.setISBN(resultSet.getString(1));
				title.setTitle(resultSet.getString(2));

				titles.add(title);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return titles;
	}

	public List<Author> searchAuthors(String columnName, String searchChar,
			Connection connect) {

		List<Author> authors = new ArrayList<Author>();
		String searchSql = "SELECT * FROM authors WHERE " + columnName
				+ " LIKE ?";
		try {
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			preparedStatement = (PreparedStatement) connect
					.prepareStatement(searchSql);
			preparedStatement.setString(1, searchChar + "%");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Author author = new Author();

				author.setAuthorId(resultSet.getString(1));
				author.setFirstName(resultSet.getString(2));
				author.setLastName(resultSet.getString(3));

				authors.add(author);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	public List<Author> getAuthors(Connection connect) {

		List<Author> authors = new ArrayList<Author>();
		EncryptionDecryptionAES encryptionDecryptionAES = new EncryptionDecryptionAES();
		try {
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from authors");

			while (resultSet.next()) {
				Author author = new Author();

				author.setAuthorId( encryptionDecryptionAES.encrypt(resultSet.getString(1)));
				author.setFirstName(resultSet.getString(2));

				author.setLastName(resultSet.getString(3));

				authors.add(author);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return authors;
	}

	public Map<String,String> getColumnNames(String tableName, Connection connect) {

		Map<String,String> map = new HashMap<String, String>();

		String sql = "select * from " + tableName;

		try {
			statement = (Statement) connect.createStatement();
			resultSet = statement.executeQuery(sql);
			ResultSetMetaData rsmd = resultSet.getMetaData();

			int colNo = rsmd.getColumnCount();

			for (int i = 1; i < colNo + 1; i++) {
				map.put(rsmd.getColumnName(i), rsmd.getColumnName(i));
			}

		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}

		return map;
	}

	public void deleteRow(String tableName, String columnName,
			String selectedIds, Connection connect) {

		String deleteSQL = "DELETE FROM " + tableName + " WHERE " + columnName
				+ " IN (" + selectedIds + ")";
		String deleteAuthorISBN = "DELETE FROM authorisbn WHERE " + columnName
				+ " IN (" + selectedIds + ")";

		try {
			preparedStatement = (PreparedStatement) connect
					.prepareStatement(deleteAuthorISBN);
			preparedStatement = (PreparedStatement) connect
					.prepareStatement(deleteSQL);
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	public void deleteAuthorISBN(String authorID, String ISBN, Connection connect) {

		String deleteAuthorISBN = "DELETE FROM authorisbn WHERE AuthorID=? and ISBN=?";

		try {
			preparedStatement = (PreparedStatement) connect
					.prepareStatement(deleteAuthorISBN);
			preparedStatement.setString(1, authorID);
			preparedStatement.setString(2, ISBN);
			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}
	
	public int addAuthor(String tableName, String firstName, String lastName,
			Connection connect) {

		String addSQL = "INSERT INTO " + tableName
				+ "(`FirstName`, `LastName`) values (?,?)";

		try {
			preparedStatement = (PreparedStatement) connect
					.prepareStatement(addSQL);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);

			return preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		return 0;
	}

	public int addTitle(String ISBN, String title,
			String editionNo, String copyright, Connection connect) {

		String deleteSQL = "INSERT INTO titles"
				+ "(`ISBN`, `Title`, `EditionNumber`, `Copyright`) values (?,?,?,?)";

		try {
			preparedStatement = (PreparedStatement) connect
					.prepareStatement(deleteSQL);
			preparedStatement.setString(1, ISBN);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, editionNo);
			preparedStatement.setString(4, copyright);

			return preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		return 0;
	}

	public List<Title> getTitles(Connection connect) {

		List<Title> titles = new ArrayList<Title>();

		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from titles");

			while (resultSet.next()) {

				Title title = new Title();

				title.setTitle(resultSet.getString(2));
				title.setISBN(resultSet.getString(1));
				title.setEditionNo(Integer.parseInt(resultSet.getString(3)));
				title.setCopyRight(Integer.parseInt(resultSet.getString(4)));

				titles.add(title);
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		return titles;
	}
	
	public List<AuthorISBN> getAuthorISBNs(String authorISBNSql, Connection connect) {

		List<AuthorISBN> authorISBNs = new ArrayList<AuthorISBN>();

		try {
			statement = connect.createStatement();
			Statement statementAuthor = connect.createStatement();
			Statement statementTitle = connect.createStatement();
			resultSet = statement.executeQuery(authorISBNSql);

			while (resultSet.next()) {
				System.out.println("1");
				AuthorISBN authorISBN = new AuthorISBN();
				String authorID = resultSet.getString(1);
				String ISBN = resultSet.getString(2);
				
				ResultSet resultSetAuthor = statementAuthor.executeQuery("select * from authors where authorID="+authorID);
				while (resultSetAuthor.next()) {
					System.out.println("2");
					Author author = new Author();
					
					author.setAuthorId(resultSetAuthor.getString(1));
					author.setFirstName(resultSetAuthor.getString(2));
					author.setLastName(resultSetAuthor.getString(3));
					
					authorISBN.setAuthor(author);
				}
				
				ResultSet resultSetTitle = statementTitle.executeQuery("select * from titles where ISBN="+ISBN);
				
				while (resultSetTitle.next()) {
					
					System.out.println("3");
					Title title = new Title();

					title.setTitle(resultSetTitle.getString(2));
					title.setISBN(resultSetTitle.getString(1));
					title.setEditionNo(Integer.parseInt(resultSetTitle.getString(3)));
					title.setCopyRight(Integer.parseInt(resultSetTitle.getString(4)));
					
					authorISBN.setTitle(title);
				}
				authorISBNs.add(authorISBN);
			}
		} catch (SQLException ex) {
			Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		return authorISBNs;
	}
	
	public void addAuthorISBN(String authorID, String ISBN, Connection connect){
        
        String sql = "INSERT INTO authorisbn (`AuthorID`, `ISBN`) VALUES (?, ?)";
        try {
            preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
            preparedStatement.setString(1,  authorID);
            preparedStatement.setString(2,  ISBN);
            System.out.println("sql: "+sql);
            preparedStatement.executeUpdate();   
        } catch (SQLException ex) {
            Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	public boolean checkAuthorISBNExist(String authorID, String ISBN, Connection connect){
        
		boolean isAuthorISBNExist = false;
        String sql = "select * from authorisbn where AuthorID=? and ISBN=?";
        try {
            preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
            preparedStatement.setString(1,  authorID);
            preparedStatement.setString(2,  ISBN);
            System.out.println("sql: "+sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	isAuthorISBNExist = true;
			}

			
			
        } catch (SQLException ex) {
            Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isAuthorISBNExist;
    }
	
	public int [] getChartData(Connection connect){
		
		int [] chartData = new int[4];
		
        String sql = "SELECT  (SELECT count(*) FROM authors) AS authorsCount, "
        +"(SELECT count(*) FROM titles) AS titlesCount, "
        +"(SELECT count(distinct authorisbn.AuthorID)  FROM authorisbn)as authorisbnAuthorIDCount, "
        +"(SELECT count(distinct authorisbn.ISBN)  FROM authorisbn)as authorisbnISBNCount";
        
        try {
            preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
            System.out.println("sql: "+sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
            	chartData[0] = resultSet.getInt(1);
            	chartData[1] = resultSet.getInt(2);
            	chartData[2] = resultSet.getInt(3);
            	chartData[3] = resultSet.getInt(4);
			}

        } catch (SQLException ex) {
            Logger.getLogger(DBQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chartData;
    }
}
