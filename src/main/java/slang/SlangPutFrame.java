package slang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SlangPutFrame extends JFrame implements ActionListener {
    SlangDictionary dictionary;

    JLabel keyLabel = new JLabel("Key: ");
    JLabel defLabel = new JLabel("Definition: ");

    JTextField keyField;
    JTextField defField;

    JButton confirmBtn;

    JPanel inputBox;
    SlangPutFrame(SlangDictionary dict,String title,String confirmText) {
        dictionary = dict;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle(title);
        this.setLayout(new BorderLayout());
        this.setSize(600,180);

        keyField = new JTextField();
        keyField.setPreferredSize(new Dimension(500,50));

        defField = new JTextField();
        defField.setPreferredSize(new Dimension(500,50));

        inputBox = new JPanel();
        inputBox.add(keyLabel);
        inputBox.add(keyField);
        inputBox.add(defLabel);
        inputBox.add(defField);

        this.add(inputBox,BorderLayout.CENTER);

        confirmBtn = new JButton(confirmText);
        confirmBtn.addActionListener(this);

        this.add(confirmBtn,BorderLayout.SOUTH);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (keyField.getText().isBlank() || defField.getText().isBlank()) {
            JOptionPane.showMessageDialog(null,"You haven't input enough information the new slang!!!","Word not declared",JOptionPane.WARNING_MESSAGE);
        }
        else {
            String newKey = keyField.getText();
            String newDef = defField.getText();
            SlangWord newWord = new SlangWord(newKey,newDef);
            if (dictionary.addSlang(newWord)) {
                this.dispose();
                JOptionPane.showMessageDialog(null,"New Slang has been added to the dictionary!!!","Added Successfully",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"Slang key has already existed in the dictionary!!!\nPlease change the keyword or add new words!!!","Added Failed",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
