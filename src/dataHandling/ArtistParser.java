package dataHandling;

/**
 * Specifies format for artist table and utilizes parent class to parse song data from data_wanted/artist
 * <p>
 *
 * @author Alec Mills
 */
public class ArtistParser extends Parser {
    private final String format = "ARTIST_NAME";

    public ArtistParser() {
        super("artist");
    }

    protected void parseData() {
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
        new ArtistParser().parseData();
    }
}
