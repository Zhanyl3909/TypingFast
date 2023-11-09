import javax.swing.*;

public class TypingFastApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LaunchScreen launchScreen = new LaunchScreen();
            launchScreen.show();
        });
    }
}
