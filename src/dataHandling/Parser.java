package dataHandling;

import database.MusicDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * line-by-line parser for tab-separated value data files with modular format specifier;
 * generates and executes INSERT statements with format specified by each specific sub-class
 * <p>
 * KNOWN ISSUE: appends comma to very last line which must be manually removed
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
     */
    protected static class Tuple {
        //value for column
        String value;
        //whether to quote that value
        boolean quote;

        protected Tuple(String value, boolean quote) {
            this.value = value;
            this.quote = quote;
        }
    }

    /**
     * parses data from a known format and converts it to INSERT statement of sql
     */
    public void parseData(String format) {
        try {
            //data to format
            File dirtyData = new File("data/dirty_data/" + fileName);

            MusicDatabase db = new MusicDatabase();
            Scanner input = new Scanner(dirtyData);

            //add sql INSERT heading
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append(fileName.toUpperCase());
            sb.append(" (");
            sb.append(format).append(") VALUES (");
            String heading = sb.toString();

            //add records
            String log = ""; //for storing currently executing statement for debug purposes
            while (input.hasNextLine()) {
                sb = new StringBuilder();
                sb.append(heading);

                String line = input.nextLine();
                Tuple[] columns = getLineData(line.split("\\t"));

                for (int i = 0; i < columns.length; i++) {
                    String column = escapeQuotes(columns[i].value);
                    //add single quotes to string values
                    //can receive null values in some cases, e.g. songs.length
                    if (column.length() > 0) {
                        if (columns[i].quote)
                            sb.append('\'' + column + '\'');
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
                try {
                    log = sb.toString();
                    db.executeSqlStatement(log);
                } catch (SQLException e) {
                    System.out.println(log);
                    e.printStackTrace();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * removes single & double quotes from a String
     *
     * @param original raw String
     * @return String sans apostrophes
     */
    private String escapeQuotes(String original) {
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
