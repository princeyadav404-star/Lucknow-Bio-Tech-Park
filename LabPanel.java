package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LabPanel extends JPanel {
    private JTextField nameField;
    private JTextArea equipArea;
    private JButton addBtn, refreshBtn, deleteBtn;
    private JTable table;
    private LabTableModel tableModel;

    public LabPanel() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(3,1,6,6));
        nameField = new JTextField();
        equipArea = new JTextArea(3,20);
        form.add(new JLabel("Lab Name:")); form.add(nameField);
        form.add(new JLabel("Equipment (comma separated):")); form.add(new JScrollPane(equipArea));
        addBtn = new JButton("Add Lab"); refreshBtn = new JButton("Refresh List"); deleteBtn = new JButton("Delete Selected");
        JPanel btns = new JPanel(); btns.add(addBtn); btns.add(deleteBtn); btns.add(refreshBtn);

        add(form, BorderLayout.NORTH);
        tableModel = new LabTableModel();
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addLab());
        refreshBtn.addActionListener(e -> loadLabs());
        deleteBtn.addActionListener(e -> deleteSelected());

        loadLabs();
    }

    private void addLab() {
        String name = nameField.getText().trim();
        String eq = equipArea.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Lab name required"); return; }
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO labs (name, equipment) VALUES (?, ?)");
            ps.setString(1, name); ps.setString(2, eq);
            ps.executeUpdate();
            nameField.setText(""); equipArea.setText("");
            loadLabs();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void loadLabs() {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, name, equipment FROM labs")) {
            tableModel.setResultSet(rs);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select one"); return; }
        int id = tableModel.getIdAt(row);
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM labs WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            loadLabs();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
