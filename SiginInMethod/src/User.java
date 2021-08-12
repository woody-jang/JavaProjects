public class User {
	private String name; // 이름
	private String birth; // 생년월일
	private String uid; // id
	private char[] upwd; // pw

	public User(String name, String birth, String uid, char[] upwd) {
		this.name = name;
		this.birth = birth;
		this.uid = uid;
		this.upwd = upwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public char[] getUpwd() {
		return upwd;
	}

	public void setUpwd(char[] upwd) {
		this.upwd = upwd;
	}

//	public void setPassword() { // 비밀번호 암호화 작업
//		encryption = new int[upwd.length];
//		encryptionCode = new boolean[upwd.length];
//		for (int i = 0; i < upwd.length; i++) {
//			int n = (int) (Math.random() * 100) % 63 + 1;
//			if (upwd[i] > 63) {
//				upwd[i] = (char) (upwd[i] - n);
//				encryptionCode[i] = true;
//			} else {
//				upwd[i] = (char) (upwd[i] + n);
//				encryptionCode[i] = false;
//			}
//			encryption[i] = n;
//		}
//	}
//
//	public void getPassword() { // 비밀번호 복호화 작업
//		for (int i = 0; i < encryption.length; i++) {
//			if (encryptionCode[i])
//				upwd[i] = (char) (upwd[i] + encryption[i]);
//			else
//				upwd[i] = (char) (upwd[i] - encryption[i]);
//		}
//	}

	@Override
	public String toString() {
		return "User [name=" + name + ", birth=" + birth + ", uid=" + uid + "]";
	}

}
