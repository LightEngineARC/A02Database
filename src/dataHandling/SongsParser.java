package dataHandling;

/**
 * Specifies format for songs table and utilizes parent class to parse song data from dirty_data/songs
 * <p>
 *
 * @author Alec Mills
 */
public class SongsParser extends Parser {
    private final String format = "id, name, length";

    public SongsParser() {
        super("songs");
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
                columns[0],//id
                columns[2],//name
                columns[4],//length
        };
        return result;
    }

    public String getFormat() {
        return format;
    }
}
