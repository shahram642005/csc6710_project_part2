package jokeproject;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List; 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***************************************************
 * UserDAO.java
 * This class provides the servlet functionality
 * @author Shahram Behmardi Kalantari
 *
 ***************************************************/
public class ControllerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private JokeDAO jokeDAO;
	private FriendDAO friendDAO;
	private JokeReviewDAO jokeReviewDAO;
	private JokeTagDAO jokeTagDAO;
	
	/* implement the init method that runs once for the life cycle of servlet */
	public void init()
	{
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
        
        friendDAO = new FriendDAO(jdbcURL, jdbcUsername, jdbcPassword);
		userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);
		jokeDAO = new JokeDAO(jdbcURL, jdbcUsername, jdbcPassword);
		jokeReviewDAO = new JokeReviewDAO(jdbcURL, jdbcUsername, jdbcPassword);
		jokeTagDAO = new JokeTagDAO(jdbcURL, jdbcUsername, jdbcPassword);
		//userDAO = new UserDAO("jdbc:mysql://127.0.0.1:3306/sampledb", "john", "pass1234");
	}
	
	/* implement the doPost method */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        doGet(request, response);
    }
	
	/* implement the doGet method */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        String action = request.getServletPath();
        
        try {
            switch (action) {
            case "/initTables":
            	initializeDatabase(request, response);
            	break;
            case "/newUser":
            	goToRegisterForm(request, response);
                break;
            case "/loginUser":
            	loginUser(request, response);
            	break;
            case "/registerUser":
                registerUser(request, response);
                break;
            case "/listUsers":
            	listUsers(request, response);
            	break;
            case "/deleteUser":
                deleteUser(request, response);
                break;
            case "/modifyUser":
                goToUserEditForm(request, response);
                break;
            case "/updateUser":
                updateUser(request, response);
                break;
            case "/newJoke":
            	goToJokePostForm(request, response);
                break;
            case "/reviewJoke":
            	//reviewUser(request, response);
            	break;
            case "/postJoke":
                postJoke(request, response);
                break;
            case "/listJokes":
            	listJokes(request, response);
            	break;
            case "/deleteJoke":
                deleteJoke(request, response);
                break;
            case "/modifyJoke":
                goToJokeEditForm(request, response);
                break;
            case "/updateJoke":
                updateJoke(request, response);
                break;
            default:
                break;
            }
        }
        catch (SQLException ex)
        {
            throw new ServletException(ex);
        }
    }
	
	/* insert a user into User table */
	private void initializeDatabase(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the rootUser parameter value */
		String isRootUser = request.getParameter("rootUser");
		
		/* create all the database tables and populate them with tuples */
		jokeTagDAO.dropJokeTagTable();
		jokeReviewDAO.dropJokeReviewTable();
		friendDAO.dropFriendTable();
		jokeDAO.dropJokeTable();
		userDAO.dropUserTable();
		userDAO.createUserTable();
		jokeDAO.createJokeTable();
		friendDAO.createFriendTable();		
		jokeReviewDAO.createJokeReviewTable();
		jokeTagDAO.createJokeTagTable();
		userDAO.initUserTable();		
		jokeDAO.initJokeTable();		
		friendDAO.initFriendTable();
		jokeReviewDAO.initJokereviewTable();
		jokeTagDAO.initJokeTagTable();
		
		/* show the text informing the tables are initialized */
		String showInitMsg = "";
		request.setAttribute("showInitMsg", showInitMsg);
		
		if (isRootUser.equals("TRUE"))
		{
			/* refresh page */
			RequestDispatcher dispatcher = request.getRequestDispatcher("Admin.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			/* go to login page */
			RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	/* go to registration form */
	private void goToRegisterForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
        dispatcher.forward(request, response);
	}
	
	/* insert a user into User table */
	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the user information from the registration form */
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String ageStr = request.getParameter("age");
		int age = 0;
		if (!ageStr.isEmpty()) /* make sure ageStr is not empty */
		{
			age = Integer.parseInt(ageStr);
		}
		
		/* create a user instance with the provided data and insert it into database */
		User user = new User(userName, password, firstName, lastName, email, gender, age);
		userDAO.insertUser(user);

		/* go to the login page */
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		dispatcher.forward(request, response);
	}
	
	/* check if user is in database and it's user name and password match the User table */
	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userName and password from Login Form */
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		/* create a new User object and put the user information inside it */
		User user = new User();
		user = userDAO.getUser(userName);
		
		/* check the credential of the user */
		if (userName == null || password == null || user == null || !password.equals(user.password)) /* unsuccessful login */
		{
			String showErrMsg = "";
			
			request.setAttribute("showErrMsg", showErrMsg);
			RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
			dispatcher.forward(request, response);
		}
		else /* successful login */
		{
			if (userName.equals("root"))	/* if it's the root user */
			{
				RequestDispatcher dispatcher = request.getRequestDispatcher("Admin.jsp");
				dispatcher.forward(request, response);
			}
			else /* if it's not a root user */
			{
				/* go to the UserAccount in the browser */
				response.sendRedirect("listJokes");
			}
		}		
	}
	
	/* list users */
	private void listUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the list of users from database */
		List<User> userList = userDAO.getUserList();
		
		/* show the list of users in userList value of Admin.jsp */
		request.setAttribute("userList", userList);
		
		/* refresh the page */
        RequestDispatcher dispatcher = request.getRequestDispatcher("Admin.jsp");
        dispatcher.forward(request, response);
	}
	
	/* delete a user */
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		/* get the userId from the request */
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		/* delete the user if it's not the root */
		if (userId != 1)
		{
			userDAO.deleteUser(userId);
		}
		
		/* list the users in the browser */
		response.sendRedirect("listUsers");
	}
	
	/* go to User edit form */
	private void goToUserEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the userId of the user */
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		if (userId != 1) /* if it's not the root */
		{
			/* get the user information based on the userId */
			User user = userDAO.getUser(userId);
			
			/* fill the form values of the Resigtration.jsp with the user info */
			request.setAttribute("user", user);
			
			/* send the updated Registration.jsp to the browser */
	        RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
	        dispatcher.forward(request, response);
		}
		else /* if it's the root user, do nothing */
		{
			/* list the users in the browser */
			response.sendRedirect("listUsers");
		}
	}
	
	/* update user information */
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the userId of the user */
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		if (userId != 1) /* if it's not the root */
		{
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String gender = request.getParameter("gender");
			String ageStr = request.getParameter("age");
			int age = 0;
			if (!ageStr.isEmpty()) /* make sure ageStr is not empty */
			{
				age = Integer.parseInt(ageStr);
			}
			User user = new User(userId, userName, password, firstName, lastName, email, gender, age);
			
			/* update the user information in the database */
			userDAO.updateUser(user);
		}

		/* list the users in the browser */
		response.sendRedirect("listUsers");
	}
	
	/* go to joke posting form */
	private void goToJokePostForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("JokePost.jsp");
        dispatcher.forward(request, response);
	}
	
	/* insert a joke into Joke table */
	private void postJoke(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the joke information from the registration form */		
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Date date = Date.valueOf(LocalDate.now());
		int userId = 1; /********** should be changed to current session's user */
		
		/* create a user instance with the provided data and insert it into database */
		Joke joke = new Joke(title, description, date, userId);
		jokeDAO.insertJoke(joke);

		/* list the users in the browser */
		response.sendRedirect("listJokes");
	}
	
	/* list jokes */
	private void listJokes(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the list of users from database */
		List<Joke> jokeList = jokeDAO.getJokeList();
		
		/* show the list of users in userList value of Admin.jsp */
		User user= new User();
		request.setAttribute("user", user); /********** should be changed to current session's user */
		request.setAttribute("jokeList", jokeList);
		
		/* refresh the page */
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserAccount.jsp");
        dispatcher.forward(request, response);
	}
	
	/* delete a joke */
	private void deleteJoke(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException
	{
		/* get the jokeId and its owner userId from the request */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		int jokeUserId = Integer.parseInt(request.getParameter("postUserId"));
		
		/* check if the user is the owner of the joke */
		if (jokeUserId == 1) /********** should be changed to current session's user */
		{
			jokeDAO.deleteJoke(jokeId);
		}
		
		/* list the users in the browser */
		response.sendRedirect("listJokes");
	}
	
	/* go to joke form */
	private void goToJokeEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the jokeId and its owner userId from the request */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		int jokeUserId = Integer.parseInt(request.getParameter("postUserId"));
		
		/* check if the user is the owner of the joke */
		if (jokeUserId == 1) /********** should be changed to current session's user */
		{
			/* get the user information based on the userId */
			Joke joke = jokeDAO.getJoke(jokeId);
			
			/* fill the form values of the the joke info */
			request.setAttribute("joke", joke);
			
			/* send the updated Registration.jsp to the browser */
	        RequestDispatcher dispatcher = request.getRequestDispatcher("JokePost.jsp");
	        dispatcher.forward(request, response);
		}
		else /* if it's not the joke owner, do nothing */
		{
			/* list the users in the browser */
			response.sendRedirect("listJokes");
		}
	}
	
	/* update a joke in Joke table */
	private void updateJoke(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the joke information from the registration form */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Date date = Date.valueOf(LocalDate.now());
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		/* create a user instance with the provided data and insert it into database */
		Joke joke = new Joke(jokeId, title, description, date, userId);
		
		/* check if the user is the owner of the joke */
		if (userId == 1) /********** should be changed to current session's user */
		{
			jokeDAO.updateJoke(joke);
		}

		/* list the users in the browser */
		response.sendRedirect("listJokes");
	}
}