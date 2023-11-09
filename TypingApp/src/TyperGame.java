import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;


public class TyperGame {
    private JFrame frame;
    private JLabel currentWord;
    private JLabel nextWord;
    private JLabel scoreLabel;
    private JTextField userInput;
    private JButton enterBtn;
    private JLabel timerLabel;
    private JButton startBtn;
    private JButton restartBtn;

    private ArrayList<String> level1 = new ArrayList<>();
    private ArrayList<String> level2 = new ArrayList<>();
    private ArrayList<String> level3 = new ArrayList<>();
    private ArrayList<String> level4 = new ArrayList<>();
    private ArrayList<String> level5 = new ArrayList<>();
    private ArrayList<String> level6 = new ArrayList<>();

    private ArrayList<ArrayList<String>> allLevels = new ArrayList<>();
    private int count = 0;
    private int currLevel = 0;
    private int currScore = 0;
    private String original = "";
    private String original2 = "";
    private int sec = 30;
    private Timer t;
    private int streak = 0;

    public TyperGame() {
       // initialize();
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame();
            frame.setBounds(100, 100, 800, 600);
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    System.exit(0);
                }
            });

            initializeGameComponents();
            frame.setVisible(true);
            firstGame();
        });
    }

    private void initializeGameComponents() {
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(250, 241, 228));

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

        nextWord = new JLabel("\"\"");
        nextWord.setFont(new Font("Tahoma", Font.PLAIN, 25));
        nextWord.setForeground(new Color(0, 0, 0, 150));

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
        enterBtn.setEnabled(false);

        GridBagConstraints gbc_enterBtn = new GridBagConstraints();
        gbc_enterBtn.insets = new Insets(10, 10, 10, 10);
        gbc_enterBtn.gridx = 0;
        gbc_enterBtn.gridy = 4;
        gbc_enterBtn.gridwidth = 2;
        panel.add(enterBtn, gbc_enterBtn);

        startBtn = new JButton("Start");
        startBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));

        GridBagConstraints gbc_startBtn = new GridBagConstraints();
        gbc_startBtn.insets = new Insets(10, 10, 10, 10);
        gbc_startBtn.gridx = 0;
        gbc_startBtn.gridy = 5;
        gbc_startBtn.gridwidth = 2;
        panel.add(startBtn, gbc_startBtn);

        ImageIcon play = new ImageIcon("src/Icons/play.png");
        Image img = play.getImage();
        Image imgNew = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        play = new ImageIcon(imgNew);
        startBtn.setIcon(play);
        startBtn.setBackground(Color.RED);

        restartBtn = new JButton("Restart");
        ImageIcon again = new ImageIcon("src/Icons/again.png");
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
                String userWord = userInput.getText().trim();
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
