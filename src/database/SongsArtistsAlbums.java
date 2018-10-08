package database;

/**
 * @author Ashton Chatelain
 * <p>
 * A02Database
 * Description : Queries to be used to sort table data based on choices on the GUI
 */
public class SongsArtistsAlbums {

    // - - - - - - - - - - - query statements - - - - - - - - - - - - -

    /**
     * Query all data from songs and add artists and albums when found
     */
    //FIXME
    public static String query_All() {
        return "SELECT songs.name, artists.name, albums.name"
            + "FROM songs "
            + "LEFT JOIN l_artists_songs.song ON songs.id=l_artists_songs.song " //songs -> link table
            + "LEFT JOIN artists ON l_artists_songs.artist=artist.id " //link table - > artists
            + "LEFT JOIN artists ON artists.id=l_artists_album_versions.artist " //artists -> link table
            + "LEFT JOIN l_artists_album_versions ON l_artists_album_versions.album_version=album_versions.id " //link table -> album_versions
            + "LEFT JOIN album_versions ON album_versions.album=albums.id " //album_versions -> albums DISTINCT
            + "ORDER BY songs.name ASC";
    }

    /**
     * Query all data from songs and add artists and albums when found
     */
    //FIXME
    public static String query_Song_String(String s) {
        return "SELECT SONGS.TITLE, ARTISTS.ARTIST_NAME as ARTIST, ALBUMS.ALBUM_NAME as ALBUM "
            + "FROM SONGS "
            + "Left join artists on artists.artist_id = songs.artist_id "
            + "left join albums on albums.album_id = songs.album_id "
            + "WHERE UPPER(SONGS.TITLE) LIKE UPPER('" + s + "%') "
            + "order by songs.title ASC";
    }

    /**
     * Query all data from albums when found
     */
    //FIXME
    public static String query_Album_String(String album) {
        return "SELECT SONGS.TITLE, ARTISTS.ARTIST_NAME as ARTIST, ALBUMS.ALBUM_NAME as ALBUM "
            + "FROM SONGS "
            + "Left join artists on artists.artist_id = songs.artist_id "
            + "left join albums on albums.album_id = songs.album_id "
            + "WHERE UPPER(ALBUMS.ALBUM_NAME) LIKE UPPER('" + album + "%') "
            + "order by songs.title ASC";
    }

    /**
     * Query all data from artists when found
     */
    public static String query_Artist_String(String artist) {
        return "SELECT SONGS.TITLE, ARTISTS.ARTIST_NAME as ARTIST, ALBUMS.ALBUM_NAME as ALBUM "
            + "FROM SONGS "
            + "Left join artists on artists.artist_id = songs.artist_id "
            + "left join albums on albums.album_id = songs.album_id "
            + "WHERE UPPER(ARTISTS.ARTIST_NAME) LIKE UPPER('" + artist + "%') "
            + "order by songs.title ASC";
    }

    /**
     * Query all data from albums when found
     */
    public static String query_ART_ALB_String(String artist, String album) {
        return "SELECT SONGS.TITLE, ARTISTS.ARTIST_NAME as ARTIST, ALBUMS.ALBUM_NAME as ALBUM "
            + "FROM SONGS "
            + "Left join artists on artists.artist_id = songs.artist_id "
            + "left join albums on albums.album_id = songs.album_id "
            + "WHERE ARTISTS.artist_name = '"+artist+"' "
            + "WHERE albums.album_name = '"+album+"' "
            + "order by songs.title ASC";
    }

}
