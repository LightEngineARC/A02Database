package dataHandling;

/**
 * Specifies format for albums table and utilizes parent class to parse song data from dirty_data/albums
 * FIXME needs getLineData() to be finished in order to function, currently will throw exceptions
 * <p>
 *
 * @author Alec Mills
 */
public class AlbumVersionsParser extends Parser {
    private final String format = "id, name, album";

    public AlbumVersionsParser() {
        super("album_versions");
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
                new Tuple(columns[2], true), //name
                new Tuple(columns[4], false) //album
        };
    }

    public String getFormat() {
        return format;
    }
}
