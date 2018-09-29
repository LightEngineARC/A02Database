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
public class SongsArtistsAlbums
{

	    // - - - - - - - - - - - query statements - - - - - - - - - - - - -

		/**
		 * Query all data from songs and add artists and albums when found
		 *
		 */
	    public static String query_All() {
	        return "SELECT SONGS.TITLE, ARTISTS.ARTIST_NAME as ARTIST, ALBUMS.ALBUM_NAME as ALBUM "
	        		+ "FROM SONGS "
	        		+ "Left join artists on artists.artist_id = songs.artist_id "
	        		+ "left join albums on albums.album_id = songs.album_id "
	        		+ "order by songs.title ASC";
	    }

}
