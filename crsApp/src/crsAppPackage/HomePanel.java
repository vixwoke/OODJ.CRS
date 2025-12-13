package crsAppPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class HomePanel extends JPanel implements IPanelNavigation {
    public static final String CONSTRAINTS = "HOME";
    // PanelNavigation
    private final IPanelNavigation navigator;
    // panels and layouts
    private final JPanel westPanel = new JPanel(new BorderLayout());
    private final CardLayout centerLayout = new CardLayout();
    private final JPanel centerPanel = new JPanel(centerLayout);
    // West components
    private final JPanel westCenterPanel = new JPanel();
    private final JButton btnShowMenu = new JButton("<");

    public HomePanel(IPanelNavigation navigator) {
        this.navigator = navigator;
        setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
    }

    public void goTo(String constraints) {
        centerLayout.show(centerPanel, constraints);
    };

    public void goNext() {};

    public void goBack() {
        // Record timestamp
        /*
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            FileManager.addData("Resources/Data/Timestamp.txt", new String[] { "LOG OUT", currentTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) });
        } catch (Exception e) {
        }
        */
        
        navigator.goBack();
    };

    protected abstract void placeCenterPanels();
    protected abstract void placeWestCenterButtons();

    protected void setActions() {
        // btnShowMenu
        btnShowMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                westCenterPanel.setVisible(!westCenterPanel.isVisible());
                btnShowMenu.setText((westCenterPanel.isVisible()) ? "<" : ">");
            }
        });

        westPanel.add(westCenterPanel, BorderLayout.CENTER);
        westPanel.add(btnShowMenu, BorderLayout.EAST);

        add(westPanel, BorderLayout.WEST);
    }

    protected final void setWestCenterPanelLayout(int numOfButtons) {
        westCenterPanel.setLayout(new GridLayout(numOfButtons, 1));
    }

    protected final void addWestCenterPanel(JButton button) {
        westCenterPanel.add(button);
    }

    protected final void addCenterPanel(JPanel panel, String constraints) {
        centerPanel.add(panel, constraints);
    }
}