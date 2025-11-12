package ui;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectTableModel extends AbstractTableModel {
    private final String[] cols = {"ID", "Name", "Lead Scientist", "Description"};
    private final List<Object[]> rows = new ArrayList<>();

    public void setResultSet(ResultSet rs) throws SQLException {
        rows.clear();
        while (rs.next()) {
            Object[] r = new Object[4];
            r[0] = rs.getInt("id");
            r[1] = rs.getString("name");
            r[2] = rs.getString("lead_scientist");
            r[3] = rs.getString("description");
            rows.add(r);
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() { return rows.size(); }
    @Override
    public int getColumnCount() { return cols.length; }
    @Override
    public String getColumnName(int column) { return cols[column]; }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows.get(rowIndex)[columnIndex];
    }

    public int getIdAt(int row) { return (Integer) rows.get(row)[0]; }
    public String getNameAt(int row) { return (String) rows.get(row)[1]; }
    public String getLeadAt(int row) { return (String) rows.get(row)[2]; }
    public String getDescAt(int row) { return (String) rows.get(row)[3]; }
}
