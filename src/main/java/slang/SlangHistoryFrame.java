package slang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SlangHistoryFrame extends JFrame {
    JTable historyTable;
    JScrollPane historyScrollPane;
    JPanel historyBox;

    SlangHistoryFrame(ArrayList<SlangWord> history) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("History");
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(500,500));
        this.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Slang", "Meaning"}, 0) {
            @Override
            public  boolean isCellEditable(int row,int col) {
                return false;
            }
        };

        for (SlangWord word : history) {
                tableModel.addRow(new Object[]{word.getKey(),word.getDef()});
        }

        historyTable = new JTable(tableModel);
        historyTable.setFont(new Font("Roboto",Font.PLAIN,18));
        historyScrollPane = new JScrollPane(historyTable);
        historyScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        historyBox = new JPanel();
        historyBox.add(historyScrollPane,BorderLayout.CENTER);

        this.add(historyBox);

        this.setResizable(false);
        this.setVisible(true);
    }
}
