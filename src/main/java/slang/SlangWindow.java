package slang;

import javax.swing.*;
import java.awt.*;

public class SlangWindow extends JFrame {
    JTextField srchSlangField;

    JButton srchKeyBtn;
    JButton srchDefBtn;

    JPanel srchBox;
    SlangWindow() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(900,500);

        srchSlangField = new JTextField();
        srchSlangField.setPreferredSize(new Dimension(500,50));
        srchSlangField.setText("Input keyword or definition of the slang word you want to search");

        srchKeyBtn = new JButton("Search by keyword");
        srchDefBtn = new JButton("Search by definition");

        srchBox = new JPanel();
        srchBox.setBackground(Color.CYAN);
        srchBox.setSize(900,100);

        srchBox.add(srchSlangField);
        srchBox.add(srchKeyBtn);
        srchBox.add(srchDefBtn);

        this.add(srchBox,BorderLayout.NORTH);

        this.setResizable(false);
        this.setVisible(true);
    }
}
