package slang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SlangWindow extends JFrame implements ActionListener{
    ArrayList<String> resultList = new ArrayList<>();
    String srchDes;

    JTextField srchSlangField;
    JButton srchKeyBtn;
    JButton srchDefBtn;
    JPanel srchBox;

    JList resultListBox;
    JScrollPane resultScrollPane;
    JPanel resultBox;
    SlangWindow() {
        this.setTitle("Slang Dictionary");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(900,500);

        srchSlangField = new JTextField();
        srchSlangField.setPreferredSize(new Dimension(500,50));
        srchSlangField.setText("Input keyword or definition of the slang word you want to search");

        srchKeyBtn = new JButton("Search by keyword");
        srchKeyBtn.addActionListener(this);
        srchDefBtn = new JButton("Search by definition");
        srchDefBtn.addActionListener(this);

        srchBox = new JPanel();
        srchBox.setBackground(Color.CYAN);
        srchBox.setSize(900,100);

        srchBox.add(srchSlangField);
        srchBox.add(srchKeyBtn);
        srchBox.add(srchDefBtn);

        this.add(srchBox,BorderLayout.NORTH);

        for (int i = 0;i < 50;i++) {
            resultList.add("Simon");
            resultList.add("Zacki");
        }

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String item : resultList) {
            listModel.addElement(item);
        }

        resultListBox = new JList<>(listModel);
        resultScrollPane = new JScrollPane(resultListBox);
        resultScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        resultBox = new JPanel();
        resultBox.add(resultScrollPane);

        this.add(resultScrollPane,BorderLayout.CENTER);

        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == srchKeyBtn) || (e.getSource() == srchDefBtn)) {
            srchDes = srchSlangField.getText();
            System.out.println(srchDes);
        }
    }
}
