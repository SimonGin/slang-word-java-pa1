package slang;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class SlangWindow extends JFrame implements ActionListener, DocumentListener {
    SlangDictionary dictionary;
    DefaultTableModel tableModel;

    String srchDes;

    JTextField srchSlangField;
    JButton srchKeyBtn;
    JButton srchDefBtn;
    JPanel srchBox;

    JTable resulTable;
    JScrollPane resultScrollPane;
    JPanel resultBox;

    private void loadAllWords() {
        for (Map.Entry<String, SlangWord> entry : dictionary.getDictionary().entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue().getDef()});
        }
    }

    SlangWindow(SlangDictionary dict) {
        this.dictionary = dict;

        this.setTitle("Slang Dictionary");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(900,500);

        srchSlangField = new JTextField();
        srchSlangField.setPreferredSize(new Dimension(500,50));
        srchSlangField.setText("Input keyword or definition of the slang word you want to search");
        srchSlangField.getDocument().addDocumentListener(this);

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

        tableModel = new DefaultTableModel(new Object[]{"Slang", "Meaning"}, 0) {
            @Override
            public  boolean isCellEditable(int row,int col) {
                return false;
            }
        };

        loadAllWords();

        resulTable = new JTable(tableModel);
        resulTable.setFont(new Font("Roboto", Font.PLAIN, 18));
        resultScrollPane = new JScrollPane(resulTable);
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
            if (e.getSource() == srchKeyBtn) {
                SlangWord foundWord = dictionary.searchKey(srchDes);
                tableModel.setRowCount(0);
                tableModel.addRow(new Object[]{foundWord.getKey(), foundWord.getDef()});
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (srchSlangField.getText().isEmpty()) {
            loadAllWords();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}
