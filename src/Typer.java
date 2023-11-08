
//importing libraries
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.Color;

public class Typer {

    //initializing global Components
    private JFrame frame;
    private JLabel currentWord;
    private JLabel nextWord;
    private JLabel scoreLabel;
    private JTextField userInput;
    private JButton enterBtn;
    private JLabel timerLabel;
    private JButton startBtn;
    private ImageIcon play, again;
    private JButton restartBtn;


    //prepared text for typing by ME (6 levels)
    private ArrayList<String> level1 = new ArrayList<String>();
    private ArrayList<String> level2 = new ArrayList<String>();
    private ArrayList<String> level3 = new ArrayList<String>();
    private ArrayList<String> level4 = new ArrayList<String>();
    private ArrayList<String> level5 = new ArrayList<String>();
    private ArrayList<String> level6 = new ArrayList<String>();


    private ArrayList<ArrayList<String>> allLevels = new ArrayList<ArrayList<String>>();
    private int count = 0;
    private int currLevel = 0;
    private int currScore = 0;
    private String original = "";
    private String original2 = "";
    private int sec = 30;   //given time
    private Timer t;
    private int streak = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Typer window = new Typer();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Typer() {
        initialize();
    }

    //Frame properties
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        //Create Panel
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        panel.setLayout(gbl_panel);
        panel.setBackground(new Color(254, 245, 237));
        /*
        * Going to change Time & Score font little bit
         */

        timerLabel = new JLabel("Time: " + sec);
        GridBagConstraints gbc_timerLabel = new GridBagConstraints();
        gbc_timerLabel.insets = new Insets(10, 10, 5, 5);
        gbc_timerLabel.gridx = 0;
        gbc_timerLabel.gridy = 0;
        panel.add(timerLabel, gbc_timerLabel);

        scoreLabel = new JLabel("Score: " + currScore);
        GridBagConstraints gbc_scoreLabel = new GridBagConstraints();
        gbc_scoreLabel.insets = new Insets(10, 10, 5, 10);
        gbc_scoreLabel.gridx = 1;
        gbc_scoreLabel.gridy = 0;
        panel.add(scoreLabel, gbc_scoreLabel);

        currentWord = new JLabel("\"\"");
        currentWord.setFont(new Font("Tahoma", Font.BOLD, 40));
        GridBagConstraints gbc_currentWord = new GridBagConstraints();
        gbc_currentWord.insets = new Insets(20, 10, 10, 10);
        gbc_currentWord.gridx = 0;
        gbc_currentWord.gridy = 1;
        gbc_currentWord.gridwidth = 2;
        panel.add(currentWord, gbc_currentWord);

        //Size and visibility change
        nextWord = new JLabel("\"\"");
        nextWord.setFont(new Font("Tahoma", Font.PLAIN, 25));

        //makes the next word more transparent
        nextWord.setForeground(new Color(0, 0, 0, 150)); // transparency 



        GridBagConstraints gbc_nextWord = new GridBagConstraints();
        gbc_nextWord.insets = new Insets(10, 10, 10, 10);
        gbc_nextWord.gridx = 0;
        gbc_nextWord.gridy = 2;
        gbc_nextWord.gridwidth = 2;
        panel.add(nextWord, gbc_nextWord);

        userInput = new JTextField();
        userInput.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_userInput = new GridBagConstraints();
        gbc_userInput.fill = GridBagConstraints.HORIZONTAL;
        gbc_userInput.insets = new Insets(10, 10, 10, 10);
        gbc_userInput.gridx = 0;
        gbc_userInput.gridy = 3;
        gbc_userInput.gridwidth = 2;
        panel.add(userInput, gbc_userInput);
        userInput.setColumns(10);
        userInput.setEnabled(false);

        enterBtn = new JButton("Enter");
        enterBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        enterBtn.setBackground(new Color(153, 167, 153));

        enterBtn.setEnabled(false);
        GridBagConstraints gbc_enterBtn = new GridBagConstraints();
        gbc_enterBtn.insets = new Insets(10, 10, 10, 10);
        gbc_enterBtn.gridx = 0;
        gbc_enterBtn.gridy = 4;
        gbc_enterBtn.gridwidth = 2;

//
//        defaultButton.setBackground(Color.BLUE);
//        defaultButton.setForeground(Color.WHITE);
//        defaultButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        panel.add(enterBtn, gbc_enterBtn);

        startBtn = new JButton("Start");
        startBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_startBtn = new GridBagConstraints();
        gbc_startBtn.insets = new Insets(10, 10, 10, 10);
        gbc_startBtn.gridx = 0;
        gbc_startBtn.gridy = 5;
        gbc_startBtn.gridwidth = 2;
        panel.add(startBtn, gbc_startBtn);

        play = new ImageIcon("src/Icons/play.png");
        Image img = play.getImage();
        Image imgNew = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        play = new ImageIcon(imgNew);
        startBtn.setIcon(play);
        startBtn.setBackground(Color.GREEN);

        restartBtn = new JButton("Restart");
        again = new ImageIcon("src/Icons/again.png");
        Image img2 = again.getImage();
        Image imgNew2 = img2.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        again = new ImageIcon(imgNew2);
        restartBtn.setIcon(again);
        restartBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_restartBtn = new GridBagConstraints();
        gbc_restartBtn.insets = new Insets(10, 10, 10, 10);
        gbc_restartBtn.gridx = 0;
        gbc_restartBtn.gridy = 6;
        gbc_restartBtn.gridwidth = 2;
        panel.add(restartBtn, gbc_restartBtn);

        allLevels.add(level1);
        allLevels.add(level2);
        allLevels.add(level3);
        allLevels.add(level4);
        allLevels.add(level5);
        allLevels.add(level6);


        for (int i = 0; i < 6; i++) {
            readText("src/TestText/level" + (i + 1) + ".txt", allLevels.get(i));
        }

        firstGame();

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterBtn.setEnabled(true);
                startBtn.setEnabled(false);
                userInput.setEnabled(true);
                frame.getRootPane().setDefaultButton(enterBtn);
                userInput.requestFocusInWindow();
                startBtn.setBackground(null);
                time();
            }
        });

        enterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userWord = userInput.getText().trim(); // Trim the user input
                if (userWord.equals(original)) {
                    updateScore();
                    game();
                    userInput.setText("");
                    userInput.requestFocusInWindow();
                    streak++;
                    if (streak == 5) {
                        streak = 0;
                        sec += 5;
                        timerLabel.setText("Time: " + sec);
                    }
                } else {
                    if (sec < 5) {
                        sec = 0;
                        timerLabel.setText("Time: " + sec);
                        userInput.setText("");
                        game();
                    } else {
                        sec -= 5;
                        timerLabel.setText("Time: " + sec);
                        userInput.setText("");
                        game();
                    }
                }
            }
        });


        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Restarting! " + "\n" + "Your Score: " + currScore);
                resetGame();
            }
        });
    }

    public void readText(String path, ArrayList<String> currentArr) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(path));
            String word;
            word = bf.readLine();

            while (word != null) {
                currentArr.add(word);
                word = bf.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void levelCount() {
        count++;
        if (count % 2 == 0) {
            count = 0;
            currLevel++;
        }
    }

    public String randomTextPicker() {
        ArrayList<String> input = allLevels.get(currLevel);
        Random random = new Random();
        int randomNumber = random.nextInt(input.size());

        String output = input.get(randomNumber);
        levelCount();
        return output;
    }

    public void firstGame() {
        original = randomTextPicker();
        currentWord.setText(original);
        original2 = randomTextPicker();
        nextWord.setText(original2);
    }

    public void game() {
        original2 = randomTextPicker();
        original = nextWord.getText();
        currentWord.setText(original);
        nextWord.setText(original2);
    }

    public void updateScore() {
        currScore++;
        scoreLabel.setText("Score: " + currScore);
    }

    public void time() {
        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sec <= 0) {
                    t.stop();
                    JOptionPane.showMessageDialog(null, "Game Over! " + "\n" + "Your Score: " + currScore);
                    userInput.setEnabled(false);
                    enterBtn.setEnabled(false);
                    sec = 30;
                    resetGame();
                }
                sec--;
                timerLabel.setText("Time: " + sec);
            }
        });
        t.start();
    }

    public void resetGame() {
        for (int i = 0; i < allLevels.size(); i++) {
            allLevels.get(i).clear();
        }
        for (int i = 0; i < 6; i++) {
            readText("src/TestText/level" + (i + 1) + ".txt", allLevels.get(i));
        }

        count = 0;
        currLevel = 0;
        currScore = 0;
        scoreLabel.setText("Score: " + currScore);
        original = "";

        if (sec != 30) {
            t.stop();
        }

        sec = 30;
        timerLabel.setText("Time: " + sec);
        userInput.setEnabled(false);
        startBtn.setEnabled(true);
        startBtn.setBackground(Color.GREEN);
        enterBtn.setEnabled(false);
        frame.getRootPane().setDefaultButton(startBtn);
        frame.repaint();
        frame.revalidate();
        userInput.setText("");
        userInput.requestFocusInWindow();
        firstGame();
    }





}
