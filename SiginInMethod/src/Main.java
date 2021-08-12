/*
 * 사용자 가입과 로그인 기능이 있는 GUI 프로그램 작성.
 * 
 * 
 * GUI 및 Java 소스코드 제출
 * 데이터베이스 정의 언어 정리해서 제출 (예) create databse ..., create table ...
 * 데이터베이스 조작 언어 정리해서 제출 (예) insert ..., select ..., update ..., delete ...
 * 
 * 자기 이름으로 .zip 압축해서 공유폴더에 넣어주세요~~~
 */
public class Main {
	public static UserDAO dao = new UserDAO();

	public static void main(String[] args) {
		new Login();
	}
}