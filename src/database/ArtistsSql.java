/**
 *  @author Ashton Chatelain
 *  
 *  A02DatabaseApplication
 *  Description : (Narrative description, not code)
 */
package database;

/**
 * @author Ashton Chatelain
 *
 */
public class ArtistsSql
{

	public static String createTable()
	{
		return "CREATE TABLE ARTIST("
				+ "ARTIST_ID INT not null primary key "
				+ "GENERATED ALWAYS AS IDENTITY"
				+ "(start with 300, increment by 1),"
				+ "ARTIST_NAME varchar(255))";
	}

	public static String fillTable()
	{
		return "INSERT INTO ARTIST ("
				+ "ARTIST_NAME) VALUES"
				+ "('JAY-Z'),"
				+ "('Eve 6'),"
				+ "('LCD Soundsystem'),"
				+ "('Wolfgang Amadeas Mozart'),"
				+ "('Ludwin van Beethoven')";

	}

	public static String dropTable()
	{
		return "drop TABLE ARTIST";

	}

	// - - - - - - - - - - - query statements - - - - - - - - - - - - -

	// public static String query_nameMAjor()
	// {
	// return "SELECT ID, LastNAme, firstname, major FROM Artists WHERE major='CS'";
	// }

	public static String query_All()
	{
		return "SELECT * FROM ARTIST";
	}

}
