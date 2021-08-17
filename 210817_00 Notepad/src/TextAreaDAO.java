import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TextAreaDAO {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/my_db";
	private String id = "root";
	private String password = "root";

	// 저장
	public int save(LocalTime time, String text, boolean isAuto) {
		String query = "INSERT INTO textpad (savetime, savedata, isAuto) VALUES (?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(url, id, password);
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setTime(1, Time.valueOf(time));
			stmt.setString(2, text);
			stmt.setBoolean(3, isAuto);

			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 불러오기
	public List<TextAreaData> load() {
		String query = "SELECT id, savetime, isAuto FROM textpad";
		List<TextAreaData> list = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url, id, password);
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				LocalTime time = rs.getTime("savetime").toLocalTime();
				boolean isAuto = rs.getBoolean("isAuto");

				TextAreaData data = new TextAreaData(id, time, isAuto);
				list.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 텍스트값 불러오기
	public String load(int id) {
		String query = "SELECT savedata FROM textpad WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(url, this.id, password);
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("savedata");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
