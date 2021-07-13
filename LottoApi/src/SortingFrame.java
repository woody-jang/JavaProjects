import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class SortingFrame extends JFrame {
	int[] lottoCntList;
	int[] lottoNoList = new int[45];
	
	public SortingFrame(int[] lottoNums) {
		setLocation(650, 300);
		lottoCntList = lottoNums.clone();
		for (int i = 1; i < 46; i++) {
			lottoNoList[i - 1] = i;
		}
		sortArrays();
		
		JPanel mainPnl = getNumsPnl();
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
		
		btnPnl.add(returnBtn);
		
		add(mainPnl, "North");
		add(btnPnl, "South");
		
		showGUI();
		
	}

	private void sortArrays() {
		for (int i = 0; i < lottoCntList.length - 1; i++) {
			for (int j = i + 1; j < lottoCntList.length; j++) {
				if (lottoCntList[i] < lottoCntList[j]) {
					int temp = lottoCntList[i];
					lottoCntList[i] = lottoCntList[j];
					lottoCntList[j] = temp;
					
					temp = lottoNoList[i];
					lottoNoList[i] = lottoNoList[j];
					lottoNoList[j] = temp;
				}
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
			if (i == 6)
				rawTemp.setLayout(new FlowLayout(FlowLayout.LEFT));
			for (int j = 0; j < 7; j++) {
				if (i == 6 && j > 2)
					break;
				int temp = (i * 7) + j;
				int tempCnt = lottoCntList[temp];
				int tempNo = lottoNoList[temp];
				JLabel numLbl = new JLabel(tempNo + " :");
				numLbl.setFont(new Font(numLbl.getFont().getName(), Font.PLAIN, 17));

				JLabel numCntLbl = new JLabel(tempCnt + "회");
				numCntLbl.setFont(new Font(numCntLbl.getFont().getName(), Font.PLAIN, 17));

				JLabel blankLbl = new JLabel(",       ");

				rawTemp.add(numLbl);
				rawTemp.add(numCntLbl);
				if (i == 6 && j != 2)
					rawTemp.add(blankLbl);
				if (j != 6)
					rawTemp.add(blankLbl);
			}
			totalTemp.add(rawTemp);
		}
		return totalTemp;
	}

	public void showGUI() {
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

}
