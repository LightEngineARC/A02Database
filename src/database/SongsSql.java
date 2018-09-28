package database;

/**
 * @author Ashton Chatelain
 * <p>
 * A02DatabaseApplication
 * Description : (Narrative description, not code)
 */
public class SongsSql {

    public static String createTable() {
        return "CREATE TABLE SONGS("
                + "SONG_ID INT not null primary key "
                + "GENERATED ALWAYS AS IDENTITY"
                + "(start with 100, increment by 1),"
                + "TITLE varchar(255),"
                + "LENGTH int,"
                + "ARTIST_ID int,"
                + "ALBUM_ID int)";
    }

    public static String fillTable() {
        return "INSERT INTO SONGS ("
                + "TITLE, LENGTH, ARTIST_ID, ALBUM_ID) VALUES"
                + "('Kill Jay Z', 178, 300, 200),"
                + "('Heres to the Night', 249, 301, 201),"
                + "('tonite', 347, 302, 202),"
                + "('Serenade No. 13', 384, 303, 203),"
                + "('5th Symphony in C Minor', 2180, 304, 204)";

    }

    public static String dropTable() {
        return "DROP TABLE SONGS";

    }

    // - - - - - - - - - - - query statements - - - - - - - - - - - - -
    // TODO change this to something useful like title and artist and genre
    // public static String query_nameMAjor()
    // {
    // return "SELECT ID, LastNAme, firstname, major FROM Songs WHERE major='CS'";
    // }

    public static String query_All() {
        return "SELECT * FROM SONGS";
    }

}
