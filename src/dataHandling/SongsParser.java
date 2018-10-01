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
    protected Tuple[] getLineData(String[] columns) {
        Tuple[] result = new Tuple[]{
                new Tuple(columns[0], false), //id
                new Tuple(columns[2], true), //name
                new Tuple(columns[4], false) //length
        };
        return result;
    }

    public String getFormat() {
        return format;
    }
}
