package slang;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class SlangWindow extends JFrame implements ActionListener, DocumentListener {
    SlangDictionary dictionary;
    DefaultTableModel tableModel;

    String srchDes;
    String selectedKey;
    ListSelectionModel listSelectModel;

    JTextField srchSlangField;
    JButton srchKeyBtn;
    JButton srchDefBtn;
    JPanel srchBox;

    JTable resultTable;
    JScrollPane resultScrollPane;
    JPanel resultBox;

    JButton addBtn;
    JButton editBtn;
    JButton delBtn;
    JButton hstrBtn;
    JPanel actionBox;

    private void loadAllWords() {
        tableModel.setRowCount(0);
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
        srchSlangField.setText("Input keyword or definition of the slang you want to search");
        srchSlangField.setFont(new Font("Roboto", Font.PLAIN, 18));
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

        resultTable = new JTable(tableModel);
        resultTable.setFont(new Font("Roboto", Font.PLAIN, 18));
        listSelectModel = resultTable.getSelectionModel();
        listSelectModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultTable.setSelectionModel(listSelectModel);
        listSelectModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = resultTable.getSelectedRow();
                    if (selectedRow > -1) {
                        selectedKey = (String) resultTable.getValueAt(selectedRow, 0);
                    }
                }
            }
        });
        resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        resultBox = new JPanel();
        resultBox.add(resultScrollPane);
        this.add(resultScrollPane,BorderLayout.CENTER);

        addBtn = new JButton("Add new slang");
        addBtn.addActionListener(this);
        editBtn = new JButton("Edit selected slang");
        editBtn.addActionListener(this);
        delBtn = new JButton("Delete slang word");
        delBtn.addActionListener(this);
        hstrBtn = new JButton("View history");
        hstrBtn.addActionListener(this);
        actionBox = new JPanel();
        actionBox.add(addBtn);
        actionBox.add(editBtn);
        actionBox.add(delBtn);
        actionBox.add(hstrBtn);
        this.add(actionBox,BorderLayout.SOUTH);

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
                if (foundWord != null) {
                    tableModel.addRow(new Object[]{foundWord.getKey(), foundWord.getDef()});
                }
            }
            if (e.getSource() == srchDefBtn) {
                ArrayList<SlangWord> foundWords = dictionary.searchDef(srchDes);
                tableModel.setRowCount(0);
                if (foundWords != null) {
                    for (SlangWord word : foundWords) {
                        tableModel.addRow(new Object[]{word.getKey(), word.getDef()});
                    }
                }
            }
        }
        if (e.getSource() == delBtn) {
            if (!listSelectModel.isSelectionEmpty()) {
                int confirm_code = JOptionPane.showConfirmDialog(null,"Do you want to delete the selected slang permanently (a really long time)?","Delete Confirmation",JOptionPane.YES_NO_OPTION);
                if (confirm_code == 0) {
                    listSelectModel.clearSelection();
                    dictionary.deleteSlang(selectedKey);
                    srchSlangField.setText("");
                    loadAllWords();
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"You haven't selected any slang to delete!!! Please select a word you want to delete!!!","Word not selected",JOptionPane.WARNING_MESSAGE);
            }
        }
        if (e.getSource() == addBtn) {
            SlangPutFrame addNewSlangFrame = new SlangPutFrame(dictionary,"Add New Slang","Add");
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
