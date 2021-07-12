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
	private Integer[] combList;
	int startNo = 1;

	public Main() throws IOException {
		setLocation(700, 300);
		getLastNo();
		JPanel totalPnl = new JPanel();
		totalPnl.setLayout(new BoxLayout(totalPnl, BoxLayout.Y_AXIS));

		JPanel logoPnl = new JPanel();
		logoPnl.setOpaque(true);
		logoPnl.setBackground(Color.white);

		URL lottoImgURL = Main.class.getClassLoader().getResource("logo.png");
		ImageIcon lottoImgIcon = new ImageIcon(lottoImgURL);
		JLabel lottoImg = new JLabel(lottoImgIcon);

		logoPnl.add(lottoImg);

		JPanel lblPnl = new JPanel();
		lblPnl.setOpaque(true);
		lblPnl.setBackground(Color.white);
		
		JLabel informLbl = new JLabel("조회를 시작 할 회차를 선택하세요");
		informLbl.setFont(new Font(informLbl.getFont().getName(), Font.PLAIN, 17));
		
		lblPnl.add(informLbl);
		
		JPanel combPnl = new JPanel();
		combPnl.setOpaque(true);
		combPnl.setBackground(Color.white);

		JComboBox<Integer> noCombBox =  new JComboBox<>(combList);
		noCombBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<Integer> tmp = (JComboBox<Integer>) e.getSource();
				startNo = tmp.getSelectedIndex() + 1;
			}
		});
		JButton okBtn = new JButton("조회하기");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new MainFrame(startNo);
				} catch (IOException e1) {
				}
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
