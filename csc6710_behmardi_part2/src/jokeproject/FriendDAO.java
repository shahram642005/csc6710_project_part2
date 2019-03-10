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

public class FriendDAO 
{
		/* values to connect to the database */
		protected String databaseURL;
		protected String databaseUserName;
		protected String databasePassword;
		private Connection connection;
		
		/* constructors */
		public FriendDAO(String databaseURL, String databaseUserName, String databasePassword)
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
		
		/* drop Friend table */
		public void dropFriendTable() throws SQLException
		{
			connect();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE IF EXISTS Friend");
			statement.close();
			disconnect();
		}
		
		/* create Friend table */
		public void createFriendTable() throws SQLException
		{
			connect();
			Statement statement = connection.createStatement();
			String sqlStatement = "CREATE TABLE IF NOT EXISTS Friend" +
					              "(userId int(20) NOT NULL," +
					              " friendUserId int(20) NOT NULL," +
			                      " PRIMARY KEY (userId, friendUserId)," +
			         	          " FOREIGN KEY (userId) REFERENCES User(userId)," +
					              " FOREIGN KEY (friendUserId) REFERENCES User(userId))"
					              ;
			statement.executeUpdate(sqlStatement);
			statement.close();
			disconnect();
			
		}
		
		/* initialize Friend table */
		public void initFriendTable() throws SQLException
		{			
			insertFriend(new Friend(1,2));
			insertFriend(new Friend(2,3));
			insertFriend(new Friend(3,4));
			insertFriend(new Friend(4,5));
			insertFriend(new Friend(5,6));
			insertFriend(new Friend(6,7));
			insertFriend(new Friend(7,8));
			insertFriend(new Friend(8,9));
			insertFriend(new Friend(9,10));
		}
		
		/* insert a friend to Friend table */
		public boolean insertFriend(Friend friend) throws SQLException
		{
			String sqlInsert = "INSERT INTO Friend (userId, friendUserId) " +
								"VALUES (?, ?)";
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
			preparedStatement.setInt(1, friend.getuserId());
			preparedStatement.setInt(2, friend.getfriendUserId());
			
			
			boolean status = preparedStatement.executeUpdate() > 0;
			preparedStatement.close();
			disconnect();
			
			return status;
		}
		
		/* delete a friend from Friend table */
		public boolean deleteFriend(Friend friend) throws SQLException
		{
			String sqlDelete = "DELETE FROM Friend WHERE userId = ? AND friendUserId = ?";
			connect();
			
			PreparedStatement prepareStatement = connection.prepareStatement(sqlDelete);
			prepareStatement.setInt(1, friend.getuserId());
			prepareStatement.setInt(2, friend.getfriendUserId());
			
			boolean status = prepareStatement.executeUpdate() > 0;
			prepareStatement.close();
			disconnect();
			
			return status;
		}
		
		/* get list of all friends from Friend table */
		public List<Friend> getFriendList(int userId) throws SQLException
		{
			List<Friend> friendList = new ArrayList<Friend>();
			String sqlQuery = "SELECT * FROM Friend WHERE userId=?";
			
			connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, userId);
			
			
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next())
			{		
				int friendUserId = result.getInt("friendUserId");
				
				friendList.add(new Friend(userId, friendUserId));
			}
			
			result.close();
			preparedStatement.close();
			disconnect();
			
			return friendList;
		}
}
