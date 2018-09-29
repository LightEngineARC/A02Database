/**
 *  @author Ashton Chatelain
 *  
 *  A02Database
 *  Description : Queries to be used to sort table data based on choices on the GUI
 */
package database;

/**
 * @author Ashton Chatelain
 *
 */
public class SongsArtistsAlbum
{

	    // - - - - - - - - - - - query statements - - - - - - - - - - - - -

		/**
		 * Query all data from songs and add artists and albums when found
		 *
		 */
	    public static String query_All() {
	        return "SELECT SONGS.TITLE, ARTIST.ARTIST_NAME, ALBUM.ALBUM_NAME "
	        		+ "FROM SONGS "
	        		+ "Left join artist on artist.artist_id = songs.artist_id "
	        		+ "left join album on album.album_id = songs.album_id "
	        		+ "order by songs.title ASC";
	    }

}
