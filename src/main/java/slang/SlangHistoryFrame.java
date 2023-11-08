package slang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SlangHistoryFrame extends JFrame {
    SlangHistoryFrame(ArrayList<SlangWord> history) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("History");
        this.setSize(new Dimension(500,500));
        this.setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Slang", "Meaning"}, 0) {
            @Override
            public  boolean isCellEditable(int row,int col) {
                return false;
            }
        };
        tableModel.setRowCount(0);

        for (SlangWord word : history) {
            tableModel.addRow(new Object[]{word.getKey(),word.getDef()});
        }

        JTable historyTable = new JTable(tableModel);
        historyTable.setFont(new Font("Roboto",Font.PLAIN,18));
        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        historyScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel historyBox = new JPanel();
        historyBox.add(historyScrollPane);

        JButton clearBtn = new JButton("Clear");
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                history.clear();
                tableModel.setRowCount(0);
                dispose();
                JOptionPane.showMessageDialog(null,"History has been cleared!!!\nNothing to view!!!","History Empty",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        this.add(historyBox,BorderLayout.CENTER);
        this.add(clearBtn,BorderLayout.SOUTH);

        this.setResizable(false);
        this.setVisible(true);
    }
}
