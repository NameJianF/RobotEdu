package robot.client.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import robot.client.common.Config;
import robot.client.util.Logger;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Feng on 2017/5/15.
 */
public class DbHelper {
    private static Connection conn = null;

    public static final Object LOCK = new Object();

    public DbHelper() {
    }

    public static void initConnection() throws SQLException {
        conn = DriverManager.getConnection(Config.JDBC_URL);

        // create tables if needs
        initTables();

//        conn = (Connection) DriverManager.getConnection(Config.JDBC_URL, Config.JDBC_USERNAME, Config.JDBC_PASSWORD);
    }

    private static void initTables() throws SQLException {
        // check db
//        if (conn != null) {
//            DatabaseMetaData meta = conn.getMetaData();
//            System.out.println("The driver name is " + meta.getDriverName());
//            System.out.println("A new database has been created.");
//        }

        int ret = -1;
        // edu_card_info
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("	CREATE TABLE IF NOT EXISTS edu_card_info (	");
        stringBuffer.append("	id INTEGER PRIMARY KEY	AUTOINCREMENT,	");
        stringBuffer.append("	card_no VARCHAR (50),	");
        stringBuffer.append("	card_type CHAR (1),	");
        stringBuffer.append("	total_times INT,	");
        stringBuffer.append("	used_times INT,	");
        stringBuffer.append("	price INTEGER	");
        stringBuffer.append("	DEFAULT (0),	");
        stringBuffer.append("	discount INTEGER ");
        stringBuffer.append("	DEFAULT (100),	");
        stringBuffer.append("	adviser VARCHAR (20),	");
        stringBuffer.append("	flag CHAR (1)	");
        stringBuffer.append("	DEFAULT (0),	");
        stringBuffer.append("	upload CHAR (1) DEFAULT (0),	");
        stringBuffer.append("	create_time BIGINT (20),	");
        stringBuffer.append("	update_time BIGINT (20)	");
        stringBuffer.append("	);	");
        ret = update(stringBuffer.toString());
        Logger.debug("CREATE TABLE edu_card_info : " + ret);

        // edu_customer_info
        stringBuffer = new StringBuffer();
        stringBuffer.append("	CREATE TABLE IF NOT EXISTS edu_customer_info (	");
        stringBuffer.append("	id INTEGER PRIMARY KEY	AUTOINCREMENT,	");
        stringBuffer.append("	adviser VARCHAR (20),	");
        stringBuffer.append("	card_no VARCHAR (50),	");
        stringBuffer.append("	child_name VARCHAR (20),	");
        stringBuffer.append("	child_sex CHAR (1)	");
        stringBuffer.append("	DEFAULT (1),	");
        stringBuffer.append("	birthday VARCHAR (20),	");
        stringBuffer.append("	child_image VARCHAR (100),	");
        stringBuffer.append("	mom_name VARCHAR (20),	");
        stringBuffer.append("	mom_mobile VARCHAR (20),	");
        stringBuffer.append("	mom_email VARCHAR (50),	");
        stringBuffer.append("	dad_name VARCHAR (20),	");
        stringBuffer.append("	dad_mobile VARCHAR (20),	");
        stringBuffer.append("	dad_email VARCHAR (50),	");
        stringBuffer.append("	address VARCHAR (100),	");
        stringBuffer.append("	remarks VARCHAR (200),	");
        stringBuffer.append("	upload CHAR (1)	");
        stringBuffer.append("	DEFAULT (0),	");
        stringBuffer.append("	create_time BIGINT (20),	");
        stringBuffer.append("	update_time BIGINT (20)	");
        stringBuffer.append("	);	");
        ret = update(stringBuffer.toString());
        Logger.debug("CREATE TABLE edu_customer_info : " + ret);

        // edu_swipe_card_records
        stringBuffer = new StringBuffer();
        stringBuffer.append("	CREATE TABLE IF NOT EXISTS edu_swipe_card_records (	");
        stringBuffer.append("	id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuffer.append("	card_no VARCHAR (50),	");
        stringBuffer.append("	child_name VARCHAR (20),	");
        stringBuffer.append("	upload CHAR (1)	");
        stringBuffer.append("	DEFAULT (0),	");
        stringBuffer.append("	create_time BIGINT (20)	");
        stringBuffer.append("	);	");
        ret = update(stringBuffer.toString());
        Logger.debug("CREATE TABLE edu_swipe_card_records : " + ret);

        // edu_teacher_customer
        stringBuffer = new StringBuffer();
        stringBuffer.append("	CREATE TABLE IF NOT EXISTS edu_teacher_customer (	");
        stringBuffer.append("	id INTEGER PRIMARY KEY AUTOINCREMENT,");
        stringBuffer.append("	staff_no VARCHAR (20),	");
        stringBuffer.append("	staff_name VARCHAR (20),	");
        stringBuffer.append("	customer_id INTEGER,	");
        stringBuffer.append("	child_name VARCHAR (20),	");
        stringBuffer.append("	upload CHAR (1)	");
        stringBuffer.append("	DEFAULT (0),	");
        stringBuffer.append("	create_time BIGINT (20),	");
        stringBuffer.append("	update_time BIGINT (20)	");
        stringBuffer.append("	);	");
        ret = update(stringBuffer.toString());
        Logger.debug("CREATE TABLE edu_teacher_customer : " + ret);

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
     * @return rowEffects
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
     * INSERT
     *
     * @param sql
     * @return new row id
     * @throws SQLException
     */
    public static Long insert(String sql) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            runner.update(conn, sql);
            //获取新增记录的自增主键
            java.math.BigInteger id = (BigInteger) runner.query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler(1));
            return id.longValue();
        }
        return -1L;
    }

    /**
     * INSERT
     *
     * @param sql
     * @param param
     * @return new row id
     * @throws SQLException
     */
    public static Long insert(String sql, Object param) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            runner.update(conn, sql, param);
            //获取新增记录的自增主键
            java.math.BigInteger id = (BigInteger) runner.query(conn, "SELECT LAST_INSERT_ID()", new ScalarHandler(1));
            return id.longValue();
        }
        return -1L;
    }

    /**
     * INSERT
     *
     * @param sql
     * @param params
     * @return new row id
     * @throws SQLException
     */
    public static Integer insert(String sql, Object... params) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            runner.update(conn, sql, params);
            //获取新增记录的自增主键
            Integer id = (Integer) runner.query(conn, "select last_insert_rowid() newid;", new ScalarHandler(1));
            return id;
        }
        return -1;
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
     * UPDATE
     *
     * @param sql
     * @return rowEffects
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
     * UPDATE
     *
     * @param sql
     * @param param
     * @return rowEffects
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
     * UPDATE
     *
     * @param sql
     * @param params
     * @return rowEffects
     * @throws SQLException
     */
    public static int update(String sql, Object... params) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.update(conn, sql, params);
        }
        return -1;
    }

    /**
     * DELETE
     *
     * @param sql
     * @return rowEffects
     * @throws SQLException
     */
    public static int delete(String sql) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.update(conn, sql);
        }
        return -1;
    }

    /**
     * DELETE
     *
     * @param sql
     * @param param
     * @return rowEffects
     * @throws SQLException
     */
    public static int delete(String sql, Object param) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.update(conn, sql, param);
        }
        return -1;
    }

    /**
     * DELETE
     *
     * @param sql
     * @param params
     * @return rowEffects
     * @throws SQLException
     */
    public static int delete(String sql, Object... params) throws SQLException {
        if (conn != null) {
            QueryRunner runner = new QueryRunner();
            return runner.update(conn, sql, params);
        }
        return -1;
    }

    /**
     * 设置提交方式
     *
     * @param autoCommit
     * @throws SQLException
     */
    public static void setAutoCommit(boolean autoCommit) throws SQLException {
        if (conn != null) {
            conn.setAutoCommit(autoCommit);
        }
    }

    /**
     * 提交事务
     */
    public static void commit() throws SQLException {
        if (conn != null) {
            conn.commit();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollback() throws SQLException {
        if (conn != null) {
            conn.rollback();
        }
    }

    /**
     * 关闭连接
     */
    public static void close() throws SQLException {
        conn.close();
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
