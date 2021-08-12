import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {
	private static Properties props = new Properties();

	static {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("./dbConn.properties");
			props.load(fis);

			Class.forName(props.getProperty("DRIVER_NAME"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(props.getProperty("URL"), props.getProperty("ID"),
				props.getProperty("PASSWORD"));
	}
}