package database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MusicDatabase {
    //TODO update result sets when any query is run
    private ResultSet results;

    public ResultSetMetaData getMetaData() {
        return metaData;
    }

    private ResultSetMetaData metaData;

    public MusicDatabase() {

    }

    public static void createAndFillDB() throws SQLException {
        executeSqlStatement(ArtistsSql.createTable(), ArtistsSql.fillTable(), SongsSql.createTable());
        executeSqlStatement(AlbumSql.createTable(), AlbumSql.fillTable(), SongsSql.fillTable());
    }

    private static void executeSqlStatement(String... sqlStatements) throws SQLException {
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
     * Method : printHeader
     * <p>
     * Purpose : prints column headers and separated by two spaces and adds a line
     * of dashes
     *
     * @param : metadata
     * @throws : SQLException
     *           <p>
     *           **************************************************
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
                printData(results);
            }

        }

    }
    /**
     * execute a query that will return results
     * @return ResultSet
     */
    public DefaultTableModel executeQuery(String query) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:derby:MusicDatabase");
             Statement statement = connection.createStatement()) {

                ResultSet results = statement.executeQuery(query);
                this.results = results;
                this.metaData = results.getMetaData();

                return this.resultSetToTableModel();
        }

    }

    public ResultSet getResults(){
        return this.results;
    }

    public DefaultTableModel resultSetToTableModel() throws SQLException{

        DefaultTableModel tableModel = new DefaultTableModel();
        int columnCount = metaData.getColumnCount();
        System.out.println("column count set to " + columnCount);

        //Get all column names from meta data and add columns to table model
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++){
            tableModel.addColumn(metaData.getColumnLabel(columnIndex));
            System.out.println("Column " + metaData.getColumnLabel(columnIndex));
        }

        Object[] row = new Object[columnCount];

        while (this.results.next()){
            System.out.println("results has next");
            //Get object from column with specific index of result set to array of objects
            for (int i = 0; i < columnCount; i++){
                row[i] = results.getObject(i+1);
                System.out.println("object added to row["+i+"]");
            }
            System.out.println("");
            //Now add row to table model with that array of objects as an argument
            tableModel.addRow(row);
            System.out.println("Added row to table model" + row.toString());
        }

        //Now add that table model to your table and you are done :D
        return tableModel;
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
        //TODO use for testing createAndFillDB();
        MusicDatabase mDb = new MusicDatabase();

        mDb.executeQueries(SongsSql.query_All(), ArtistsSql.query_All(), AlbumSql.query_All());

        System.out.println("done\n");
    }
}

