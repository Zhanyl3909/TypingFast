import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchScreen {
    private JFrame frame;

    public LaunchScreen() {
        initialize();
    }

    public void show() {
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel titleLabel = new JLabel("Welcome to Typer Game!");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        panel.add(titleLabel);

        JButton startButton = new JButton("Start Typer Game");
        startButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        panel.add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                TyperGame typerGame = new TyperGame();
                typerGame.start();
            }
        });
    }
}
