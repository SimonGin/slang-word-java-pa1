package slang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class SlangGameFrame extends JFrame implements ActionListener {
    ArrayList<SlangWord> choices;
    int result;
    JButton optBtn;
    JLabel quizLabel;
    String quizType;

    SlangGameFrame(String type) {
        quizType = type;
        choices = new ArrayList<>();

        while (true) {
            SlangWord randomSlang = SlangWindow.dictionary.randomizeSlang();
            if (!choices.contains(randomSlang)) {
                choices.add(randomSlang);
            }
            if (choices.size() == 4) {
                break;
            }
        }

        result = new Random().nextInt(4) + 1;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(800,400));
        this.setLayout(new BorderLayout());

        JPanel quizBox = new JPanel();
        quizBox.setBackground(Color.CYAN);
        quizBox.setPreferredSize(new Dimension(800,65));
        quizBox.setLayout(new GridLayout(2,1));

        String quizStr = "";

        if (type.equals("key")) {
            quizLabel = new JLabel("According to the Slang dictionary, what is the meaning of",SwingConstants.CENTER);
            quizStr = choices.get(result).getKey();
        }
        else if (type.equals("def")) {
            quizLabel = new JLabel("According to the Slang dictionary, which slang has the below meaning:",SwingConstants.CENTER);
            quizStr = choices.get(result).getDef();
        }

        quizLabel.setFont(new Font("Roboto",Font.BOLD,18));
        quizBox.add(quizLabel);
        quizLabel = new JLabel(quizStr,SwingConstants.CENTER);
        quizLabel.setFont(new Font("Roboto",Font.BOLD,20));

        quizBox.add(quizLabel);

        this.add(quizBox,BorderLayout.NORTH);

        JPanel optionBox = new JPanel();
        optionBox.setLayout(new GridLayout(2,2));

        for (int i = 0;i < 4;i++) {
            if (type.equals("key")) {
                optBtn = new JButton(choices.get(i).getDef());
            }
            else if (type.equals("def")) {
                optBtn = new JButton(choices.get(i).getKey());
            }
            optBtn.addActionListener(this);
            optionBox.add(optBtn);
        }

        this.add(optionBox,BorderLayout.CENTER);

        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (quizType.equals("key")) {
            if (e.getActionCommand().equals(choices.get(result).getDef())) {
                dispose();
                JOptionPane.showMessageDialog(null,"Congratulation!!!\nYou Won The Game!!!","Right Answer",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"You're wrong!!!\nPlease choose another option!!!","Wrong Answer",JOptionPane.ERROR_MESSAGE);
            }
        }

        else if (quizType.equals("def")) {
            if (e.getActionCommand().equals(choices.get(result).getKey())) {
                dispose();
                JOptionPane.showMessageDialog(null,"Congratulation!!!\nYou Won The Game!!!","Right Answer",JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null,"You're wrong!!!\nPlease choose another option!!!","Wrong Answer",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
