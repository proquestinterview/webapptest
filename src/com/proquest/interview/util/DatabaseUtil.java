package com.proquest.interview.util;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * This class is just a utility class, you should not have to change anything here
 * @author rconklin
 */
public class DatabaseUtil {

	static Logger logger = Logger.getLogger(DatabaseUtil.class);

	public static void initDB() {
		Connection cn = null;
		Statement stmt = null;
		try {
			cn = getConnection();
			stmt = cn.createStatement();
			// NOTE: This table could use an indexed primary key column (PERSON_ID)
			// stmt.execute("DROP TABLE PHONEBOOK");
			stmt.execute("CREATE TABLE PHONEBOOK (NAME varchar(255), PHONENUMBER varchar(255), ADDRESS varchar(255))");
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('Chris Johnson','(321) 231-7876', '452 Freeman Drive, Algonac, MI')");
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('Dave Williams','(231) 502-1236', '285 Huron St, Port Austin, MI')");
			cn.commit();
			cn.close();
		} catch (Exception ex) {
			logger.warn("Failed to init DB.");
			ErrorLogger.filterStackTrace(ex);
		} finally {
			closeAll(stmt, cn);
		}
		
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		return DriverManager.getConnection("jdbc:hsqldb:mem", "sa", "");
	}

	/**
	 * Closes the result set, statement & connection.
	 * 
	 * @param rs
	 * @param stmt
	 * @param conn
	 * @param query
	 */
	public static void closeAll (ResultSet rs, Statement stmt, Connection conn, String query) {

		try {
			if (rs != null) {
				rs.close();
			}

			if (stmt != null ) {
				stmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			logger.warn("Unable to close rs/stmt/conn. Query: " + query, e);
		}
	}

	/**
	 * Closes the statement & connection. {@link DatabaseUtil#closeAll(Statement, Connection, String)} is preferred.
	 *
	 * @param stmt
	 * @param conn
	 */
	public static void closeAll (Statement stmt, Connection conn) {
		try {
			if (stmt != null) {
				stmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			logger.warn("Unable to close stmt/conn.", e);
		}
	}

	/**
	 * Closes the statement & connection.
	 *
	 * @param stmt
	 * @param conn
	 * @param query
	 */
	public static void closeAll (Statement stmt, Connection conn, String query) {
		try {
			if (stmt != null) {
				stmt.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			logger.warn("Unable to close stmt/conn. Query: " + query, e);
		}
	}
	

	/**
	 * Helper method to convert strings to a sql-ready state. Probably don't want to pass pre-escaped SQL to this. Could
	 * add functionality to handle converting nulls to empty strings or vice versa, etc... if desired
	 * 
	 * @param str The String to be converted.  Should not already be escaped.
	 *
	 * @return The string, escaped for SQL and wrapped in single quotes when necessary (which is always in this case
	 * unless the string is null). Empty strings are wrapped in single quotes. Don't ever wrap the string returned by this method in single quotes.
	 */
	public static String getSqlValue(String str) {

		// Could be optimized
		if (str != null) {
			str = str.replace("'", "''"); // escape single quotes (could use StringEscapeUtils#escapeSql instead)
			StringBuilder sb = new StringBuilder(str.length() + 2);
			sb.append("'").append(str).append("'");
			return sb.toString();
		}

		return str;

	}
}
