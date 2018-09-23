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
public class AlbumSql
{

	public static String createTable()
	{
		return "CREATE TABLE ALBUM("
				+ "ALBUM_ID INT not null primary key "
				+ "GENERATED ALWAYS AS IDENTITY "
				+ "(start with 200, increment by 1), "
				+ "ALBUM_NAME varchar(255), "
				+ "GENRE varchar(255),"
				+ "RELEASE_YEAR int,"
				+ "ARTIST_ID INT)";
	}

	public static String fillTable()
	{
		return "INSERT INTO ALBUM ("
				+ "ALBUM_NAME, GENRE, RELEASE_YEAR, ARTIST_ID) VALUES"
				+ "('4:44', 'HIP-HOP', 2017, 300),"
				+ "('Horrorscope', 'Rock', 1999, 301),"
				+ "('american dream', 'Rock', 2017, 302),"
				+ "('Symphony No. 13', 'Classical', 1771, 303),"
				+ "('Schicksals-Motive', 'Classical', 1808, 304)";

	}

	public static String dropTable()
	{
		return "DROP TABLE ALBUM";

	}

	// - - - - - - - - - - - query statements - - - - - - - - - - - - -

	// public static String query_nameMAjor()
	// {
	// return "SELECT ID, LastNAme, firstname, major FROM ALBUMs WHERE major='CS'";
	// }

	public static String query_All()
	{
		return "SELECT * FROM ALBUM";
	}
}
