package kr.co.board;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class MySQLConnectionTest {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mariadb://192.168.255.115:3306/test";
	private static final String USER = "user";
	private static final String PW = "user";

	@Test
	public void testConnection() throws Exception {
		Class.forName(DRIVER);
		try (Connection conn = DriverManager.getConnection(URL, USER, PW)) {
			System.out.println(conn+" ========= db연결성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}