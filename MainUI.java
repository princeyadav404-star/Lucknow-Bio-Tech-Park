package ui;

import javax.swing.*;

public class MainUI extends JFrame {
    public MainUI() {
        setTitle("Lucknow Biotech Park Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Projects", new ProjectPanel());
        tabs.addTab("Scientists", new ScientistPanel());
        tabs.addTab("Labs", new LabPanel());

        add(tabs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI ui = new MainUI();
            ui.setVisible(true);
        });
    }
}
