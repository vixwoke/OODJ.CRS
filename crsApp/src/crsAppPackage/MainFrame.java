package crsAppPackage;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final JPanel cardPanel = new MainCardPanel();

    public MainFrame() {
        setTitle("Course Recovery Plan");
        setMinimumSize(new Dimension(500, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(cardPanel);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}
