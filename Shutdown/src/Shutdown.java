import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.*;

public class Shutdown {
	public static void main(String[] args) {
		Cmd cmd = new Cmd();
		new GUI(cmd);
	}

	public static void run(Cmd cmd, String commandStr) {
		String command = cmd.inputCommand(commandStr);
		cmd.execCommand(command);
	}

	public static class Cmd {

		private StringBuffer buffer;
		private Process process;
		private BufferedReader bufferedReader;
		private StringBuffer readBuffer;

		public String inputCommand(String cmd) {

			buffer = new StringBuffer();

			buffer.append("cmd.exe ");
			buffer.append("/c ");
			buffer.append(cmd);

			return buffer.toString();
		}

		public String execCommand(String cmd) {
			try {
				process = Runtime.getRuntime().exec(cmd);
				bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line = null;
				readBuffer = new StringBuffer();

				while ((line = bufferedReader.readLine()) != null) {
					readBuffer.append(line);
					readBuffer.append("\n");
				}

				return readBuffer.toString();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}

			return null;
		}
	}

	public static class GUI extends JFrame implements ActionListener{
		private Cmd cmd;
		private Font font20 = new Font("굴림", Font.PLAIN, 20);
		private int selectedTime = 0;
		private int selectedMinute = 0;
		private JButton okBtn;
		private JButton cancelBtn;
		private JComboBox<Integer> timeComb;
		private JComboBox<Integer> minuteComb;

		public GUI(Cmd cmd) {
			this.cmd = cmd;
			setTitle("컴퓨터 종료 예약 By 장깽");
			JPanel totalPnl = new JPanel();
			totalPnl.setLayout(new BoxLayout(totalPnl, BoxLayout.Y_AXIS));

			JPanel titlePnl = new JPanel();
			JLabel titleLbl = new JLabel("컴퓨터 종료 예약 프로그램");
			titleLbl.setFont(new Font("굴림", Font.PLAIN, 30));
			titleLbl.setHorizontalAlignment(JLabel.CENTER);
			titleLbl.setOpaque(true);
			titlePnl.add(titleLbl);
			totalPnl.add(titlePnl);

			JPanel tempPnl1 = new JPanel();
			JPanel timePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
			timePnl.setPreferredSize(new Dimension(150, 30));
			timePnl.setOpaque(true);
			JLabel timeLbl = new JLabel("시간: ");
			timeLbl.setFont(font20);
			timeLbl.setPreferredSize(new Dimension(60, 20));
			timeLbl.setOpaque(true);
			timePnl.add(timeLbl);

			Integer[] timeList = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
					24 };
			timeComb = new JComboBox<Integer>(timeList);
			timeComb.setFont(font20);
			timeComb.setPreferredSize(new Dimension(50, 25));
			timeComb.setOpaque(true);
			timeComb.addActionListener(this);
			timePnl.add(timeComb);

			tempPnl1.add(timePnl);
			totalPnl.add(tempPnl1);

			JPanel tempPnl2 = new JPanel();
			JPanel minutePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
			minutePnl.setPreferredSize(new Dimension(150, 30));
			minutePnl.setOpaque(true);
			JLabel minuteLbl = new JLabel("분: ");
			minuteLbl.setFont(font20);
			minuteLbl.setPreferredSize(new Dimension(60, 20));
			minuteLbl.setOpaque(true);
			minutePnl.add(minuteLbl);

			Integer[] minuteList = new Integer[60];
			for (int i = 0; i < minuteList.length; i++) {
				minuteList[i] = i;
			}
			minuteComb = new JComboBox<Integer>(minuteList);
			minuteComb.setFont(font20);
			minuteComb.setPreferredSize(new Dimension(50, 25));
			minuteComb.setOpaque(true);
			minuteComb.addActionListener(this);
			minutePnl.add(minuteComb);

			tempPnl2.add(minutePnl);
			totalPnl.add(tempPnl2);

			JPanel btnPnl = new JPanel();
			okBtn = new JButton("예약하기");
			okBtn.setFont(font20);
			okBtn.setMargin(new Insets(0, 0, 0, 0));
			okBtn.addActionListener(this);
			btnPnl.add(okBtn);

			cancelBtn = new JButton("종료 취소하기");
			cancelBtn.setFont(font20);
			cancelBtn.setMargin(new Insets(0, 0, 0, 0));
			cancelBtn.addActionListener(this);
			btnPnl.add(cancelBtn);
			totalPnl.add(btnPnl);
			
			JPanel byePnl = new JPanel();
			JButton byeBtn = new JButton("프로그램 끄기");
			byeBtn.setFont(font20);
			byeBtn.setMargin(new Insets(0, 0, 0, 0));
			byeBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int result = JOptionPane.showConfirmDialog(GUI.this, "프로그램 종료?", "프로그램 종료", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						dispose();
					}
				}
			});
			byePnl.add(byeBtn);
			totalPnl.add(byePnl);

			add(totalPnl);

			showGUI();
		}

		private void showGUI() {
			pack();
			setLocation(600, 450);
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();
			if (o instanceof JButton) {
				JButton tempBtn = (JButton) o;
				if (tempBtn.equals(okBtn)) {
					if (selectedTime == 0 && selectedMinute == 0) {
						JOptionPane.showMessageDialog(this, "시간 선택 안했음", "시간선택", JOptionPane.WARNING_MESSAGE);
						return;
					} else {
						int result = JOptionPane.showConfirmDialog(this, selectedTime + "시간 " + selectedMinute + "분 예약 할거임?", "예약확인", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							int time = (selectedTime * 60 + selectedMinute) * 60;
							String commandStr = "shutdown -s -t " + time;
							run(cmd, commandStr);
							timeComb.setSelectedIndex(0);
							minuteComb.setSelectedIndex(0);
						}
					}
				} else if (tempBtn.equals(cancelBtn)) {
					int result = JOptionPane.showConfirmDialog(this, "종료 취소 할꺼임?", "취소확인", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						run(cmd, "shutdown -a");
					}
				}
			} else if (o instanceof JComboBox<?>) {
				JComboBox<Integer> tempComb = (JComboBox<Integer>) o;
				if (tempComb.equals(timeComb)) {
					selectedTime = tempComb.getSelectedIndex();
				} else if (tempComb.equals(minuteComb)) {
					selectedMinute = tempComb.getSelectedIndex();
				}
			}
		}
	}
}