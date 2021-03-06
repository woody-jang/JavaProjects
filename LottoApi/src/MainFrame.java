import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MainFrame extends JFrame {
	int[] lottoNums = new int[45]; // 각 숫자들이 나온 횟수를 저장할 배열
	int[] bonusNums = new int[45];
	List<List<Integer>> lottoNumList = new ArrayList<>(); // 회차별로 6개 숫자를 저장할 리스트
	List<Integer> bonusNumList = new ArrayList<>();
	JLabel waitingLbl = new JLabel();
	List<List<JLabel>> numLblList = new ArrayList<>(); // 등장 횟수를 표시할 레이블
	List<List<JLabel>> bnusNumLblList = new ArrayList<>(); // 보너스 번호 등장 횟수를 표시할 레이블

	public MainFrame(int startNo) throws IOException {
		setLocation(650, 150);
		// 메인 패널
		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

		// 안내 문구를 위한 패널
		JPanel titlePnl = new JPanel();
		titlePnl.setOpaque(true);
		titlePnl.setBackground(Color.white);

		waitingLbl.setText("불러오기 버튼을 누르시고 잠시만 기다려주세요 :-)");
		waitingLbl.setFont(new Font(waitingLbl.getFont().getName(), Font.PLAIN, 22));
		titlePnl.add(waitingLbl);

		// 숫자별 등장횟수를 표시할 패널 받아오기
		JPanel numsPnl = getNumsPnl(numLblList, "**숫자별 등장횟수**");

		// 보너스 번호 등장횟수 표시할 패널 받아오기
		JPanel bnusNumsPnl = getNumsPnl(bnusNumLblList, "**보너스 번호 등장횟수**");

		// 버튼용 패널
		JPanel btnPnl = new JPanel();
		btnPnl.setOpaque(true);
		btnPnl.setBackground(Color.white);

		JButton returnBtn = new JButton("처음으로");
		returnBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new Main();
				} catch (IOException e1) {
				}
				dispose();
			}
		});
		
		JButton recentNumBtn = new JButton("최근 당첨번호 보기");
		recentNumBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RecentNumDialog(lottoNumList, bonusNumList);
			}
		});

		JButton sortBtn = new JButton("많이 나온 순서대로 보기");
		sortBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 숫자별 등장횟수 배열을 넘겨주면서 새로은 프레임 생성
				new SortingFrame(lottoNums);
				// 현재 창 끄기
				dispose();
			}
		});

		JButton loadBtn = new JButton("불러오기");
		loadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// 선택한 회차부터 최근 회차까지의 당첨번호 받아오기
					getLottoNo(startNo);
				} catch (IOException e1) {
				}
				// 등장 횟수용 레이블 수정하기
				setNumsLbl();
				waitingLbl.setText("숫자별 당첨 번호 출현 횟수");
				btnPnl.remove(loadBtn);
				btnPnl.add(returnBtn);
				btnPnl.add(sortBtn);
				btnPnl.add(recentNumBtn);
				pack();
			}
		});

		btnPnl.add(loadBtn);

		mainPnl.add(titlePnl);
		mainPnl.add(numsPnl);
		mainPnl.add(bnusNumsPnl);
		mainPnl.add(btnPnl);

		add(mainPnl);

		showGUI();
	}

	// 등장횟수 레이블에 표시된 내용을 실제 등장횟수로 수정하기
	private void setNumsLbl() {
		for (int i = 0; i < numLblList.size(); i++) {
			for (int j = 0; j < numLblList.get(i).size(); j++) {
				int temp = (i * 7) + j;
				numLblList.get(i).get(j).setText(lottoNums[temp] + "");
				bnusNumLblList.get(i).get(j).setText(bonusNums[temp] + "");
			}
		}
	}

	// 등장횟수 레이블 추가 - 처음엔 0으로 표시
	// 보너스 숫자 레이블과 같이 사용
	private JPanel getNumsPnl(List<List<JLabel>> lblList, String titleString) {
		JPanel totalTemp = new JPanel();
		totalTemp.setOpaque(true);
		totalTemp.setBackground(Color.white);
		totalTemp.setLayout(new BoxLayout(totalTemp, BoxLayout.Y_AXIS));

		if (titleString.contains("보너스"))
			totalTemp.add(new JLabel(" "));

		JPanel titlePnl = new JPanel();
		titlePnl.setOpaque(true);
		titlePnl.setBackground(Color.white);

		JLabel titleLbl = new JLabel(titleString);
		titleLbl.setFont(new Font(titleLbl.getFont().getName(), Font.PLAIN, 20));
		titleLbl.setForeground(Color.red);
		titlePnl.add(titleLbl);

		totalTemp.add(titlePnl);

		for (int i = 0; i < 7; i++) {
			JPanel rawTemp = new JPanel();
			rawTemp.setOpaque(true);
			rawTemp.setBackground(Color.white);
			List<JLabel> tempLblList = new ArrayList<>();
			rawTemp.setLayout(new FlowLayout(FlowLayout.LEFT));
			for (int j = 0; j < 7; j++) {
				int temp = (i * 7) + j + 1;
				if (i == 6 && j > 2)
					break;
				JLabel numLbl = new JLabel(temp + " :");
				numLbl.setFont(new Font(numLbl.getFont().getName(), Font.PLAIN, 17));

				JLabel numCntLbl = new JLabel(0 + "");
				numCntLbl.setFont(new Font(numCntLbl.getFont().getName(), Font.PLAIN, 17));

				JLabel blankLbl = null;
				if (temp > 9)
					blankLbl = new JLabel(",       ");
				else
					blankLbl = new JLabel(",          ");

				rawTemp.add(numLbl);
				rawTemp.add(numCntLbl);
				if (temp != 45 && temp % 7 != 0)
					rawTemp.add(blankLbl);
				tempLblList.add(numCntLbl);
			}
			totalTemp.add(rawTemp);
			lblList.add(tempLblList);
		}
		return totalTemp;
	}

	// 역대 당첨번호 저장
	public void getLottoNo(int startNo) throws IOException {
		for (int i = startNo;; i++) {
			// 로또 홈페이지에서 정보 긁어오기
			String lottoURL = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" + i;
			Connection lottoConn = Jsoup.connect(lottoURL);
			Document lottoHTML = lottoConn.get();

			// 종료조건 설정하기
			String lottoContents = lottoHTML.toString().trim().split("\n")[3];
			if (lottoContents.equals("  {\"returnValue\":\"fail\"}"))
				break;

			// 당첨번호 6개 긁어오기
			List<Integer> eachLottoNumList = new ArrayList<>();
			for (int j = 1; j < 7; j++) {
				String[] lottoNumList = lottoHTML.toString().split("drwtNo" + j + "\":");
				String temp = "";

				// 만약 당첨숫자가 2자리라면 2개 뽑아오기
				if (Pattern.matches("^[0-9]*$", lottoNumList[1].substring(1, 2)))
					temp = lottoNumList[1].substring(0, 2);
				// 당첨숫자가 한자리라면 한개 뽑아오기
				else
					temp = lottoNumList[1].substring(0, 1);
				lottoNums[Integer.parseInt(temp) - 1]++;
				eachLottoNumList.add(Integer.parseInt(temp));
			}
			lottoNumList.add(eachLottoNumList);

			// 보너스 숫자 긁어오기
			String[] lottoNumList = lottoHTML.toString().split("bnusNo" + "\":");
			String temp = "";

			// 만약 보너스숫자가 2자리라면 2개 뽑아오기
			if (Pattern.matches("^[0-9]*$", lottoNumList[1].substring(1, 2)))
				temp = lottoNumList[1].substring(0, 2);
			// 보너스숫자가 한자리라면 한개 뽑아오기
			else
				temp = lottoNumList[1].substring(0, 1);
			bonusNums[Integer.parseInt(temp) - 1]++;
			bonusNumList.add(Integer.parseInt(temp));
		}
	}

	public void showGUI() {
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
