package dataHandling;

import database.MusicDatabase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * line-by-line parser for tab-separated value data files with modular format specifier, input/output file location, etc
 * KNOWN ISSUE: appends comma to very last line which must be manually removed
 * <p>
 *
 * @author Alec Mills
 */
public abstract class Parser {
    protected final String fileName;

    public Parser(String fileName) {
        this.fileName = fileName;
    }

    //used to allow getLineData to toggle quoting of values on a per-column basis, i.e. INTEGER or VARCHAR
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
            MusicDatabase db = new MusicDatabase();
            //data to format
            File dirtyData = new File("data/dirty_data/" + fileName);
            Scanner input = new Scanner(dirtyData);

            //where to store formatted data
            File cleanData = new File("data/clean_data/" + fileName + ".sql");

            //add sql heading
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append(fileName.toUpperCase());
            sb.append(" (");
            sb.append(format).append(") VALUES (");
            String heading = sb.toString();

            //add records
            String log = "";
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
     * removes apostrophes from a String
     *
     * @param original raw String
     * @return String sans apostrophes
     */
    private String escapeQuotes(String original) {
        StringBuilder sb = new StringBuilder();
        for (char ch : original.toCharArray()) {
            if (ch != '\'')
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

    public abstract String getFormat();
}
