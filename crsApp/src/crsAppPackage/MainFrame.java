package crsAppPackage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private final MainCardPanel cardPanel = new MainCardPanel();

    public MainFrame() {
        setTitle("Course Recovery Plan");
        setMinimumSize(new Dimension(500, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(cardPanel);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        // When close record the timestamp if the user has logged in
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Record timestamp
                /*if (cardPanel.isLoggedIn()) {
                    try {
                        LocalDateTime currentTime = LocalDateTime.now();
                        FileManager.addData("Resources/Data/Timestamp.txt", new String[] { "LOG OUT", currentTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) });
                    } catch (Exception ex) {
                    }
                }*/
                dispose();
            }
        });
    }
}
