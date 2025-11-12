package ui;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabTableModel extends AbstractTableModel {
    private final String[] cols = {"ID", "Name", "Equipment"};
    private final List<Object[]> rows = new ArrayList<>();

    public void setResultSet(ResultSet rs) throws SQLException {
        rows.clear();
        while (rs.next()) {
            Object[] r = new Object[3];
            r[0] = rs.getInt("id");
            r[1] = rs.getString("name");
            r[2] = rs.getString("equipment");
            rows.add(r);
        }
        fireTableDataChanged();
    }

    @Override public int getRowCount() { return rows.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }
    @Override public Object getValueAt(int rowIndex, int columnIndex) { return rows.get(rowIndex)[columnIndex]; }
    public int getIdAt(int row) { return (Integer) rows.get(row)[0]; }
}
