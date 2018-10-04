package dataHandling;

/**
 * Specifies format for albums table and utilizes parent class to parse song data from dirty_data/albums
 * and insert into database
 * <p>
 *
 * @author Alec Mills
 */
public class AlbumVersionsParser extends Parser {
    private final String format = "id, name, album";

    public AlbumVersionsParser() {
        super("album_versions");
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
                new Tuple(columns[2], true), //name
                new Tuple(columns[4], false) //album
        };
    }

    /**
     * see parent class
     */
    public String getFormat() {
        return format;
    }
}
