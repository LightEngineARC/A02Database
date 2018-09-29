package database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * @author Ashton Chatelain
 * <p>
 * A02Database
 * Description : (Narrative description, not code)
 */
@SuppressWarnings("serial")
public class DataGUI extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private boolean isFiltered = false;

    /**
     * Launch the application.
     */

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DataGUI frame = new DataGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create the frame.
     */
    public DataGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 570, 401);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        MusicDatabase musicDatabase = new MusicDatabase();

        JComboBox<String> artistComboBox = new JComboBox<>();
        artistComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //TODO filter songs with this artists
                String filterArtist = (String) artistComboBox.getSelectedItem();
                try {
                    table.setModel(musicDatabase.tableModelQuery(SongsArtistsAlbums.query_Artist_String(filterArtist)));
                } catch(SQLException e){
                    System.out.println("SQL exception when filtering by artists");
                }

            }
        });
        artistComboBox.setBounds(123, 51, 130, 30);
        contentPane.add(artistComboBox);

        JComboBox<String> albumComboBox = new JComboBox<>();
        albumComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //TODO filter songs with this albums
                String filterAlbum = (String) albumComboBox.getSelectedItem();
                try {
                    table.setModel(musicDatabase.tableModelQuery(SongsArtistsAlbums.query_Album_String(filterAlbum)));
                } catch(SQLException e){
                    System.out.println("SQL exception when filtering by Album");
                }
            }
        });
        albumComboBox.setBounds(289, 51, 130, 30);
        contentPane.add(albumComboBox);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(5, 116, 539, 235);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JLabel lblArtist = new JLabel("Filter by Artist");
        lblArtist.setHorizontalAlignment(SwingConstants.CENTER);
        lblArtist.setBounds(123, 11, 130, 29);
        contentPane.add(lblArtist);

        JLabel lblAlbum = new JLabel("Filter by Album");
        lblAlbum.setHorizontalAlignment(SwingConstants.CENTER);
        lblAlbum.setBounds(289, 11, 130, 29);
        contentPane.add(lblAlbum);
        artistComboBox.addItem("");

        albumComboBox.addItem("");


        try {
            table.setModel(musicDatabase.tableModelQuery(SongsArtistsAlbums.query_All()));
            //musicDatabase.createAndFillDB();

            musicDatabase.resultSetToColumn(albumComboBox, AlbumsSql.query_Albums());
            musicDatabase.resultSetToColumn(artistComboBox, ArtistsSql.query_Artists());

        } catch (SQLException e) {
            System.out.println("SQL exception when executing query_all");
        }

    }

}


