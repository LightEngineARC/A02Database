package database;

/**
 * @author Ashton Chatelain
 * <p>
 * A02DatabaseApplication
 * Description : (Narrative description, not code)
 */
public class ArtistsSql {

    public static String createTable() {
        return "CREATE TABLE ARTISTS("
                + "ARTIST_ID INT not null primary key "
                + "GENERATED ALWAYS AS IDENTITY"
                + "(start with 300, increment by 1),"
                + "ARTIST_NAME varchar(255))";
    }

    public static String fillTable() {
        return "INSERT INTO ARTISTS ("
                + "ARTIST_NAME) VALUES"
                + "('JAY-Z'),"
                + "('Eve 6'),"
                + "('LCD Soundsystem'),"
                + "('Wolfgang Amadeas Mozart'),"
                + "('Ludwin van Beethoven')";

    }

    public static String dropTable() {
        return "drop TABLE ARTISTS";

    }

    // - - - - - - - - - - - query statements - - - - - - - - - - - - -

    // public static String query_nameMAjor()
    // {
    // return "SELECT ID, LastNAme, firstname, major FROM Artists WHERE major='CS'";
    // }

    public static String query_All() {
        return "SELECT * FROM ARTISTS";
    }

    public static String query_Artists() {
        return "SELECT ARTIST_NAME as COL FROM ARTISTS order by COL ASC";
    }

}
