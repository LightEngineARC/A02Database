package dataHandling;

import database.MusicDatabase;

import java.sql.SQLException;

public class Test {
    public static void main (String[] args) {
        MusicDatabase db = new MusicDatabase();
        try {
//            db.executeQueries("SELECT artists.name, songs.name, albums.name FROM artists " +
//                "LEFT JOIN l_artists_songs ON l_artists_songs.artist = artists.id " +
//                "LEFT JOIN songs ON songs.id = l_artists_songs.song " +
//                "LEFT JOIN l_songs_album_versions ON l_songs_album_versions.song = songs.id " +
//                "LEFT JOIN album_versions ON album_versions.id = l_songs_album_versions.album_version " +
//                "LEFT JOIN albums ON albums.id = album_versions.album " +
//                "WHERE albums.name NOT LIKE '%null%'");
//            db.executeSqlStatement("CREATE VIEW all_content (artist, album, song) AS " +
//                    "SELECT DISTINCT artists.name, album_versions.name, songs.name FROM album_versions " +
//                    "INNER JOIN l_artists_album_versions ON l_artists_album_versions.album_version = album_versions.id " +
//                    "INNER JOIN artists ON artists.id = l_artists_album_versions.artist " +
//                    "INNER JOIN l_artists_songs ON l_artists_songs.artist = artists.id " +
//                    "INNER JOIN songs ON songs.id = l_artists_songs.song " +
//                    "ORDER BY artists.name");
//            db.executeQueries("SELECT * FROM all_content FETCH FIRST 1000 ROWS ONLY");
            db.executeSqlStatement(
                    "create index indLaav on l_artists_album_versions (artist, album_version)",
                    "create index indLas on l_artists_songs (artist, song)",
                    "create index indAV on album_versions (name, album)",
                    "create index  indArtists on artists (name)",
                    "create index  indSongs on SONGS (name)"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}