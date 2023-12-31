package slang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SlangPutFrame extends JFrame implements ActionListener {
    String editKey;
    int actionType;
    SlangDictionary dictionary;

    JTextField keyField;
    JTextField defField;

    JButton confirmBtn;

    SlangPutFrame(SlangDictionary dict,String selectedKey,String title,String confirmText) {
        dictionary = dict;
        editKey = selectedKey;
        if (title.contains("Add")) {
            actionType = 1;
        }
        else {
            actionType = 2;
        }

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle(title);
        this.setLayout(new BorderLayout());
        this.setSize(600,180);

        keyField = new JTextField();
        keyField.setPreferredSize(new Dimension(500,50));

        defField = new JTextField();
        defField.setPreferredSize(new Dimension(500,50));

        JLabel defLabel = new JLabel("Definition: ");

        JPanel inputBox = new JPanel();
        if (actionType == 1) {
            JLabel keyLabel = new JLabel("Key: ");
            inputBox.add(keyLabel);
            inputBox.add(keyField);
        }
        inputBox.add(defLabel);
        inputBox.add(defField);

        this.add(inputBox,BorderLayout.CENTER);

        confirmBtn = new JButton(confirmText);
        confirmBtn.addActionListener(this);

        this.add(confirmBtn,BorderLayout.SOUTH);

        if (actionType == 2) {
            this.pack();
        }
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (actionType == 1) {
            if (keyField.getText().isBlank() || defField.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "You haven't input enough information the new slang!!!", "Word not declared", JOptionPane.WARNING_MESSAGE);
            } else {
                String newKey = keyField.getText();
                String newDef = defField.getText();
                SlangWord newWord = new SlangWord(newKey, newDef);
                if (dictionary.addSlang(newWord)) {
                    this.dispose();
                    SlangWindow.loadAllWords();
                    JOptionPane.showMessageDialog(null, "New slang has been added to the dictionary!!!", "Added Successfully", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int isOverwrite = JOptionPane.showConfirmDialog(null, "Slang key has already existed in the dictionary!!!\nDo you want to overwrite the word???", "Overwrite Slang", JOptionPane.YES_NO_OPTION);
                    if (isOverwrite == 0) {
                        dictionary.updateSlang(newWord);
                        SlangWindow.loadAllWords();
                        this.dispose();
                    }
                }
            }
        }
        if (actionType == 2) {
            if (defField.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "You haven't input the new definition!!!", "New Definition not declared", JOptionPane.WARNING_MESSAGE);
            }
            String newDef = defField.getText();
            SlangWord editedWord = new SlangWord(editKey,newDef);
            if (dictionary.updateSlang(editedWord)) {
                this.dispose();
                SlangWindow.loadAllWords();
                JOptionPane.showMessageDialog(null, "Selected slang has been updated!!!", "Edited Successfully", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
