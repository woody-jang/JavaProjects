import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalTime;

import javax.swing.*;

class AutoSave extends JFrame implements ActionListener, ItemListener {
	// 사용자가 텍스트를 입력할 수 있는 JTextArea를 생성하여 추가하고
	// 사용자가 작성한 텍스트 내용을
	// 1분마다 Database에 자동 저장할 수 있게 구현해보세요.

	private TextAreaDAO textDao;
	private JTextArea inputTextTa;
	private JButton saveBtn;
	private JButton loadBtn;
	private JCheckBox autoSaveCbx;
	private Font font20 = new Font("굴림", Font.PLAIN, 20);
	private Timer autoSaveTimer;

	public JTextArea getInputTextTa() {
		return inputTextTa;
	}

	public AutoSave() {
		textDao = new TextAreaDAO();
		autoSaveTimer = new Timer(1000 * 60, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textDao.save(LocalTime.now(), inputTextTa.getText(), true);
			}
		});

		JPanel btnPnl = new JPanel();
		saveBtn = new JButton("저장");
		loadBtn = new JButton("불러오기");

		saveBtn.addActionListener(this);
		loadBtn.addActionListener(this);

		btnPnl.add(saveBtn);
		btnPnl.add(loadBtn);

		inputTextTa = new JTextArea();
		inputTextTa.setFont(font20);
		JScrollPane scrlTa = new JScrollPane(inputTextTa);

		autoSaveCbx = new JCheckBox("자동 저장");
		autoSaveCbx.addItemListener(this);

		add(btnPnl, "North");
		add(scrlTa, "Center");
		add(autoSaveCbx, "South");
		showGUI();
	}

	private void showGUI() {
		setLocation(650, 400);
		setSize(400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			autoSaveTimer.start();
		} else if (e.getStateChange() == ItemEvent.DESELECTED) {
			autoSaveTimer.stop();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(saveBtn)) {
			int result = textDao.save(LocalTime.now(), inputTextTa.getText(), false);
			if (result == 1) {
				JOptionPane.showMessageDialog(this, "저장 완료");
			}
		} else if (e.getSource().equals(loadBtn)) {
			new LoadDialog(this);
		}
	}
}

public class Main {
	public static void main(String[] args) {
		new AutoSave();
	}
}