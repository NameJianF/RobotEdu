package robot.client.db;

import com.mysql.jdbc.Connection;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import robot.client.common.Config;
import robot.client.util.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Feng on 2017/5/15.
 */
public class DbHelper {
    private static Connection conn = null;


    public DbHelper() {
    }

    public static void initConnection() throws SQLException {
        conn = (Connection) DriverManager.getConnection(Config.JDBC_URL, Config.JDBC_USERNAME, Config.JDBC_PASSWORD);
    }

    /**
     * 查询
     *
     * @param sql
     * @param rsh
     * @param params
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.query(conn, sql, rsh, params);
        }
        return null;
    }

    /**
     * 查询
     *
     * @param sql
     * @param rsh
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.query(conn, sql, rsh);
        }
        return null;
    }


    /**
     * 批量执行INSERT、UPDATE、DELETE
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static int[] batch(String sql, Object[][] params) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.batch(conn, sql, params);
        }
        return null;
    }

    /**
     * 执行一个插入查询语句
     *
     * @param sql
     * @param rsh
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> T insert(String sql, ResultSetHandler<T> rsh) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.insert(conn, sql, rsh);
        }
        return null;
    }

    /**
     * 执行一个插入查询语句
     *
     * @param sql
     * @param rsh
     * @param params
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> T insert(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.insert(conn, sql, rsh, params);
        }
        return null;
    }

    /**
     * 批量执行插入语句
     *
     * @param sql
     * @param rsh
     * @param params
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> T insertBatch(String sql, ResultSetHandler<T> rsh, Object[][] params) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.insert(conn, sql, rsh, params);
        }
        return null;
    }

    /**
     * 执行INSERT、UPDATE、DELETE
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    public static int update(String sql) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.update(conn, sql);
        }
        return -1;
    }

    /**
     * 执行INSERT、UPDATE、DELETE
     *
     * @param sql
     * @param param
     * @return
     * @throws SQLException
     */
    public static int update(String sql, Object param) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.update(conn, sql, param);
        }
        return -1;
    }

    /**
     * 执行INSERT、UPDATE、DELETE
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static int update(String sql, Object... params) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.update(conn, sql, params);
        }
        return -1;
    }

    public static void testDb() {
        try {
            List<Object[]> results = DbHelper.query("select * from admin_dict", new ArrayListHandler());
            for (Object[] object : results) {
                System.out.println(Arrays.asList(object));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
