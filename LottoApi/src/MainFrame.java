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
	int[] lottoNums = new int[45];
	int[] bonusNums = new int[45];
	List<List<Integer>> lottoNumList = new ArrayList<>();
	List<Integer> bonusNumList = new ArrayList<>();
	JLabel waitingLbl = new JLabel();
	JPanel numsPnl = new JPanel();
	List<List<JLabel>> lblList = new ArrayList<>();

	public MainFrame() throws IOException {
		
		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));
		
		JPanel titlePnl = new JPanel();
		titlePnl.setOpaque(true);
		titlePnl.setBackground(Color.white);
		
		waitingLbl.setText("잠시만 기다려주세요...");
		waitingLbl.setFont(new Font(waitingLbl.getFont().getName(), Font.PLAIN, 15));
		titlePnl.add(waitingLbl);
		
		numsPnl.setOpaque(true);
		numsPnl.setBackground(Color.white);
		
		numsPnl = getNumsPnl();
		
		JPanel btnPnl = new JPanel();
		btnPnl.setOpaque(true);
		btnPnl.setBackground(Color.white);
		
		JButton loadBtn = new JButton("불러오기");
		loadBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					getLottoNo();
				} catch (IOException e1) {
				}
				setNumsLbl();
				waitingLbl.setText("숫자별 당첨 번호 출현 횟수");
				repaint();
				revalidate();
				pack();
			}

		});
		
		btnPnl.add(loadBtn);
		
		mainPnl.add(titlePnl);
		mainPnl.add(numsPnl);
		mainPnl.add(btnPnl);
		
		add(mainPnl);
		
		showGUI();
	}

	private void setNumsLbl() {
		for (int i = 0; i < lblList.size(); i++) {
			for (int j = 0; j < lblList.get(i).size(); j++) {
				int temp = (i * 7) + j;
				lblList.get(i).get(j).setText(lottoNums[temp] + "");
			}
		}
	}
	
	// 숫자 레이블 추가
	private JPanel getNumsPnl() {
		JPanel totalTemp = new JPanel();
		totalTemp.setOpaque(true);
		totalTemp.setBackground(Color.white);
		totalTemp.setLayout(new BoxLayout(totalTemp, BoxLayout.Y_AXIS));
		
		for (int i = 0; i < 7; i++) {
			JPanel rawTemp = new JPanel();
			rawTemp.setOpaque(true);
			rawTemp.setBackground(Color.white);
			List<JLabel> tempLblList = new ArrayList<>();
			if (i == 6)
				rawTemp.setLayout(new FlowLayout(FlowLayout.LEFT));
			for (int j = 0; j < 7; j++) {
				int temp = (i * 7) + j + 1;
				if (i == 6 && j > 2)
					break;
				JLabel numLbl = new JLabel(temp + " :");
				numLbl.setFont(new Font(numLbl.getFont().getName(), Font.PLAIN, 17));
				
				JLabel numCntLbl = new JLabel(0 + "");
				numCntLbl.setFont(new Font(numCntLbl.getFont().getName(), Font.PLAIN, 17));
				
				JLabel blankLbl = new JLabel(",       ");
				
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
	public void getLottoNo() throws IOException{
		for (int i = 1; ; i++) {
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
