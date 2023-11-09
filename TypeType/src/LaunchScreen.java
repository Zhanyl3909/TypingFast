

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;

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

        //size of the screen
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screnWidth = d.width;
        frame.setSize(screnWidth/4, screenHeight/2);
        frame.setLocation(screnWidth/4, screenHeight/4);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setBackground(new Color(250, 241, 228));






        JLabel titleLabel = new JLabel("Practice Typing");
        titleLabel.setFont(new Font("Merriweather", Font.BOLD , 40));
        titleLabel.setForeground(new Color(22,72,99));
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.insets = new Insets(20, 10, 20, 10);
        gbc_titleLabel.gridx = 0;
        gbc_titleLabel.gridy = 0;
        panel.add(titleLabel, gbc_titleLabel);

        JButton startButton = new JButton("Start Typing");
        startButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_startButton = new GridBagConstraints();
        gbc_startButton.insets = new Insets(10, 10, 10, 10);
        gbc_startButton.gridx = 0;
        gbc_startButton.gridy = 1;
        panel.add(startButton, gbc_startButton);

        try {
            InputStream stream = getClass().getResourceAsStream("/Icons/keyboard.jpg");
            if (stream != null) {
                Image image = ImageIO.read(stream);
                Image newIM = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                frame.setIconImage(newIM);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        startButton.addActionListener(e -> {
            frame.dispose();
            TyperGame typerGame = new TyperGame();
            typerGame.start();
        });
    }
}





