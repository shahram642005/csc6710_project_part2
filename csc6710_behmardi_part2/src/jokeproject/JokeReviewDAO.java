package jokeproject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/***************************************************
 * JokeReviewDAO.java
 * This DAO class provides CRUD operation for JokeReview table
 * @author Gwen Hickey
 *
 ***************************************************/

public class JokeReviewDAO 
{
	/* values to connect to the database */
	protected String databaseURL;
	protected String databaseUserName;
	protected String databasePassword;
	private Connection connection;
	
	/* constructors */
	public JokeReviewDAO(String databaseURL, String databaseUserName, String databasePassword)
	{
		this.databaseURL = databaseURL;
		this.databaseUserName = databaseUserName;
		this.databasePassword = databasePassword;
	}
	
	/* establish connection to database */
	public void connect() throws SQLException
	{
		if (connection == null || connection.isClosed())
		{
            try 
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } 
            catch (ClassNotFoundException e)
            {
                throw new SQLException(e);
            }
            connection = DriverManager.getConnection(databaseURL +  "?" + "user=" + databaseUserName + 
            										 "&password=" + databasePassword + "&useSSL=false");
        }
	}
	
	/* disconnect from the database */
	public void disconnect() throws SQLException
	{
		if (connection != null && !connection.isClosed())
		{
	        connection.close();
	    }
	}
	
	/* drop JokeReview table */
	public void dropJokeReviewTable() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP TABLE IF EXISTS JokeReview");
		statement.close();
		disconnect();
	}
		
	/* create JokeReview table */
	public void createJokeReviewTable() throws SQLException
	{
		connect();
		Statement statement = connection.createStatement();
		String sqlStatement = "CREATE TABLE IF NOT EXISTS JokeReview" +
				              "(reviewJokeId int(20) NOT NULL," +
				              " reviewUserId int(20) NOT NULL," +
		                      " reviewScore varchar(25) DEFAULT NULL," + 
				              " CHECK (reviewScore = 'excellent' or 'good' or 'fair' or 'poor')," +
		                      " reviewRemark varchar(250) DEFAULT NULL ," + 
		                      " reviewDate date DEFAULT NULL," +
				              " favoriteFlag char(1) DEFAULT 'N'," +  
		                      " PRIMARY KEY (reviewUserId, reviewJokeId)," +
				              " FOREIGN KEY (reviewUserId) REFERENCES User (userId)," +
		         	          " FOREIGN KEY (reviewJokeId) REFERENCES Joke (jokeId))" 
				              ;
		statement.executeUpdate(sqlStatement);
		statement.close();
		disconnect();
			
	}
	
	/* initialize JokeReview table */
	public void initJokereviewTable() throws SQLException
	{
		Date date = Date.valueOf(LocalDate.now());
		insertJokeReview(new JokeReview(1, 1, "excellent", null, date, null));
		insertJokeReview(new JokeReview(2, 2, "good", null, date, null));
		insertJokeReview(new JokeReview(3, 3, "fair", null, date, null));
		insertJokeReview(new JokeReview(4, 4, "poor", null, date, null));
		insertJokeReview(new JokeReview(5, 5, "excellent", null, date, null));
		insertJokeReview(new JokeReview(6, 6, "good", null, date, null));
		insertJokeReview(new JokeReview(7, 7, "fair", null, date, null));
		insertJokeReview(new JokeReview(8, 8, "poor", null, date, null));
		insertJokeReview(new JokeReview(9, 9, "excellent", null, date, null));
		insertJokeReview(new JokeReview(10, 10, "good", null, date, null));
	}
	
	/* insert a joke to JokeReview table */
	public boolean insertJokeReview(JokeReview jokeReview) throws SQLException
	{
		String sqlInsert = "INSERT INTO JokeReview (reviewJokeId, reviewUserId, reviewScore, reviewRemark, reviewDate, favoriteFlag) " +
							"VALUES (?, ?, ?, ?, ?, ?)";
		connect();
		PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
		preparedStatement.setInt(1, jokeReview.getreviewJokeId());
		preparedStatement.setInt(2, jokeReview.getreviewUserId());
		preparedStatement.setString(3, jokeReview.getreviewScore());
		preparedStatement.setString(4, jokeReview.getreviewRemark());
		preparedStatement.setDate(5, jokeReview.getreviewDate());
		preparedStatement.setString(6, jokeReview.getfavoriteFlag());
		
		boolean status = preparedStatement.executeUpdate() > 0;
		preparedStatement.close();
		disconnect();
		
		return status;
	}
	
	/* get list of all jokeReviews from jokeReview table */
	public List<JokeReview> getJokeReviewList() throws SQLException
	{
		List<JokeReview> jokeReviewList = new ArrayList<JokeReview>();
		String sqlQuery = "SELECT * FROM JokeReview";
		
		connect();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sqlQuery);
		
		while(result.next())
		{
			int reviewJokeId = result.getInt("reviewJokeId");
			int reviewUserId = result.getInt("reviewUserId");
			String reviewScore = result.getString("reviewScore");
			String reviewRemark = result.getString("reviewRemark");
			Date reviewDate = result.getDate("reviewDate");
			String favoriteFlag = result.getString("favoriteFlag");
			
			jokeReviewList.add(new JokeReview(reviewJokeId, reviewUserId, reviewScore, reviewRemark, reviewDate, favoriteFlag));
		}
		result.close();
		statement.close();
		disconnect();
		
		return jokeReviewList;
	   }
}

