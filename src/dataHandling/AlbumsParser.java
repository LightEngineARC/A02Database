package dataHandling;

/**
 * Specifies format for albums table and utilizes parent class to parse song data from dirty_data/albums
 * FIXME needs getLineData() to be finished in order to function, currently will throw exceptions
 * <p>
 *
 * @author Alec Mills
 */
public class AlbumsParser extends Parser {
    private final String format = "id, name";

    public AlbumsParser() {
        super("albums");
    }

    public void parseData() {
        super.parseData(format);
    }

    /**
     * defines what data and what format to take from data source
     *
     * @param columns raw data
     * @return desired data
     */
    @Override
    protected String[] getLineData(String[] columns) {
        String[] result = new String[]{
                columns[0], //id
                columns[2] //name
        };
        return result;
    }

    public String getFormat() {
        return format;
    }
}
