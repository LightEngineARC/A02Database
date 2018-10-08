package database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

/**
 * @author Ashton Chatelain
 *
 * A02Database
 * Description : Create a gui that interacts with a database. The gui has a MusicDatabase class it uses to make queries
 * and execute statements.
 */
@SuppressWarnings("serial")
public class DataGUI extends JFrame {
    private final JTable table;
    private final JTextField textFieldSearch;
    private final JRadioButton rdbtnSong = new JRadioButton("Song");
    private final JRadioButton rdbtnArtist = new JRadioButton("Artist");
    private final JRadioButton rdbtnAlbum = new JRadioButton("Album");
    private static final MusicDatabase musicDatabase = new MusicDatabase();
    

    /**
     * Launch the application.
     */

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DataGUI frame = new DataGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                musicDatabase.shutdown();
            }
        });

    }

    /**
     * Create the frame and add elements
     */
    public DataGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 720, 475);
        //Private fields that need to be manipulated by methods
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        MusicDatabase musicDatabase = new MusicDatabase();
        ButtonGroup group = new ButtonGroup();
        group.add(rdbtnSong);
        group.add(rdbtnArtist);
        group.add(rdbtnAlbum);
        JComboBox<String> artistComboBox = new JComboBox<>();
        artistComboBox.setBounds(274, 51, 200, 30);
        contentPane.add(artistComboBox);
        JComboBox<String> albumComboBox = new JComboBox<>();
        albumComboBox.setBounds(484, 51, 200, 30);
        contentPane.add(albumComboBox);
        JScrollPane scrollPane = new JScrollPane();

        scrollPane.setBounds(5, 116, 689, 309);
        contentPane.add(scrollPane);
        table = new JTable();
        scrollPane.setViewportView(table);
        JLabel lblArtist = new JLabel("Filter by Artist");
        lblArtist.setHorizontalAlignment(SwingConstants.CENTER);
        lblArtist.setBounds(274, 11, 130, 29);
        contentPane.add(lblArtist);
        JLabel lblAlbum = new JLabel("Filter by Album");
        lblAlbum.setHorizontalAlignment(SwingConstants.CENTER);
        lblAlbum.setBounds(414, 11, 130, 29);
        contentPane.add(lblAlbum);
        rdbtnSong.setSelected(true);
        rdbtnSong.setBounds(5, 86, 65, 23);
        contentPane.add(rdbtnSong);
        rdbtnArtist.setBounds(72, 86, 65, 23);
        contentPane.add(rdbtnArtist);
        rdbtnAlbum.setBounds(139, 86, 65, 23);
        contentPane.add(rdbtnAlbum);
        artistComboBox.addItem("");
        albumComboBox.addItem("");
        textFieldSearch = new JTextField();
        textFieldSearch.setToolTipText("Search");
        textFieldSearch.setBounds(5, 56, 185, 20);
        contentPane.add(textFieldSearch);
        textFieldSearch.setColumns(10);
        
        JButton btnReset = new JButton("Reset");
        btnReset.setFont(new Font("Tahoma", Font.PLAIN, 9));
        btnReset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		textFieldSearch.setText("");
        		rdbtnSong.setSelected(true);
        		artistComboBox.setSelectedIndex(0);
        		albumComboBox.setSelectedIndex(0);
        	}
        });
        btnReset.setBounds(199, 55, 65, 23);
        contentPane.add(btnReset);
        


        artistComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //TODO filter songs with this artists
                String filterArtist = (String) artistComboBox.getSelectedItem();
                try {
                    table.setModel(musicDatabase.tableModelQuery(SongsArtistsAlbums.query_Artist_String(filterArtist)));
                } catch(SQLException e){
                    e.printStackTrace();
                    System.out.println("SQL exception when filtering by artists");
                }

            }
        });

        albumComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //TODO filter songs with this albums
                String filterAlbum = (String) albumComboBox.getSelectedItem();
                try {
                    table.setModel(musicDatabase.tableModelQuery(SongsArtistsAlbums.query_Album_String(filterAlbum)));
                } catch(SQLException e){
                    e.printStackTrace();
                    System.out.println("SQL exception when filtering by Album");
                }
            }
        });


        textFieldSearch.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		//TODO add action listener to search for songs by entry
        		if(!textFieldSearch.getText().contains(",") || !textFieldSearch.getText().contains(";") || !textFieldSearch.getText().contains("(") );{
        			try
					{
						search(musicDatabase);
					} catch (SQLException e)
					{
						e.printStackTrace();
					}
        		}
        	}
        });

        textFieldSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                if(!textFieldSearch.getText().contains(",") || !textFieldSearch.getText().contains(";") || !textFieldSearch.getText().contains("(") );{
                    try
                    {
                        search(musicDatabase);
                    } catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        try {
            //musicDatabase.executeQueries(SongsArtistsAlbums.query_All());
            table.setModel(musicDatabase.tableModelQuery(SongsArtistsAlbums.query_All()));
            //musicDatabase.createAndFillDB();
            musicDatabase.resultSetToColumn(albumComboBox, AlbumsSql.query_Albums());
            musicDatabase.resultSetToColumn(artistComboBox, ArtistsSql.query_Artists());

        } catch (SQLException e) {
            System.out.println("SQL exception when executing query_all");
            e.printStackTrace();
        }
    }
    /**
     * Sets the table to the music filtered by the search input, and radio button selection.
     * 
     * @param musicDatabase to query
     */
    public void search(MusicDatabase musicDatabase) throws SQLException {
    	
        if(rdbtnSong.isSelected()) {
            table.setModel(musicDatabase.tableModelQuery(SongsArtistsAlbums.query_Song_String(textFieldSearch.getText())));
        }
        if(rdbtnArtist.isSelected()){
            table.setModel(musicDatabase.tableModelQuery(SongsArtistsAlbums.query_Artist_String(textFieldSearch.getText())));
        }
        if(rdbtnAlbum.isSelected()) {
            table.setModel(musicDatabase.tableModelQuery(SongsArtistsAlbums.query_Album_String(textFieldSearch.getText())));
        }

    }
}


