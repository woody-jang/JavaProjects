import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main extends JFrame {
	// 콤보박스에 담을 배열
	private Integer[] combList;
	// 콤보박스 선택을 담을 변수
	int startNo = 1;

	public Main() throws IOException {
		setLocation(700, 300);
		// 콤보박스에 추가하기 위해 제일 최근의 회차를 찾음
		getLastNo();
		// 전체를 담을 패널
		JPanel totalPnl = new JPanel();
		totalPnl.setLayout(new BoxLayout(totalPnl, BoxLayout.Y_AXIS));

		// 로또 로고 담을 패널
		JPanel logoPnl = new JPanel();
		logoPnl.setOpaque(true);
		logoPnl.setBackground(Color.white);

		// 로또 로고용 레이블
		URL lottoImgURL = Main.class.getClassLoader().getResource("logo.png");
		ImageIcon lottoImgIcon = new ImageIcon(lottoImgURL);
		JLabel lottoImg = new JLabel(lottoImgIcon);

		logoPnl.add(lottoImg);

		// 안내 문구를 위한 패널
		JPanel lblPnl = new JPanel();
		lblPnl.setOpaque(true);
		lblPnl.setBackground(Color.white);

		JLabel informLbl = new JLabel("조회를 시작 할 회차를 선택하세요");
		informLbl.setFont(new Font(informLbl.getFont().getName(), Font.PLAIN, 17));

		lblPnl.add(informLbl);

		// 콤보박스와 버튼을 위한 패널
		JPanel combPnl = new JPanel();
		combPnl.setOpaque(true);
		combPnl.setBackground(Color.white);

		JComboBox<Integer> noCombBox = new JComboBox<>(combList);
		noCombBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 콤보박스에서 선택한 값을 저장
				JComboBox<Integer> tmp = (JComboBox<Integer>) e.getSource();
				startNo = tmp.getSelectedIndex() + 1;
			}
		});
		JButton okBtn = new JButton("조회하기");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// 콤보박스에서 선택한 값이 저장된 변수를 넘기면서 다음 프레임 생성
					new MainFrame(startNo);
				} catch (IOException e1) {
				}
				// 현재 창은 꺼짐
				Main.this.dispose();
			}
		});

		combPnl.add(noCombBox);
		combPnl.add(okBtn);

		totalPnl.add(logoPnl);
		totalPnl.add(lblPnl);
		totalPnl.add(combPnl);

		add(totalPnl);

		showGUI();

	}

	// 제일 최근 회차를 받아 콤보박스에 추가하기 위해 배열 크기 알아내기
	private void getLastNo() throws IOException {
		int i = 970;
		for (;; i++) {
			// 로또 홈페이지에서 정보 긁어오기
			String lottoURL = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" + i;
			Connection lottoConn = Jsoup.connect(lottoURL);
			Document lottoHTML = lottoConn.get();

			// 종료조건 설정하기
			String lottoContents = lottoHTML.toString().trim().split("\n")[3];
			if (lottoContents.equals("  {\"returnValue\":\"fail\"}"))
				break;
		}
		this.combList = new Integer[i - 1];
		getCombList();
	}

	// 배열 크기만큼 1부터 배열에 차곡차곡 담기
	private void getCombList() {
		for (int i = 0; i < combList.length; i++) {
			combList[i] = i + 1;
		}
	}

	private void showGUI() {
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		new Main();
//		new MainFrame();
	}
}
