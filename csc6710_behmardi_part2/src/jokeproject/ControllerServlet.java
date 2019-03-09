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
import javax.servlet.http.HttpSession;

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
            case "/logoutUser":
            	logoutUser(request, response);
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
            case "/searchJoke":
            	//searchJoke(request, response);
                //break;
            case "/reviewJoke":
            	//reviewJoke(request, response);
            	//break;
            case "/banUser":
                //banUser(request, response);
                //break;
            case "/unbanUser":
            	//unbanUser(request, response);
                //break;
            default:
            	throw new ServletException("The action \"" + action + "\" has not been implemented yet!");
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
		
		/* show a message indicating successful logout */
		String message = "All tables are successfully initialized!";
		String color = "green";
		request.setAttribute("message", message);
		request.setAttribute("color", color);
		
		/* go to login page */
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		dispatcher.forward(request, response);
	}
	
	/* go to registration form */
	private void goToRegisterForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* determine form variables */
		String formAction = new String("registerUser");
		String formText = new String("Please insert your account information:");
		String buttonText = new String("register");
		
		/* fill the form values of the Resigtration.jsp with the user info */
		request.setAttribute("formAction", formAction);
		request.setAttribute("formText", formText);
		request.setAttribute("buttonText", buttonText);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
        dispatcher.forward(request, response);
	}
	
	/* insert a user into User table */
	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the user information from the registration form */
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
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
		
		if (!password.isEmpty() && password.equals(confirmPassword)) /* successful match between password and its confirmation */
		{
			/* insert the user to database */
			userDAO.insertUser(user);
	
			/* go to the login page */
			RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			/* show a message indicating successful logout */
			String message = "Password mismatch!";
			String color = "red";
			request.setAttribute("message", message);
			request.setAttribute("color", color);
			
			/* determine form variables */
			user.password = "";
			request.setAttribute("user", user);
			String formAction = new String("registerUser");
			String formText = new String("Please insert your account information:");
			String buttonText = new String("register");
			
			/* fill the form values of the Resigtration.jsp with the user info */
			request.setAttribute("formAction", formAction);
			request.setAttribute("formText", formText);
			request.setAttribute("buttonText", buttonText);
			
			/* go to the registration page */
			RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
			dispatcher.forward(request, response);
		}
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
			/* show a message indicating successful logout */
			String message = "The user name or password is not correct, please try again!";
			String color = "red";
			request.setAttribute("message", message);
			request.setAttribute("color", color);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
			dispatcher.forward(request, response);
		}
		else /* successful login */
		{
			/* add userId to session */
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getUserId());
			
			/* go to the UserAccount in the browser */
			listJokes(request, response);
		}		
	}
	
	/* check if user is in database and it's user name and password match the User table */
	private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* remove userId from session */
		HttpSession session = request.getSession();
		session.removeAttribute("userId");
		session.invalidate();
		
		/* show a message indicating successful logout */
		String message = "You are successfully logged out!";
		String color = "green";
		request.setAttribute("message", message);
		request.setAttribute("color", color);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
		dispatcher.forward(request, response);		
	}
	
	/* list users */
	private void listUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the list of users from database */
		List<User> userList = userDAO.getUserList();
		
		/* show the list of users to root user */
		request.setAttribute("userList", userList);
		
		/* refresh the page */
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserAccount.jsp");
        dispatcher.forward(request, response);
	}
	
	/* delete a user */
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get the userId from the request */
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		/* delete the user if it's not the root */
		if (userId != 1)
		{
			userDAO.deleteUser(userId);
		}
		
		/* list the users in the browser */
		listUsers(request, response);
	}
	
	/* go to User edit form */
	private void goToUserEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the userId of the user */
		int userId = Integer.parseInt(request.getParameter("userId"));
		User user = new User();
		
		if (sessionUserId == 1) /* if it's the root user trying to modify */
		{
			/* get the user information based on the userId */
			user = userDAO.getUser(userId);
		}
		else /* the current logged in user is trying to modify its profile */
		{			
			/* get the user information based on the userId */
			user = userDAO.getUser(sessionUserId);
		}
			
		/* determine form variables */
		String formAction = new String("updateUser");
		String formText = new String("Please modify your account information:");
		String buttonText = new String("save");
		
		/* fill the form values of the Resigtration.jsp with the user info */
		request.setAttribute("user", user);
		request.setAttribute("confirmPassword", user.getPassword());
		request.setAttribute("formAction", formAction);
		request.setAttribute("formText", formText);
		request.setAttribute("buttonText", buttonText);
		
		/* send the updated Registration.jsp to the browser */
        RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
        dispatcher.forward(request, response);
	}
	
	/* update user information */
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the userId of the user */
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		/* if it's the current user trying to edit his profile or it's the root user */
		if (sessionUserId == userId || sessionUserId == 1)
		{
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String confirmPassword = request.getParameter("confirmPassword");
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
			
			if (!password.isEmpty() && password.equals(confirmPassword)) /* successful match between password and its confirmation */
			{
				/* update the user information in the database */
				userDAO.updateUser(user);
				
				if (sessionUserId == 1) /* if it's the root user */
				{
					/* list the users in the browser */
					listUsers(request, response);
				}
				else
				{
					listJokes(request, response);
				}				
			}
			else
			{
				/* show a message indicating successful logout */
				String message = "Password mismatch!";
				String color = "red";
				request.setAttribute("message", message);
				request.setAttribute("color", color);
				
				/* determine form variables */
				user.password = "";
				request.setAttribute("user", user);
				String formAction = new String("updateUser");
				String formText = new String("Please modify your account information:");
				String buttonText = new String("save");
				
				/* fill the form values of the Resigtration.jsp with the user info */
				request.setAttribute("formAction", formAction);
				request.setAttribute("formText", formText);
				request.setAttribute("buttonText", buttonText);
				
				/* go to the registration page */
				RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
				dispatcher.forward(request, response);
			}
		}
		else /* if somebody is trying to modify somebody's else profile */
		{
			/* go to the registration page */
			RequestDispatcher dispatcher = request.getRequestDispatcher("logoutUser");
			dispatcher.forward(request, response);
		}
	}
	
	/* go to joke posting form */
	private void goToJokePostForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* determine form variables */
		String formAction = new String("postJoke");
		String formText = new String("Please insert your joke:");
		String buttonText = new String("post");
		String gender = user.getGender();
		
		/* fill the form values of the JokePost.jsp with the user info */
		request.setAttribute("formAction", formAction);
		request.setAttribute("formText", formText);
		request.setAttribute("buttonText", buttonText);
		request.setAttribute("gender", gender);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("JokePost.jsp");
        dispatcher.forward(request, response);
	}
	
	/* insert a joke into Joke table */
	private void postJoke(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the joke information from the registration form */		
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Date date = Date.valueOf(LocalDate.now());
		
		/* create a user instance with the provided data and insert it into database */
		Joke joke = new Joke(title, description, date, sessionUserId);
		jokeDAO.insertJoke(joke);

		/* list the jokes in the browser */
		listJokes(request, response);
	}
	
	/* list jokes */
	private void listJokes(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* get the list of users from database */
		List<Joke> jokeList = jokeDAO.getUserJokes(sessionUserId);
		if (jokeList.isEmpty())
		{
			jokeList = null;
		}
		
		/* show a message on top of the table */
		String message;
		String color;
		if (jokeList == null)
		{
			message = "You currently have no jokes, please post a joke!";
			color = "red";
		}
		else
		{
			message = "List of all your jokes:";
			color = "green";
		}

		/* show the list of user's jokes */
		String gender = user.getGender();
		request.setAttribute("user", user);
		request.setAttribute("userDAO", userDAO);
		request.setAttribute("jokeList", jokeList);
		request.setAttribute("gender", gender);
		request.setAttribute("message", message);
		request.setAttribute("color", color);
		
		/* refresh the page */
        RequestDispatcher dispatcher = request.getRequestDispatcher("UserAccount.jsp");
        dispatcher.forward(request, response);
	}
	
	/* delete a joke */
	private void deleteJoke(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the jokeId and its owner userId from the request */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		int jokeUserId = Integer.parseInt(request.getParameter("postUserId"));
		
		/* check if the session's user is the owner of the joke or it's the root user */
		if (sessionUserId == jokeUserId || sessionUserId == 1)
		{
			jokeDAO.deleteJoke(jokeId);
		}
		
		/* list the jokes in the browser */
		listJokes(request, response);
	}
	
	/* go to joke form */
	private void goToJokeEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		User user = userDAO.getUser(sessionUserId);
		
		/* get the jokeId and its owner userId from the request */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		int jokeUserId = Integer.parseInt(request.getParameter("postUserId"));
		
		/* check if the user is the owner of the joke */
		if (sessionUserId == jokeUserId)
		{
			/* get the user information based on the userId */
			Joke joke = jokeDAO.getJoke(jokeId);
			
			/* determine form variables */
			String formAction = new String("updateJoke");
			String formText = new String("Please modify your joke:");
			String buttonText = new String("save");
			String gender = user.getGender();
			
			/* fill the form values of the JokePost.jsp with the user info */
			request.setAttribute("joke", joke);
			request.setAttribute("formAction", formAction);
			request.setAttribute("formText", formText);
			request.setAttribute("buttonText", buttonText);
			request.setAttribute("gender", gender);
			
			/* send the updated JokePost.jsp to the browser */
	        RequestDispatcher dispatcher = request.getRequestDispatcher("JokePost.jsp");
	        dispatcher.forward(request, response);
		}
		else /* if it's not the joke owner, do nothing */
		{
			/* list the jokes in the browser */
			listJokes(request, response);
		}
	}
	
	/* update a joke in Joke table */
	private void updateJoke(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException
	{
		/* get userId from session */
		HttpSession session = request.getSession();
		int sessionUserId = Integer.parseInt(session.getAttribute("userId").toString());
		
		/* get the joke information from the registration form */
		int jokeId = Integer.parseInt(request.getParameter("jokeId"));
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Date date = Date.valueOf(LocalDate.now());
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		/* create a user instance with the provided data and insert it into database */
		Joke joke = new Joke(jokeId, title, description, date, userId);
		
		/* check if the user is the owner of the joke */
		if (sessionUserId == userId)
		{
			jokeDAO.updateJoke(joke);
		}

		/* list the users in the browser */
		listJokes(request, response);
	}
}