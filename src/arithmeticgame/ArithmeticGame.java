package arithmeticgame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ArithmeticGame extends JFrame {

    JButton[] button = new JButton[9];
    JButton clear, zero;

    JPanel p1, p2, p3, p4, p5;
    JLabel label1, timelabel, userinput;

    Timer timer;
    JProgressBar pbar;

    static int num1 = 0, num2 = 0;
    static String operator = "reload", innerscore = "", userinter = "";
    int second = 100, score = 0, sum = 0;

    ArithmeticGame() {

        super("Arithimetic Calculator");

        getRandom();

        pbar = new JProgressBar();
        pbar.setMaximum(100);
        pbar.setMinimum(0);
        pbar.setStringPainted(false);

        timelabel = new Display();
        timelabel.setFont(new Font("Serif", 3, 30));

        label1 = new JLabel("" + num1 + operator + num2);
        label1.setFont(new Font("Serif", 3, 30));

        p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        p3.setBorder(new LineBorder(Color.GRAY, 2));
        p3.setBackground(Color.LIGHT_GRAY);
        p3.add(label1);

        p1 = new JPanel(new GridLayout(3, 3));
        p1.setBorder(new LineBorder(Color.GRAY, 3));

        for (int i = 8; i >= 0; i--) {
            button[i] = new JButton("" + (i + 1));
            button[i].setBackground(Color.BLACK);
            button[i].setForeground(Color.WHITE);
            button[i].setFont(new Font("Serif", 3, 30));
            p1.add(button[i]);
        }

        clear = new JButton("Clear");
        clear.setBackground(Color.BLACK);
        clear.setForeground(Color.WHITE);
        clear.setFont(new Font("Serif", 3, 30));

        zero = new JButton("0");
        zero.setBackground(Color.BLACK);
        zero.setForeground(Color.WHITE);
        zero.setFont(new Font("Serif", 3, 30));

        p2 = new JPanel(new GridLayout(2, 1));
        p2.setBorder(new LineBorder(Color.BLACK, 1));
        p2.add(clear);
        p2.add(zero);

        p5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        p5.setBorder(new TitledBorder("user input"));
        p5.setBackground(Color.WHITE);
        userinput = new JLabel();
        userinput.setFont(new Font("Serif", 3, 15));

        p5.add(userinput);

        p4 = new JPanel(new BorderLayout());
        p4.setBackground(Color.yellow);
        p4.setBorder(new LineBorder(Color.GRAY, 2));
        p4.add(timelabel, BorderLayout.WEST);
        p4.add(pbar, BorderLayout.CENTER);
        p4.add(p3, BorderLayout.NORTH);
        p4.add(p5, BorderLayout.SOUTH);

        add(p1, BorderLayout.CENTER);
        add(p2, BorderLayout.EAST);
        add(p4, BorderLayout.NORTH);

        ButtonListener listener = new ButtonListener();

        for (int i = 0; i < 9; i++) {
            button[i].addActionListener(listener);
        }
        clear.addActionListener(listener);
        zero.addActionListener(listener);
    }

    public static void main(String[] args) {
        JFrame frame = new ArithmeticGame();

        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void getRandomForDiv() {

        Random random = new Random();

        num1 = (int) (Math.random() * 101);
        num2 = random.nextInt(26);

        while (true) {
           
            if (num2 < 2) {
                num2 += 2;

            }

            if (num1 < 25) {
                num1 += 20;
                continue;
            }

            if (num1 % num2 == 0) {
                break;
            }
            num2 = random.nextInt(26);
            num2++;
        }

    }
    public static void getRandom() {

        switch ((int) (Math.random() * 4)) {
            case 0:
                num1 = (int) (Math.random() * 100);
                num2 = (int) (Math.random() * 100);
                operator = "+";
                break;
            case 1:
                num1 = (int) (Math.random() * 100);
                num2 = (int) (Math.random() * 100);
                if (num1 < num2) {
                    num1 += num2;
                    num2 = num1 - num2;
                    num1 -= num2;
                }
                operator = "-";
                break;
            case 2:
                num1 = (int) (Math.random() * 13);
                num2 = (int) (Math.random() * 13);
                operator = "*";
                break;
            case 3:
                operator = "/";
                getRandomForDiv();
                break;
        }
    }
    public void resetGame() {
        score = 0;
        second = 100;
        getRandom();
        sum = 0;
        label1.setText(String.valueOf(num1 + operator + num2));
        innerscore = "";
        userinput.setText(innerscore);
        timer.start();
    }
    public class Display extends JLabel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            FontMetrics fontmetrics = g.getFontMetrics();

            int stringwidth = fontmetrics.stringWidth("" + second);
            int stringheight = fontmetrics.getAscent();

            int xcoordinate = getWidth() / 2 - stringwidth / 2;
            int ycoordinate = getHeight() / 2 + stringheight / 2;

            g.drawString("" + second, xcoordinate, ycoordinate);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(40, 40);
        }

        Display() {
            second = 100;
            timer = new Timer(1000, new TimerListener());
            timer.start();
        }

        public class TimerListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                second--;
                if (second >= 0) {
                    pbar.setValue(second);
                    repaint();
                } else {

                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Game Over!\nYou scored " + score + " points");
                    int yes = JOptionPane.showConfirmDialog(null, "Do you went to Continue");

                    switch (yes) {
                        case JOptionPane.YES_OPTION:
                            resetGame();
                            break;
                        case JOptionPane.NO_OPTION:
                            timer.stop();
                            System.exit(0);
                            break;
                        case JOptionPane.CANCEL_OPTION:
                            timer.stop();
                            break;
                        default:
                            break;
                    }
                }
            }

        }
    }
    public class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            switch (operator) {
                case "+":
                    sum = num1 + num2;
                    break;
                case "-":
                    sum = num1 - num2;
                    break;
                case "*":
                    sum = num1 * num2;
                    break;
                case "/":
                    sum = num1 / num2;
                    break;
            }
            for (int i = 0; i < 9; i++) {
                if (e.getSource() == button[i]) {
                    innerscore += button[i].getText();
                    userinput.setText(innerscore);
                }
            }
            if (e.getSource() == clear) {
                innerscore = "";
                userinput.setText(innerscore);
            } else if (e.getSource() == zero) {

                innerscore += zero.getText();
                userinput.setText(innerscore);
            }

            if (innerscore.length() != 0 && sum == Integer.parseInt(innerscore)) {
                 
                score += 5;
                innerscore = "";
                userinput.setText(innerscore);
                getRandom();
                label1.setText(String.valueOf(num1 + operator + num2));

            } else if (innerscore.length() != 0 && sum < Integer.parseInt(innerscore)) {
                second = second > 20 ? second -= 10 : second;
                innerscore = "";
                userinput.setText(innerscore);
            }

        }
    }

}
