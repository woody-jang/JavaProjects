import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO { // Data Access Object
	public void addUser(User user) {
		String query = "INSERT INTO users (id, password, name, birth) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			stmt.setString(1, user.getUid());
			stmt.setString(2, new String(user.getUpwd()));
			stmt.setString(3, user.getName());
			stmt.setString(4, user.getBirth());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkUidIsAlreadyExist(String uid) {
		String query = "SELECT * FROM users WHERE id = ?";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {
			
			stmt.setString(1, uid);
			
			try (ResultSet rs = stmt.executeQuery();){
				if (rs.next())
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public User checkLoginIsAvailable(User user) {
		String query = "SELECT * FROM users WHERE id = ?";
		try (Connection conn = ConnectionProvider.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);) {

			stmt.setString(1, user.getUid());

			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					if (rs.getString("password").equals(new String(user.getUpwd()))) {
						user.setName(rs.getString("name"));
						user.setBirth(rs.getString("birth"));
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
}
