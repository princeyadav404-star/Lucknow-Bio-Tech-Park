package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProjectPanel extends JPanel {
    private JTextField nameField, leadField;
    private JTextArea descArea;
    private JButton addBtn, refreshBtn, deleteBtn, editBtn;
    private JTable table;
    private ProjectTableModel tableModel;

    public ProjectPanel() {
        setLayout(new BorderLayout());
        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Project Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        form.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Lead Scientist:"), gbc);
        gbc.gridx = 1;
        leadField = new JTextField(20);
        form.add(leadField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descArea = new JTextArea(4,20);
        form.add(new JScrollPane(descArea), gbc);

        addBtn = new JButton("Add Project");
        refreshBtn = new JButton("Refresh List");
        deleteBtn = new JButton("Delete Selected");
        editBtn = new JButton("Edit Selected");

        JPanel btnPanel = new JPanel();
        btnPanel.add(addBtn); btnPanel.add(editBtn); btnPanel.add(deleteBtn); btnPanel.add(refreshBtn);

        add(form, BorderLayout.NORTH);

        tableModel = new ProjectTableModel();
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addProject());
        refreshBtn.addActionListener(e -> loadProjects());
        deleteBtn.addActionListener(e -> deleteSelected());
        editBtn.addActionListener(e -> editSelected());

        loadProjects();
    }

    private void addProject() {
        String name = nameField.getText().trim();
        String lead = leadField.getText().trim();
        String desc = descArea.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Project name required"); return; }

        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO projects (name, description, lead_scientist) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, desc);
            ps.setString(3, lead);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Project added");
            nameField.setText(""); leadField.setText(""); descArea.setText("");
            loadProjects();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadProjects() {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, name, lead_scientist, description FROM projects")) {
            tableModel.setResultSet(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a project to delete"); return; }
        int id = tableModel.getIdAt(row);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete project ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM projects WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Deleted");
            loadProjects();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a project to edit"); return; }
        int id = tableModel.getIdAt(row);
        String currentName = tableModel.getNameAt(row);
        String currentLead = tableModel.getLeadAt(row);
        String currentDesc = tableModel.getDescAt(row);

        JTextField nameF = new JTextField(currentName);
        JTextField leadF = new JTextField(currentLead);
        JTextArea descA = new JTextArea(currentDesc);

        JPanel p = new JPanel(new GridLayout(0,1));
        p.add(new JLabel("Name:")); p.add(nameF);
        p.add(new JLabel("Lead:")); p.add(leadF);
        p.add(new JLabel("Description:")); p.add(new JScrollPane(descA));

        int res = JOptionPane.showConfirmDialog(this, p, "Edit Project", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement ps = con.prepareStatement("UPDATE projects SET name=?, lead_scientist=?, description=? WHERE id=?");
                ps.setString(1, nameF.getText().trim());
                ps.setString(2, leadF.getText().trim());
                ps.setString(3, descA.getText().trim());
                ps.setInt(4, id);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Updated");
                loadProjects();
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
}
