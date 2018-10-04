package dataHandling;

/**
 * Specifies format for songs table and utilizes parent class to parse song data from dirty_data/songs
 * and insert into database
 * <p>
 *
 * @author Alec Mills
 */
public class SongsParser extends Parser {
    private final String format = "id, name, length";

    public SongsParser() {
        super("songs");
    }

    /**
     * see parent class
     */
    public void parseData() {
        super.parseData(format);
    }

    @Override
    protected Tuple[] getLineData(String[] columns) {
        Tuple[] result = new Tuple[]{
                new Tuple(columns[0], false), //id
                new Tuple(columns[2], true), //name
                new Tuple(columns[4], false) //length
        };
        return result;
    }

    /**
     * see parent class
     */
    public String getFormat() {
        return format;
    }
}
