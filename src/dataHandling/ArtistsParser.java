package dataHandling;

/**
 * Specifies format for artists table and utilizes parent class to parse song data from dirty_data/artists
 * and insert into database
 * <p>
 *
 * @author Alec Mills
 */
public class ArtistsParser extends Parser {
    private final String format = "id, name";

    public ArtistsParser() {
        super("artists");
    }

    /**
     * see parent class
     */
    public void parseData() {
        super.parseData(format);
    }

    @Override
    protected Tuple[] getLineData(String[] columns) {
        return new Tuple[]{
                new Tuple(columns[0], false), //id
                new Tuple(columns[2], true) //name
        };
    }

    /**
     * see parent class
     */
    public String getFormat() {
        return format;
    }
}
