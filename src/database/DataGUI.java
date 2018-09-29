/**
 * @author Ashton Chatelain
 * <p>
 * A02Database
 * Description : (Narrative description, not code)
 */
package database;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Ashton Chatelain
 *
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

        JComboBox<String> artistComboBox = new JComboBox<String>();
        artistComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //TODO filter songs with this artist
            }
        });
        artistComboBox.setBounds(123, 51, 130, 30);
        contentPane.add(artistComboBox);

        JComboBox<String> albumComboBox = new JComboBox<String>();
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

        MusicDatabase musicDatabase = new MusicDatabase();
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


