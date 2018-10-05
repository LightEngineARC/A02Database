package dataHandling;

import database.MusicDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * line-by-line parser for .tsv files with modular format specifier;
 * dynamically generates and executes INSERT statements with format specified by sub-class implementation
 * <p>
 *
 * @author Alec Mills
 */
public abstract class Parser {
    private final String fileName;

    public Parser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * used to allow getLineData to toggle quoting of values on a per-column basis, i.e. INTEGER vs VARCHAR
     *
     * @author alec mills
     */
    protected static class Tuple {
        //value for column
        private final String value;
        //whether to quote that value
        private final boolean quote;

        Tuple(String value, boolean quote) {
            this.value = value;
            this.quote = quote;
        }
    }

    /**
     * parses data from a known format and converts it to INSERT statement of sql
     *
     * @param format enumeration of target columns
     */
    public void parseData(String format) {
        try {
            //data to format
            File dirtyData = new File("data/dirty_data/" + fileName);

            MusicDatabase db = new MusicDatabase();
            Scanner input = new Scanner(dirtyData);

            //generate sql INSERT heading
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append(fileName.toUpperCase());
            sb.append(" (");
            sb.append(format).append(") VALUES (");
            String heading = sb.toString();

            //generate and execute records
            String log = ""; //for storing currently executing statement for debug purposes
            while (input.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(heading);

                String line = input.nextLine();
                Tuple[] columns = getLineData(line.split("\\t"));

                for (int i = 0; i < columns.length; i++) {
                    String column = escapeQuotes(columns[i].value);
                    //make "" -> null
                    if (column.length() > 0 && !column.toUpperCase().equals("\\N")) {
                        if (columns[i].quote)
                            sb.append('\'').append(column).append('\'');
                        else
                            sb.append(column);
                    }
                    else
                        sb.append("null");
                    //we need commas on all but the last value in a record
                    if (i < columns.length - 1)
                        sb.append(", ");
                }
                sb.append(")");
                //execute statement
                try {
                    log = sb.toString();
                    db.executeSqlStatement(log);
                } catch (SQLException e) {
                    System.out.println(log);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Hint: does file path data/dirty_data/* exist?");
            e.printStackTrace();
        }
    }

    /**
     * removes single & double quotes from a String
     *
     * @param original raw String
     * @return String sans apostrophes
     */
    private String escapeQuotes(String original) { //TEST PASSED
        StringBuilder sb = new StringBuilder();
        for (char ch : original.toCharArray()) {
            if (ch != '\'' && ch != '"')
                sb.append(ch);
        }

        return sb.toString();
    }

    /**
     * parse data from a line into an array of String values
     *
     * @param columns the raw data
     * @return String[] of desired column values in order
     */
    protected abstract Tuple[] getLineData(String[] columns);

    /**
     * specify table format
     *
     * @return String representation of table columns
     */
    public abstract String getFormat();
}
