import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;

class DataPanel extends JPanel {
	private Font font30 = new Font("굴림", Font.PLAIN, 30);
	private int id;
	
	public int getId() {
		return id;
	}

	public DataPanel(TextAreaData data) {
		id = data.getId();
		JLabel id = new JLabel(String.valueOf(data.getId()) + ".");
		String formatted = data.getSaveTime().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
		JLabel time = new JLabel(formatted);
		JLabel isAuto = new JLabel(data.isAuto() ? "자동저장" : "수동저장");
		id.setFont(font30);
		time.setFont(font30);
		isAuto.setFont(font30);

		add(id);
		add(time);
		add(isAuto);

		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(400, 48));
	}
}

public class LoadDialog extends JDialog {
	private TextAreaDAO textDao;

	public LoadDialog(AutoSave main) {
		textDao = new TextAreaDAO();
		JTextArea txtArea = main.getInputTextTa();

		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));

		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DataPanel pnl = (DataPanel) e.getSource();
				String savedata = textDao.load(pnl.getId());
				txtArea.setText(savedata);
				dispose();
			}
		};
		
		List<TextAreaData> textDataList = textDao.load();
		for (TextAreaData data : textDataList) {
			DataPanel pnl = new DataPanel(data);
			pnl.addMouseListener(mouseAdapter);
			pnlCenter.add(pnl);
		}

		add(pnlCenter);
		showGUI();
	}

	private void showGUI() {
//		setSize(400, 400);
		setLocation(640, 480);
		pack();
		setVisible(true);
	}
}
