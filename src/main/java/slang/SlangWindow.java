package slang;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SlangWindow extends JFrame implements ActionListener, DocumentListener {
    static public SlangDictionary dictionary;
    static public DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Slang", "Meaning"}, 0) {
        @Override
        public boolean isCellEditable(int row,int col) {
            return false;
        }
    };
    ArrayList<SlangWord> historySlangs = new ArrayList<>();

    private String selectedKey;
    private final ListSelectionModel listSelectModel;

    private final JTextField srchSlangField;

    static public void loadAllWords() {
        tableModel.setRowCount(0);
        for (Map.Entry<String, SlangWord> entry : dictionary.getDictionary().entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue().getDef()});
        }
    }

    SlangWindow(SlangDictionary dict) {
        dictionary = dict;

        this.setTitle("Slang Dictionary");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(900,500);

        srchSlangField = new JTextField();
        srchSlangField.setPreferredSize(new Dimension(500,50));
        srchSlangField.setText("Input keyword or definition of the slang you want to search");
        srchSlangField.setFont(new Font("Roboto", Font.PLAIN, 18));
        srchSlangField.getDocument().addDocumentListener(this);

        JPanel srchBox = new JPanel();
        srchBox.setBackground(Color.CYAN);
        srchBox.setSize(900,100);

        srchBox.add(srchSlangField);

        JButton srchBtn = new JButton("Search By Keyword");
        srchBtn.addActionListener(this);
        srchBox.add(srchBtn);
        srchBtn = new JButton("Search By Definition");
        srchBtn.addActionListener(this);
        srchBox.add(srchBtn);

        this.add(srchBox,BorderLayout.NORTH);

        loadAllWords();

        JTable resultTable = new JTable(tableModel);
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
        JScrollPane resultScrollPane = new JScrollPane(resultTable);
        resultScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel resultBox = new JPanel();
        resultBox.add(resultScrollPane);
        this.add(resultScrollPane,BorderLayout.CENTER);

        JPanel actionBox = new JPanel();

        String[] actionList = {"Reset Dictionary","Random Slang","Add New Slang","Edit Selected Slang","Delete Slang",
                               "View History","Guess Slang Meaning Quiz","What's The Slang Quiz"};

        for (String act : actionList) {
            JButton actionBtn = new JButton(act);
            actionBtn.addActionListener(this);
            actionBox.add(actionBtn);
        }

        actionBox.setPreferredSize(new Dimension(900,70));

        this.add(actionBox,BorderLayout.SOUTH);

        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ((e.getActionCommand().equals("Search By Keyword")) || (e.getActionCommand().equals("Search By Definition"))) {
            String srchDes = srchSlangField.getText();
            if (e.getActionCommand().equals("Search By Keyword")) {
                SlangWord foundWord = dictionary.searchKey(srchDes);
                tableModel.setRowCount(0);
                if (foundWord != null) {
                    tableModel.addRow(new Object[]{foundWord.getKey(), foundWord.getDef()});
                    historySlangs.add(foundWord);
                }
            }
            if (e.getActionCommand().equals("Search By Definition")) {
                ArrayList<SlangWord> foundWords = dictionary.searchDef(srchDes);
                tableModel.setRowCount(0);
                if (foundWords != null) {
                    for (SlangWord word : foundWords) {
                        tableModel.addRow(new Object[]{word.getKey(), word.getDef()});
                        historySlangs.add(word);
                    }
                }
            }
        }

        if (e.getActionCommand().equals("Delete Slang")) {
            if (!listSelectModel.isSelectionEmpty()) {
                int confirm_code = JOptionPane.showConfirmDialog(null,"Do you want to delete the selected slang permanently (a really long time)?","Delete Confirmation",JOptionPane.YES_NO_OPTION);
                if (confirm_code == 0) {
                    listSelectModel.clearSelection();
                    dictionary.deleteSlang(selectedKey);
                    srchSlangField.setText("");
                    loadAllWords();
                }
            } else {
                JOptionPane.showMessageDialog(null,"You haven't selected any slang to delete!!! Please select a word you want to delete!!!","Word not selected",JOptionPane.WARNING_MESSAGE);
            }
        }

        if (e.getActionCommand().equals("Add New Slang")) {
            new SlangPutFrame(dictionary,"","Add New Slang","Add");
        }

        if (e.getActionCommand().equals("Edit Selected Slang")) {
            if (!listSelectModel.isSelectionEmpty()) {
                new SlangPutFrame(dictionary,selectedKey,"Edit Selected Slang","Confirm");
            } else {
                JOptionPane.showMessageDialog(null,"You haven't selected any slang to delete!!! Please select a word you want to edit!!!","Word not selected",JOptionPane.WARNING_MESSAGE);
            }
        }

        if (e.getActionCommand().equals("Random Slang")) {
            SlangWord randomSlang = dictionary.randomizeSlang();
            JOptionPane.showMessageDialog(null,
                                 "Slang word for today is: " + randomSlang.getKey() +
                                          "\nWith the meaning of: " + randomSlang.getDef(),
                                   "Slang word randomized",
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        if (e.getActionCommand().equals("Reset Dictionary")) {
            if (!listSelectModel.isSelectionEmpty()) {
                listSelectModel.clearSelection();
            }
            try {
                dictionary.loadFile("slang-org.txt");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            loadAllWords();
        }

        if (e.getActionCommand().equals("View History")) {
            if (historySlangs.isEmpty()) {
                JOptionPane.showMessageDialog(null,"You haven't searched any word!!!\nNothing to view!!!","History Empty",JOptionPane.INFORMATION_MESSAGE);
            } else {
                new SlangHistoryFrame(historySlangs);
            }
        }

        if (e.getActionCommand().equals("Guess Slang Meaning Quiz")) {
            new SlangGameFrame("key");
        }

        if (e.getActionCommand().equals("What's The Slang Quiz")) {
            new SlangGameFrame("def");
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
