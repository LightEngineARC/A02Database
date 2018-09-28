/**
 *  @author Ashton Chatelain
 *  
 *  A02Database
 *  Description : (Narrative description, not code)
 */
package database;

import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Ashton Chatelain
 *
 */

public class DataGUI extends JFrame {
    private JPanel contentPane;
    private JTable table;

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
        setBounds(100, 100, 450, 340);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JComboBox<String> comboBox_1 = new JComboBox();
        comboBox_1.setBounds(5, 51, 130, 30);
        contentPane.add(comboBox_1);

        JComboBox<String> comboBox_3 = new JComboBox();
        comboBox_3.setBounds(145, 51, 130, 30);
        contentPane.add(comboBox_3);

        JComboBox<String> comboBox_2 = new JComboBox();
        comboBox_2.setBounds(285, 51, 130, 30);
        contentPane.add(comboBox_2);

        table = new JTable();
        table.setBounds(5, 116, 424, 185);
        contentPane.add(table);

        JLabel lblArtist = new JLabel("Filter by Artist");
        lblArtist.setHorizontalAlignment(SwingConstants.CENTER);
        lblArtist.setBounds(5, 11, 130, 29);
        contentPane.add(lblArtist);

        JLabel lblAlbum = new JLabel("Filter by Album");
        lblAlbum.setHorizontalAlignment(SwingConstants.CENTER);
        lblAlbum.setBounds(145, 11, 130, 29);
        contentPane.add(lblAlbum);

        JLabel lblGenre = new JLabel("Filter by Genre");
        lblGenre.setHorizontalAlignment(SwingConstants.CENTER);
        lblGenre.setBounds(285, 11, 130, 29);
        contentPane.add(lblGenre);
        comboBox_1.addItem("Artist");
        comboBox_1.addItem("Album");
        comboBox_1.addItem("Genre");
        comboBox_2.addItem("Artist");
        comboBox_2.addItem("Album");
        comboBox_2.addItem("Genre");
        comboBox_3.addItem("Artist");
        comboBox_3.addItem("Album");
        comboBox_3.addItem("Genre");
    }

}
