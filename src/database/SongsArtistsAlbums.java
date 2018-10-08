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
        return "SELECT * from all_content fetch first 1000 rows only";
    }

    /**
     * Query all data from songs and add artists and albums when found
     */
    //FIXME
    public static String query_Song_String(String song) {
        return "SELECT artist, album, song from all_content "
                + "WHERE UPPER(song) LIKE UPPER('%" + song + "%') "
                + "order by song ASC";
    }

    /**
     * Query all data from albums when found
     */
    //FIXME
    public static String query_Album_String(String album) {
        return "SELECT artist, album, song from all_content "
                + "WHERE UPPER(album) LIKE UPPER('%" + album + "%') "
                + "order by album ASC";
    }

    /**
     * Query all data from artists when found
     */
    public static String query_Artist_String(String artist) {
        return "SELECT artist, album, song from all_content "
                + "WHERE UPPER(artist) LIKE UPPER('%" + artist + "%') "
                + "order by artist ASC";
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
    public static String createViewAll(){
        return "CREATE VIEW all_content (artist, album, song) AS " +
                "SELECT DISTINCT artists.name, album_versions.name, songs.name FROM album_versions " +
                "left JOIN l_artists_album_versions ON l_artists_album_versions.album_version = album_versions.id " +
                "left JOIN artists ON artists.id = l_artists_album_versions.artist " +
                "left JOIN l_artists_songs ON l_artists_songs.artist = artists.id " +
                "left JOIN songs ON songs.id = l_artists_songs.song " +
                "ORDER BY artists.name";
    }

}
