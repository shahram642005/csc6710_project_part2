package jokeproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/***************************************************
 * JokeTagDAO.java
 * This DAO class provides CRUD operation for JokeTag table
 * @author Gwen Hickey
 *
 ***************************************************/

public class FavoriteJokeDAO 
{
		/* values to connect to the database */
		protected String databaseURL;
		protected String databaseUserName;
		protected String databasePassword;
		private Connection connection;
		
		/* constructors */
		public FavoriteJokeDAO(String databaseURL, String databaseUserName, String databasePassword)
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
		
		/* drop FavoriteJoke table */
		public void dropFavoriteJokeTable() throws SQLException
		{
			connect();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS FavoriteJoke");
			statement.close();
			disconnect();
		}
		
		/* create FavoriteJoke table */
		public void createFavoriteJokeTable() throws SQLException
		{
			connect();
			Statement statement = connection.createStatement();
			String sqlStatement = "CREATE TABLE IF NOT EXISTS FavoriteJoke" +
					              "(userId int(20) NOT NULL," +
					              " jokeId int(20) NOT NULL," +
			                      " PRIMARY KEY (userId, jokeId)," +
			         	          " FOREIGN KEY (userId) REFERENCES User(userId)," +
					              " FOREIGN KEY (jokeId) REFERENCES Joke(jokeId))"
					              ;
			statement.executeUpdate(sqlStatement);
			statement.close();
			disconnect();
			
		}
		
		/* initialize FavoriteJoke table */
		public void initFavoriteJokeTable() throws SQLException
		{			
			insertFavoriteJoke(new FavoriteJoke(1,2));
			insertFavoriteJoke(new FavoriteJoke(2,3));
			insertFavoriteJoke(new FavoriteJoke(3,4));
			insertFavoriteJoke(new FavoriteJoke(4,5));
			insertFavoriteJoke(new FavoriteJoke(5,6));
			insertFavoriteJoke(new FavoriteJoke(6,7));
			insertFavoriteJoke(new FavoriteJoke(7,8));
			insertFavoriteJoke(new FavoriteJoke(8,9));
			insertFavoriteJoke(new FavoriteJoke(9,10));
		}
		
		/* insert a joke to FavoriteJoke table */
		public boolean insertFavoriteJoke(FavoriteJoke favoriteJoke) throws SQLException
		{
			String sqlInsert = "INSERT INTO FavoriteJoke (userId, jokeId) " +
								"VALUES (?, ?)";
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setInt(1, favoriteJoke.getuserId());
			preparedStatement.setInt(2, favoriteJoke.getjokeId());
			
			
			boolean status = preparedStatement.executeUpdate() > 0;
			preparedStatement.close();
			disconnect();
			
			return status;
		}
		
		/* delete a joke from FavoriteJoke table */
		public boolean deleteFavoriteJoke(FavoriteJoke favoriteJoke) throws SQLException
		{
			String sqlDelete = "DELETE FROM FavoriteJoke WHERE userId = ? AND jokeId = ?";
			connect();
			
			PreparedStatement prepareStatement = connection.prepareStatement(sqlDelete);
			prepareStatement.setInt(1, favoriteJoke.getuserId());
			prepareStatement.setInt(2, favoriteJoke.getjokeId());
			
			boolean status = prepareStatement.executeUpdate() > 0;
			prepareStatement.close();
			disconnect();
			
			return status;
		}
		
		/* get list of all favorite jokes from FavoriteJoke table */
		public List<Joke> getFavoriteJokeList(int userId) throws SQLException
		{
			List<Joke> jokeList = new ArrayList<Joke>();
			String sqlQuery = "SELECT * FROM FavoriteJoke WHERE userId=?";
			
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, userId);
			
			
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next())
			{		
				int jokeId = result.getInt("jokeId");
				
				jokeList.add(new Joke(jokeId));
			}
			
			result.close();
			preparedStatement.close();
			disconnect();
			
			return jokeList;
		}
}
