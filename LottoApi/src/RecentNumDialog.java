import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RecentNumDialog extends JDialog {
	public RecentNumDialog(List<List<Integer>> lottoNumList, List<Integer> bonusNumList) {
		setModal(true);
		setLocation(700, 300);

		JPanel mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

		JPanel titlePnl = new JPanel();
		titlePnl.setOpaque(true);
		titlePnl.setBackground(Color.white);

		JLabel titleLbl = new JLabel("최근 당첨 번호 10개");
		titleLbl.setFont(new Font(titleLbl.getFont().getName(), Font.PLAIN, 23));

		titlePnl.add(titleLbl);

		JPanel numbersPnl = getNumbersPnl(lottoNumList, bonusNumList, Main.combList);

		mainPnl.add(titlePnl);
		mainPnl.add(numbersPnl);

		add(mainPnl);

		pack();
		setVisible(true);
	}

	private JPanel getNumbersPnl(List<List<Integer>> lottoNumList, List<Integer> bonusNumList, Integer[] combList) {
		JPanel tmpMainPnl = new JPanel();
		tmpMainPnl.setOpaque(true);
		tmpMainPnl.setBackground(Color.white);
		tmpMainPnl.setLayout(new BoxLayout(tmpMainPnl, BoxLayout.Y_AXIS));

		int idx = combList.length - 1;
		int cnt = 0;
		for (int i = lottoNumList.size() - 1; i >= 0; i--) {
			cnt++;
			List<Integer> tempList = lottoNumList.get(i);
			JPanel tmpNumsPnl = new JPanel();
			tmpNumsPnl.setOpaque(true);
			tmpNumsPnl.setBackground(Color.white);
			tmpNumsPnl.setLayout(new FlowLayout(FlowLayout.LEFT));

			JLabel noLbl = new JLabel(String.valueOf(combList[idx--]) + "회차");
			JLabel blankLbl = new JLabel("   :   ");
			String numsStr = getNumsStr(tempList);
			JLabel numsLbl = new JLabel(numsStr + " + " + bonusNumList.get(i));
			noLbl.setFont(new Font(noLbl.getFont().getName(), Font.PLAIN, 20));
			blankLbl.setFont(new Font(blankLbl.getFont().getName(), Font.PLAIN, 20));
			numsLbl.setFont(new Font(numsLbl.getFont().getName(), Font.PLAIN, 20));

			tmpNumsPnl.add(noLbl);
			tmpNumsPnl.add(blankLbl);
			tmpNumsPnl.add(numsLbl);

			tmpMainPnl.add(tmpNumsPnl);

			if (cnt == 10)
				break;
		}
		return tmpMainPnl;
	}

	private String getNumsStr(List<Integer> tempList) {
		StringBuffer sb = new StringBuffer();
		for (int i : tempList) {
			if (i > 9)
				sb.append(i + "  ");
			else
				sb.append(i + "    ");
		}
		return sb.toString();
	}
}
