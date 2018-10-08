package dataHandling;

/**
 * Specifies format for albums table and utilizes parent class to parse song data from dirty_data/albums
 * and insert into database
 * <p>
 *
 * @author Alec Mills
 */
public class AlbumsParser extends Parser {

    public AlbumsParser() {
        super("albums");
    }

    /**
     * see parent class
     */
    public void parseData() {
        super.parseData(getFormat());
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
        return "id, name";
    }
}
