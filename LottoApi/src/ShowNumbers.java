import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowNumbers extends JDialog {
	int[] lottoNoList;

	public ShowNumbers(int[] lottoNoList) {
		setModal(true);
		this.lottoNoList = lottoNoList;
		// 메인 패널 생성
		JPanel mainPnl = new JPanel();
		mainPnl.setOpaque(true);
		mainPnl.setBackground(Color.white);
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

		// 각각의 레이블 생성
		JLabel titleLbl1 = new JLabel("**검색한 기간에 많이 나온 숫자**");
		titleLbl1.setFont(new Font(titleLbl1.getFont().getName(), Font.PLAIN, 20));

		String maxCnt = getMaxCntNumbers();
		JLabel numbersLbl1 = new JLabel(maxCnt);
		numbersLbl1.setFont(new Font(numbersLbl1.getFont().getName(), Font.PLAIN, 20));

		JLabel titleLbl2 = new JLabel("**검색한 기간에 적게 나온 숫자**");
		titleLbl2.setFont(new Font(titleLbl2.getFont().getName(), Font.PLAIN, 20));
		titleLbl2.setFont(new Font(titleLbl2.getFont().getName(), Font.PLAIN, 20));

		String minCnt = getMinCntNumbers();
		JLabel numbersLbl2 = new JLabel(minCnt);
		numbersLbl2.setFont(new Font(numbersLbl2.getFont().getName(), Font.PLAIN, 20));

		JLabel blankLbl = new JLabel(" ");

		mainPnl.add(titleLbl1);
		mainPnl.add(numbersLbl1);
		mainPnl.add(blankLbl);
		mainPnl.add(titleLbl2);
		mainPnl.add(numbersLbl2);

		add(mainPnl);

		setLocation(700, 350);
		pack();
		setVisible(true);
	}

	// 적게 등장한 숫자 6개 뽑기
	private String getMinCntNumbers() {
		StringBuffer sb = new StringBuffer();
		int[] tempNum = new int[6];
		int idx = 0;
		for (int i = lottoNoList.length - 1; i > lottoNoList.length - 7; i--) {
			tempNum[idx++] = lottoNoList[i];
		}
		Arrays.sort(tempNum);
		for (int i : tempNum) {
			sb.append(i + "  ");
		}
		return sb.toString();
	}

	// 많이 등장한 숫자 6개 뽑기
	private String getMaxCntNumbers() {
		StringBuffer sb = new StringBuffer();
		int[] tempNum = new int[6];
		int idx = 0;
		for (int i = 0; i < 6; i++) {
			tempNum[idx++] = lottoNoList[i];
		}
		Arrays.sort(tempNum);
		for (int i : tempNum) {
			sb.append(i + "  ");
		}
		return sb.toString();
	}
}
