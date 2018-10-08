package database;

import dataHandling.SQLFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class MusicDatabase {

    private ResultSet results;
    private ResultSetMetaData metaData;

    //FIXME should probably just make alternate constructors, or pass boolean values into the constructor?
//below are used for toggling between database creation or not
    //private static boolean create = true;
    private static boolean create = false;

    //used for toggling table dropping (will drop all tables if true)
//    private static boolean dropTables = true;
    private static final boolean dropTables = true;

    //used for toggling db table-creation (for example, tables have been modified)
//    private static boolean addTables = true;
    private static final boolean addTables = false;

    //used for toggling db filling behavior
//    private static boolean fill = true;
    private static final boolean fill = true;

    public ResultSetMetaData getMetaData() {
        return metaData;
    }

    public void executeSqlStatement(String... sqlStatements) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:" +
                ((create) ? "MusicDatabase;create=true" : "MusicDatabase"));
             Statement statement = connection.createStatement()) {
            for (String sqlStatement : sqlStatements) {
                statement.execute(sqlStatement);
            }
        }

    }

    public void printData(ResultSet s) throws SQLException {

        ResultSetMetaData metaData = s.getMetaData();
        int columnCount = metaData.getColumnCount();

        printHeader(metaData);
        while (s.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-" + metaData.getColumnLabel(i).length() + "s ", s.getObject(i) + " ");
            }
            System.out.println();

        }
        System.out.println();

    }

    /**
     * Prints column headers and separated by two spaces and adds a line of dashes
     *
     * @param metaData informatoin about columns
     * @throws SQLException throws if db connection has errors
     */
    private static void printHeader(ResultSetMetaData metaData) throws SQLException {
        int columnCount = metaData.getColumnCount();
        // print column headers
        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnLabel(i) + "  ");
        }
        System.out.println();
        // print dashed line
        for (int i = 1; i <= columnCount; i++) {
            for (int j = 0; j < metaData.getColumnLabel(i).length(); j++) {
                System.out.print("-");
            }
            if (i != columnCount) {
                System.out.print("--");
            }

        }
        System.out.println();
    }

    public void executeQueries(String... query) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:MusicDatabase");
             Statement statement = connection.createStatement()) {
            for (String queries : query) {
                ResultSet results = statement.executeQuery(queries);
                this.results = results;
                printData(results);
            }

        }

    }

    public ResultSetMetaData executeQueryVerbose(String query) {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:MusicDatabase");
             Statement statement = connection.createStatement()) {
            ResultSet results = statement.executeQuery(query);
            ResultSetMetaData metadata = results.getMetaData();
            printData(results);
            return metadata;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * execute a query that will return results
     *
     * @return ResultSet
     */
    public DefaultTableModel tableModelQuery(String query) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:MusicDatabase");
             Statement statement = connection.createStatement()) {

            ResultSet results = statement.executeQuery(query);
            this.results = results;
            this.metaData = results.getMetaData();

            return resultSetToTableModel();
        }
    }


    public ResultSet getResults() {
        return this.results;
    }

    public DefaultTableModel resultSetToTableModel() throws SQLException {

        DefaultTableModel tableModel = new DefaultTableModel();
        int columnCount = metaData.getColumnCount();

        // Get all column names from meta data and add columns to table model
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            tableModel.addColumn(metaData.getColumnLabel(columnIndex));
        }

        Object[] row = new Object[columnCount];

        while (this.results.next()) {
            // Get object from column with specific index of result set to array of objects
            for (int i = 0; i < columnCount; i++) {
                row[i] = results.getObject(i + 1);
            }
            // Now add row to table model with that array of objects as an argument
            tableModel.addRow(row);
        }

        // Now add that table model to your table and you are done :D
        return tableModel;
    }

    /**
     * taking a results set and adding them to JCombobox
     */
    public void resultSetToColumn(JComboBox<String> comboBox, String query) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:MusicDatabase");
             Statement statement = connection.createStatement()) {

            ResultSet results = statement.executeQuery(query);
            this.results = results;
            this.metaData = results.getMetaData();
            ResultSetMetaData metaData = results.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (this.results.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    comboBox.addItem(this.results.getString("COL"));
                }
            }

        }


    }

    public void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:MusicDatabase;shutdown=true");
        } catch (SQLException e) {
            //the exception for error code 45000 indicates successful shutdown and can safely be ignored
            if (e.getErrorCode() != 45000)
                e.printStackTrace();
        }
    }

    public String[] parseSQL(String path) {
        return parseSQL(new File(path));
    }

    /**
     * //TODO describe & test
     * //FIXME known issue: comments in a sql file cause unrecoverable errors
     *
     * @param file file
     * @return statements found
     */
    public String[] parseSQL(File file) {
        try (Scanner in = new Scanner(file)) {
            StringBuilder sb = new StringBuilder();

            while (in.hasNextLine()) {
                String line = in.nextLine();
                //FIXME Ignore comments, below doesn't work
//                if (line.length() > 0 && line.charAt(0) != '-')
                sb.append(line);
            }

            return sb.toString().split(";");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void dropTable(String table) {
        try {
            executeSqlStatement("DROP TABLE " + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getDataBaseMetaData() {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:MusicDatabase");
             Statement statement = connection.createStatement()) {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
                ResultSetMetaData metadata = executeQueryVerbose("SELECT * FROM " + rs.getString("TABLE_NAME") + " FETCH FIRST ROW ONLY");
                System.out.printf("%s has %d columns%n", rs.getString("TABLE_NAME"), metadata.getColumnCount());
                System.out.println("---------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
     *
     *
     * ==========================================================
     *
     *
     * TEST CLIENT
     */
    public static void main(String[] args) throws SQLException {
        MusicDatabase mDb = new MusicDatabase();
        if (dropTables) {
//            mDb.dropTable("l_artists_album_versions");
//            mDb.dropTable("l_songs_album_versions");
//            mDb.dropTable("l_artists_songs");
//            mDb.dropTable("album_versions");
//            mDb.dropTable("albums");
//            mDb.dropTable("artists");
//            mDb.dropTable("songs");
//            mDb.executeSqlStatement("ALTER TABLE album_versions DROP CONSTRAINT fk_album_version_album");
            mDb.executeSqlStatement("TRUNCATE TABLE albums");
        }
        //if we need to create the database, do so
        if (addTables) {
            for (String statement : mDb.parseSQL("src/dataHandling/CreateTables.sql")) {
                System.out.println("EXECUTING: ");
                System.out.println(statement);
                System.out.println();
                mDb.executeSqlStatement(statement);
            }
            System.out.println("Finished adding tables.");
        } else
            System.out.println("Already added tables\n");
        //if we need to fill the database, do so
        if (fill) {
            System.out.println("Filling database...");
            SQLFactory.fillDB(false);
        } else
            System.out.println("Already filled database\n");

        //FIXME is this constraint not existing going to screw us over?`
//        mDb.executeSqlStatement("ALTER TABLE album_versions ADD CONSTRAINT fk_album_version_album FOREIGN KEY (album) REFERENCES albums (id)");

//        mDb.executeQueries(
//        "SELECT songs.name, artists.name, albums.name "
//            + "FROM songs, artists, albums "
//            + "LEFT JOIN songs ON songs.id=l_artists_songs.song " //songs -> link table
//            + "LEFT JOIN l_artists_songs ON l_artists_songs.artist=artist.id " //link table - > artists
//            + "LEFT JOIN artists ON artists.id=l_artists_album_versions.artist " //artists -> link table
//            + "LEFT JOIN l_artists_album_versions ON l_artists_album_versions.album_version=album_versions.id " //link table -> album_versions
//            + "LEFT JOIN album_versions ON album_versions.album=albums.id " //album_versions -> albums DISTINCT
//            + "ORDER BY songs.name ASC");
//        mDb.getDataBaseMetaData();
//        mDb.executeQueries("SELECT * FROM artists FETCH FIRST 1000 ROWS ONLY");
        mDb.shutdown();
    }
}
