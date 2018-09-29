package database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class MusicDatabase {

    // TODO update result sets when any query is run
    private ResultSet results;
    private ResultSetMetaData metaData;

    public ResultSetMetaData getMetaData() {
        return metaData;
    }

    public MusicDatabase() {

    }

    public void createAndFillDB() throws SQLException {
        executeSqlStatement(ArtistsSql.createTable(), ArtistsSql.fillTable(), SongsSql.createTable());
        executeSqlStatement(AlbumsSql.createTable(), AlbumsSql.fillTable(), SongsSql.fillTable());
    }

    public void executeSqlStatement(String... sqlStatements) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:MusicDatabase;create=true");
             // attribute "...;create=true" removed after databaseold creation
             Statement statement = connection.createStatement()) {
            for (String sqlStatement : sqlStatements) {
                statement.execute(sqlStatement);
            }
        }

    }

    private void printData(ResultSet s) throws SQLException {

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
     * @param metaData
     * @throws SQLException
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

            return this.resultSetToTableModel();
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
            System.out.println("Column " + metaData.getColumnLabel(columnIndex));
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
                System.out.println("results has next");
                for (int i = 1; i <= columnCount; i++) {
                    comboBox.addItem(this.results.getString("COL"));
                }

            }

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

        // TODO TEST this for all tables. This will purge all old and
        // new tables, create them, and query all on each of them.

        // ORIGINAL NAMES
        try {
            mDb.executeSqlStatement("drop table albums");
        } catch (SQLException e) {
            System.out.println("table ALBUMS does not exist\n");
        }
        try {
            mDb.executeSqlStatement("drop table artists");
        } catch (SQLException e) {
            System.out.println("table ARTISTS does not exist\n");
        }
        try {
            mDb.executeSqlStatement("drop table songs");
        } catch (SQLException e) {
            System.out.println("table SONGS does not exist\n");
        }

        // PLURALIZED TABLES
        try {
            mDb.executeSqlStatement(AlbumsSql.dropTable());
        } catch (SQLException e) {
            System.out.println();

        }
        try {
            mDb.executeSqlStatement(ArtistsSql.dropTable());
        } catch (SQLException e) {
            System.out.println();

        }
        try {
            mDb.executeSqlStatement(SongsSql.dropTable());
        } catch (SQLException e) {
            System.out.println();

        }

        mDb.createAndFillDB();
        mDb.executeQueries(SongsSql.query_All(), ArtistsSql.query_All(), AlbumsSql.query_All(), SongsArtistsAlbums.query_Artist_String("Eve 6"));

        System.out.println("done\n");
    }
}
