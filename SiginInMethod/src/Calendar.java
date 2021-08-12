import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Calendar extends JDialog {
	static String selectedDate = "None";
	private List<JButton> dayBtns;
	private LocalDate today;
	private JPanel mainPnl;
	private JPanel calPnl;
	private JLabel nowMonthLbl;
	private JPanel weekLblPnl;
	private JButton lastClickedBtn;
	private Font font20 = new Font("굴림체", Font.PLAIN, 20);

	public Calendar() {
		selectedDate = "None";
		today = LocalDate.of(1990, 1, 1);
		mainPnl = new JPanel();
		mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

		JPanel titlePnl = new JPanel();
		JButton lastMonthBtn = new JButton("◀");
		setTopButtonDefault(lastMonthBtn);
		titlePnl.add(lastMonthBtn);

		nowMonthLbl = new JLabel(" " + today.getYear() + "." + today.getMonthValue() + " ");
		nowMonthLbl.setFont(font20);
		nowMonthLbl.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		titlePnl.add(nowMonthLbl);

		JButton nextMonthBtn = new JButton("▶");
		setTopButtonDefault(nextMonthBtn);
		titlePnl.add(nextMonthBtn);
		mainPnl.add(titlePnl);

		calPnl = new JPanel();
		calPnl.setLayout(new BoxLayout(calPnl, BoxLayout.Y_AXIS));

		calPnl.add(addWeekDayPnl());
		calPnl.add(addDayLbl());
		mainPnl.add(calPnl);

		JPanel btnPnl = new JPanel();
		JButton okBtn = new JButton("확 인");
		setDownBtnDefault(okBtn);
		btnPnl.add(okBtn);

		JButton goTodayBtn = new JButton("오늘으로");
		setDownBtnDefault(goTodayBtn);
		btnPnl.add(goTodayBtn);

		JButton cancelBtn = new JButton("취 소");
		setDownBtnDefault(cancelBtn);
		btnPnl.add(cancelBtn);
		mainPnl.add(btnPnl);

		goTodayBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				today = LocalDate.now();
				repaintCalendar();
			}
		});

		nowMonthLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] chooseDate = new int[2];
				new ChooseYearMonth(chooseDate);
				if (chooseDate[0] != 0) {
					today = LocalDate.of(chooseDate[0], chooseDate[1], 1);
					repaintCalendar();
				}
			}
		});

		lastMonthBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				today = LocalDate.of(today.getYear(), today.getMonth(), 1);
				today = today.plusDays(-1);
				repaintCalendar();
			}

		});

		nextMonthBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				today = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth());
				today = today.plusDays(1);
				repaintCalendar();
			}
		});

		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedDate.equals("None")) {
					JOptionPane.showMessageDialog(null, "날짜를 선택하세요", "날짜 선택", JOptionPane.ERROR_MESSAGE);
				} else {
					LocalDate selected = null;
					int selectedYear = Integer.parseInt(selectedDate.split("-")[0]);
					int selectedMonth = Integer.parseInt(selectedDate.split("-")[1]);
					int selectedDay = Integer.parseInt(selectedDate.split("-")[2]);
					selected = LocalDate.of(selectedYear, selectedMonth, selectedDay);
					today = LocalDate.now();
					if (selected.getDayOfYear() > today.getDayOfYear() || selected.getYear() > today.getYear()) {
						JOptionPane.showMessageDialog(null, "오늘 이전을 선택하세요", "선택 에러", JOptionPane.ERROR_MESSAGE);
						return;
					}
					dispose();
				}
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedDate = "None";
				dispose();
			}

		});

		add(mainPnl);

		showGUI();
	}

	private void showGUI() {
		setTitle("생일선택");
		setModal(true);
		pack();
		setLocation(730, 380);
		setVisible(true);
	}

	private void setTopButtonDefault(JButton tempBtn) {
		tempBtn.setFont(font20);
		tempBtn.setMargin(new Insets(0, 0, 0, 0));
		tempBtn.setPreferredSize(new Dimension(26, 26));
		tempBtn.setBackground(Color.white);
	}

	private void setDownBtnDefault(JButton tempBtn) {
		tempBtn.setFont(font20);
		tempBtn.setPreferredSize(new Dimension(100, 30));
		tempBtn.setMargin(new Insets(0, 5, 0, 5));
	}

	private void repaintCalendar() {
		calPnl.removeAll();
		calPnl.add(weekLblPnl);
		calPnl.add(addDayLbl());
		if (selectedDate.length() > 5) {
			int selectedYear = Integer.parseInt(selectedDate.split("[.]")[0]);
			int selectedMonth = Integer.parseInt(selectedDate.split("[.]")[1]);
			int selectedDay = Integer.parseInt(selectedDate.split("[.]")[2]);
			if (selectedYear == today.getYear()) {
				if (selectedMonth == today.getMonthValue()) {
					dayBtns.get(selectedDay - 1).setBackground(new Color(101, 255, 94));
				}
			}
		}
		nowMonthLbl.setText(" " + today.getYear() + "." + today.getMonthValue() + " ");
		revalidate();
		repaint();
		pack();
	}

	private JPanel addWeekDayPnl() {
		weekLblPnl = new JPanel();
		String[] weekDay = { "일", "월", "화", "수", "목", "금", "토" };
		for (int i = 0; i < 7; i++) {
			JLabel weekDayLbl = new JLabel(weekDay[i]);
			weekDayLbl.setFont(font20);
			weekDayLbl.setPreferredSize(new Dimension(40, 30));
			weekDayLbl.setHorizontalAlignment(JLabel.CENTER);
			weekDayLbl.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			weekLblPnl.add(weekDayLbl);
		}
		weekLblPnl.getComponent(0).setForeground(new Color(255, 0, 0));
		weekLblPnl.getComponent(6).setForeground(new Color(0, 0, 255));
		return weekLblPnl;
	}

	private JPanel addDayLbl() {
		dayBtns = new ArrayList<JButton>();
		today = LocalDate.of(today.getYear(), today.getMonth(), 1);
		JPanel tempPnl = new JPanel();
		int startOfMonth = today.getDayOfWeek().getValue();
		tempPnl.setLayout(new BoxLayout(tempPnl, BoxLayout.Y_AXIS));

		for (int i = 1; i <= today.lengthOfMonth(); i++) {
			int todayWeek = today.getDayOfWeek().getValue();
			if (i != 1 && todayWeek == 7) {
				JPanel temp = new JPanel();
				int startDay = (i - 7 > 0) ? i - 7 - 1 : 0;
				if (startDay == 0 && startOfMonth != 7) {
					for (int j = 0; j < startOfMonth; j++) {
						JLabel tempLbl = new JLabel(" ");
						tempLbl.setPreferredSize(new Dimension(40, 30));
						temp.add(tempLbl);
					}
				}
				for (int j = startDay; j < i - 1; j++) {
					temp.add(dayBtns.get(j));
				}
				tempPnl.add(temp);
			}

			JButton nowBtn = new JButton(String.valueOf(today.getDayOfMonth()));
			nowBtn.setPreferredSize(new Dimension(40, 30));
			nowBtn.setFont(font20);
			nowBtn.setBackground(Color.white);
			nowBtn.setMargin(new Insets(0, 0, 0, 0));
			if (todayWeek == 7) {
				nowBtn.setForeground(new Color(255, 0, 0));
			} else if (todayWeek == 6) {
				nowBtn.setForeground(new Color(0, 0, 255));
			}
			dayBtns.add(nowBtn);

			lastClickedBtn = null;
			nowBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton tempPressedBtn = (JButton) e.getSource();
					tempPressedBtn.setBackground(new Color(101, 255, 94));
					int idx = dayBtns.indexOf(tempPressedBtn);
					StringBuffer sb = new StringBuffer();
					sb.append(today.getYear() + "-");
					sb.append(today.getMonthValue() + "-");
					sb.append(String.valueOf(idx + 1));
					selectedDate = sb.toString();
					if (lastClickedBtn != null) {
						lastClickedBtn.setBackground(Color.white);
					}
					lastClickedBtn = tempPressedBtn;
				}
			});

			if (i == today.lengthOfMonth()) {
				JPanel temp = new JPanel();
				if (todayWeek == 7) {
					todayWeek = 0;
				}
				int lastTemp = i - todayWeek - 1;
				for (int j = lastTemp; j < dayBtns.size(); j++) {
					temp.add(dayBtns.get(j));
				}
				if (todayWeek != 6) {
					for (int j = 0; j < 6 - todayWeek; j++) {
						JLabel tempLbl = new JLabel(" ");
						tempLbl.setPreferredSize(new Dimension(40, 30));
						temp.add(tempLbl);
					}
				}
				tempPnl.add(temp);
				break;
			}
			today = today.plusDays(1);

		}
		return tempPnl;
	}

	class ChooseYearMonth extends JDialog {
		public ChooseYearMonth(int[] chooseDate) {

			JPanel mainPnl = new JPanel();
			mainPnl.setLayout(new BoxLayout(mainPnl, BoxLayout.Y_AXIS));

			JPanel inputPnl = new JPanel();
			JLabel inputYearLbl = new JLabel("년도");
			inputYearLbl.setFont(font20);
			inputPnl.add(inputYearLbl);

			JTextField inputYearTf = new JTextField(5);
			inputYearTf.setToolTipText("년도 4자리 입력");
			inputYearTf.setFont(font20);
			inputPnl.add(inputYearTf);

			JLabel inputMonthLbl = new JLabel(" 월");
			inputMonthLbl.setFont(font20);
			inputPnl.add(inputMonthLbl);

			JTextField inputMonthTf = new JTextField(3);
			inputMonthTf.setToolTipText("월 입력");
			inputMonthTf.setFont(font20);
			inputPnl.add(inputMonthTf);

			JPanel btnPnl = new JPanel();
			JButton okBtn = new JButton("확인");
			okBtn.setFont(font20);
			okBtn.setMargin(new Insets(0, 0, 0, 0));
			btnPnl.add(okBtn);

			JButton cancelBtn = new JButton("취소");
			cancelBtn.setFont(font20);
			cancelBtn.setMargin(new Insets(0, 0, 0, 0));
			btnPnl.add(cancelBtn);

			mainPnl.add(inputPnl);
			mainPnl.add(btnPnl);

			inputYearTf.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char temp = e.getKeyChar();
					if (!(Character.isDigit(temp) || temp == 127 || temp == 8 || temp == 27)) {
						JOptionPane.showMessageDialog(null, "숫자만 입력하세요", "입력", JOptionPane.ERROR_MESSAGE);
						e.consume();
						return;
					}
					if (inputYearTf.getText().length() >= 4) {
						JOptionPane.showMessageDialog(null, "년도 4자리를 입력하세요", "입력", JOptionPane.ERROR_MESSAGE);
						e.consume();
					}
				}
			});

			inputMonthTf.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char temp = e.getKeyChar();
					if (!(Character.isDigit(temp) || temp == 127 || temp == 8 || temp == 27)) {
						JOptionPane.showMessageDialog(null, "숫자만 입력하세요", "입력", JOptionPane.ERROR_MESSAGE);
						e.consume();
						return;
					}
					if (inputMonthTf.getText().length() >= 2) {
						JOptionPane.showMessageDialog(null, "월을 입력하세요", "입력", JOptionPane.ERROR_MESSAGE);
						e.consume();
					}
				}
			});

			okBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!inputYearTf.getText().equals("")) {
						int inputMonth = Integer.parseInt(inputMonthTf.getText());
						if (!inputMonthTf.getText().equals("")) {
							if (inputMonth > 12 || inputMonth < 1) {
								JOptionPane.showMessageDialog(null, "월을 확인하세요", "월", JOptionPane.ERROR_MESSAGE);
								inputMonthTf.setText("");
								return;
							} else {
								chooseDate[0] = Integer.parseInt(inputYearTf.getText());
								chooseDate[1] = Integer.parseInt(inputMonthTf.getText());
								dispose();
								return;
							}
						} else {
							JOptionPane.showMessageDialog(null, "월을 입력하세요", "월", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} else {
						JOptionPane.showMessageDialog(null, "년도를 입력하세요", "년도", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			});

			cancelBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			add(mainPnl);
			showGUI();
		}

		private void showGUI() {
			setTitle("년/월 선택");
			setModal(true);
			pack();
			setLocation(800, 430);
			setVisible(true);
		}
	}
}