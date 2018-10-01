package dataHandling;

/**
 * Specifies format for artists table and utilizes parent class to parse song data from dirty_data/artists
 * <p>
 *
 * @author Alec Mills
 */
public class ArtistsParser extends Parser {
    private final String format = "id, name";

    public ArtistsParser() {
        super("artists");
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
        return new Tuple[]{
                new Tuple(columns[0], false), //id
                new Tuple(columns[2], true) //name
        };
    }

    public String getFormat() {
        return format;
    }
}
