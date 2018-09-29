package dataHandling;

/**
 * Specifies format for artists table and utilizes parent class to parse song data from data_wanted/artists
 * <p>
 *
 * @author Alec Mills
 */
public class ArtistsParser extends Parser {
    private final String format = "ARTIST_NAME";

    private ArtistsParser() {
        super("artists");
    }

    private void parseData() {
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
        return new String[]{
                columns[3]
        };
    }

    public static void main(String[] args) {
        new ArtistsParser().parseData();
    }
}
