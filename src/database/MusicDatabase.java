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
//    private static boolean create = true;
    private static boolean create = false;
    //used for toggling filling behavior
    private static boolean fill = true;
//    private static boolean fill = false;

    public ResultSetMetaData getMetaData() {
        return metaData;
    }

    public void createAndFillDB() throws SQLException {
        executeSqlStatement(ArtistsSql.createTable(), ArtistsSql.fillTable(), SongsSql.createTable());
        executeSqlStatement(AlbumsSql.createTable(), AlbumsSql.fillTable(), SongsSql.fillTable());
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

            String[] statements = sb.toString().split(";");
            return statements;

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
        //if we need to create the database, do so
        if (create) {
            for (String statement : mDb.parseSQL("src/dataHandling/CreateTables.sql")) {
                System.out.println("EXECUTING: ");
                System.out.println(statement);
                System.out.println();
                mDb.executeSqlStatement(statement);
            }
            System.out.println("Finished creation.");
        } else
            System.out.println("Already created\n");
        //if we need to fill the database, do so
        if (fill) {
            System.out.println("Filling database...");
            SQLFactory.fillDB(false);
        } else
            System.out.println("Already filled database\n");

        mDb.shutdown();
    }
}
