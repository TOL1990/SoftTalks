package model.dao.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Taras on 03.03.2017.
 */
public class DaoUtils {
    public static final String USERNAME = "root";
    public static final String PASSWORD = "1111";
    public static final String URL = "jdbc:mysql://localhost:3306/mysql";
    public static void close (Connection con, Statement st, ResultSet rs) throws SQLException
    {
        if(rs !=null )rs.close();
        if(st != null) st.close();
        if(con != null) con.close();
    }
    public static void close(Statement st,ResultSet rs)throws SQLException
    {
        close(null, st, rs);
    }
    public static void close(Statement st)throws SQLException
    {
        close(null, st, null);
    }

    public static Connection getConnection() {

        return new DbProcessor().getCon(USERNAME, PASSWORD, URL);
    }
}
