package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ScientistPanel extends JPanel {
    private JTextField nameField, specField;
    private JButton addBtn, refreshBtn, deleteBtn;
    private JTable table;
    private ScientistTableModel tableModel;

    public ScientistPanel() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(3,2,6,6));
        form.add(new JLabel("Name:"));
        nameField = new JTextField(); form.add(nameField);
        form.add(new JLabel("Specialization:"));
        specField = new JTextField(); form.add(specField);
        addBtn = new JButton("Add Scientist");
        refreshBtn = new JButton("Refresh List");
        deleteBtn = new JButton("Delete Selected");
        JPanel btns = new JPanel(); btns.add(addBtn); btns.add(deleteBtn); btns.add(refreshBtn);

        add(form, BorderLayout.NORTH);
        tableModel = new ScientistTableModel();
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addScientist());
        refreshBtn.addActionListener(e -> loadScientists());
        deleteBtn.addActionListener(e -> deleteSelected());

        loadScientists();
    }

    private void addScientist() {
        String name = nameField.getText().trim();
        String spec = specField.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Name required"); return; }
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO scientists (name, specialization) VALUES (?, ?)");
            ps.setString(1, name); ps.setString(2, spec);
            ps.executeUpdate();
            nameField.setText(""); specField.setText("");
            loadScientists();
        } catch (SQLException ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this, ex.getMessage()); }
    }

    private void loadScientists() {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, name, specialization FROM scientists")) {
            tableModel.setResultSet(rs);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select one"); return; }
        int id = tableModel.getIdAt(row);
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM scientists WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            loadScientists();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
