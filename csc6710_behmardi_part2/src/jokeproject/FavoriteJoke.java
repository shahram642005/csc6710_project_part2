package jokeproject;

/***************************************************
 * Friend.java
 * This is a model class representing a friend entity
 * @author Gwen Hickey
 *
 ***************************************************/


public class FavoriteJoke 
{
	/* attributes of Friend class */
	protected int userId;
	protected int jokeId;
	
    /* constructors */
    public FavoriteJoke() 
    {
    }

    public FavoriteJoke(int userId, int jokeId)
	{
		this.userId = userId;
		this.jokeId = jokeId;
	}
    
    /* define accessors and setters methods */
    
    //userId
	public int getuserId()
	{
		return userId;
	}
	public void setuserId(int userId)
	{
		this.userId = userId;
	}
	
	//jokeId
	public int getjokeId()
	{
		return jokeId;
	}
	public void setjokeId(int jokeId)
	{
		this.jokeId = jokeId;
	}
}
