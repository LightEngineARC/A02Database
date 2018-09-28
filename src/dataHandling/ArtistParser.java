package dataHandling;

/**
 * Specifies format for artist table and utilizies parent class to parse song data from data_wanted/artist
 * <p>
 * @author Alec Mills
 */
public class ArtistParser extends Parser {
    private String format = "ARTIST_NAME";

    public ArtistParser() {
        super("artist");
    }

    protected void parseData() {
        super.parseData(format);
    }

    /**
     * defines what data and what format to take from data soure
     * @param columns
     * @return
     */
    @Override
    protected String[] getLineData(String[] columns) {
        String[] result = new String[] {
                columns[3]
        };
        return result;
    }

    public static void main(String[] args) {
        new ArtistParser().parseData();
    }
}
