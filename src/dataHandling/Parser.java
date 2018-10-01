package dataHandling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    /**
     * parses data from a known format and converts it to INSERT statement of sql
     */
    public void parseData(String format) {
        try {
            //data to format
            File dirtyData = new File("data/dirty_data/" + fileName);
            Scanner input = new Scanner(dirtyData);

            //where to store formatted data
            File cleanData = new File("data/clean_data/" + fileName + ".sql");
            FileWriter write = new FileWriter(cleanData, true);

            //add sql heading
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append(fileName.toUpperCase());
            sb.append(" (");
            sb.append(format).append(") VALUES\n");
            write.write(sb.toString());
            write.flush();

            //add records
            while (input.hasNextLine()) {
                String line = input.nextLine();
                //for debugging
//                    System.out.println(line);

                String[] columns = getLineData(line.split("\\t"));
                //for debugging
//                   System.out.println();


                write.write("(");
                for (int i = 0; i < columns.length; i++) {
                    String column = escapeQuotes(columns[i]);
                    sb = new StringBuilder();
                    //add single quotes to string values
                    //can receive null values in some cases, e.g. songs.length
                    if (column.length() > 0) {
                        if (Character.isAlphabetic(column.charAt(0)))
                            sb.append('\'' + column + '\'');
                            //don't add single quotes for ints
                        else
                            sb.append(column);
                        //we need commas on all but the last value in a record
                        if (i < columns.length - 1)
                            sb.append(", ");
                        write.write(sb.toString());
                    }
                    else
                        write.write("null");
                }
                write.write("),\n");
                write.flush();
            }

            write.close();

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
    protected abstract String[] getLineData(String[] columns);

    public abstract String getFormat();
}
