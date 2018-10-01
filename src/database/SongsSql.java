package database;

/**
 * @author Ashton Chatelain
 * <p>
 * A02DatabaseApplication
 * Description : (Narrative description, not code)
 */
public class SongsSql {
    //TODO add a lot of songs for each of these albums

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
                + "('The Story of O.J.', 232, 300, 200), "
                + "('Smile', 289, 300, 200), "
                + "('Caught Their Eyes', 206, 300, 200), "
                + "('4:44', 288, 300, 200), "
                + "('Family Feud', 251, 300, 200), "
                + "('Bam', 235, 300, 200), "
                + "('Moonlight', 144, 300, 200), "
                + "('Marcy Me', 174, 300, 200), "
                + "('Legacy', 177, 300, 200), "
                + "('Rescue', 236, 301, 201),"
                + "('Promise', 176, 301, 201),"
                + "('On the Roof Again', 185, 301, 201),"
                + "('Sunset Strip Bitch', 198, 301, 201),"
                + "('Heres to the Night', 249, 301, 201),"
                + "('Amphetamines', 166, 301, 201),"
                + "('Enemy', 228, 301, 201),"
                + "('Nocturnal', 187, 301, 201),"
                + "('Jet Pack', 213, 301, 201),"
                + "('Nightmare', 187, 301, 201),"
                + "('Bang', 214, 301, 201),"
                + "('Girl Eyes', 225, 301, 201),"
                + "('Oh Baby', 349, 302, 202),"
                + "('Other Voices', 403, 302, 202),"
                + "('I Used To', 332, 302, 202),"
                + "('Change Yr Mind', 297, 302, 202),"
                + "('How Do You Sleep', 552, 302, 202),"
                + "('Tonite', 347, 302, 202),"
                + "('Call the Police', 418, 302, 202),"
                + "('American Dream', 366, 302, 202),"
                + "('Emotional Haircut', 329, 302, 202),"
                + "('Black Screen', 725, 302, 202),"
                + "('Serenade No. 13', 384, 303, 203),"
                + "('5th Symphony in C Minor', 2180, 304, 204)";

    }

    public static String dropTable() {
        return "DROP TABLE SONGS";

    }

    // - - - - - - - - - - - query statements - - - - - - - - - - - - -

    public static String query_All() {
        return "SELECT * FROM SONGS";
    }

    public static String query_SONGS() {
        return "SELECT TITLE as COL FROM SONGS";
    }

}