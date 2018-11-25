package Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DAO {

    private static int i = 0;
    Connection con;
    Statement st;

    public DAO(String host, String port, String db, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, "" + user, "" + password);
            st = con.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<HashMap<String, String>> resultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, String>> rows = new ArrayList<HashMap<String, String>>();
        while (rs.next()) {
            HashMap<String, String> row = new HashMap<String, String>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i).toString());
            }
            rows.add(row);
        }
        return rows;
    }

    private void valueParsing(StringBuilder set, String v) {
        try {
            int insert = Integer.parseInt(v);
            set.append(insert);
        } catch (NumberFormatException e) {
            set.append("'");
            set.append(v);
            set.append("'");
        }
    }

    public List<HashMap<String, String>> select(String data, String table, String condition) {
        List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        System.out.println("SELECT " + data + " FROM " + table + " WHERE " + condition);
        try (ResultSet rs = st.executeQuery("SELECT " + data + " FROM " + table + " WHERE " + condition)) {
            result = resultSetToList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int insert(String table, HashMap<String, String> values) {
        int key = 0;
        StringBuilder keys = new StringBuilder();
        StringBuilder vals = new StringBuilder();
        values.forEach((k, v) -> {
            valueParsing(vals, v);
            keys.append(k);
            i++;
            if (i < values.size()) {
                vals.append(',');
                keys.append(',');
            }
        });

        System.out.println(keys);
        System.out.println(vals);

        try {
            System.out.println("INSERT INTO " + table + " " + keys + " VALUES ( " + vals + " )");
            st.executeUpdate("INSERT INTO " + table + " (" + keys + ") VALUES ( " + vals + " )", Statement.RETURN_GENERATED_KEYS);
            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    key = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    public void update(String table, HashMap<String, String> values, String condition) {
        StringBuilder set = new StringBuilder();
        i = 0;
        values.forEach((k, v) -> {
            set.append(k);
            set.append(" = ");
            valueParsing(set, v);
            i++;
            if (i < values.size()) {
                set.append(',');
            }

        });
        try {
            System.out.println("UPDATE " + table + " SET " + set + " WHERE " + condition);
            st.executeUpdate("UPDATE " + table + " SET " + set + " WHERE " + condition);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String table, String condition) {
        try {
            System.out.println("DELETE FROM " + table + " WHERE " + condition);
            st.executeUpdate("DELETE FROM " + table + " WHERE " + condition);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            st.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
