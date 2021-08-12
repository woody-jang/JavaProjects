import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

public class MyInfo extends JFrame{

	public MyInfo(User loginUser) {
		JPanel userInfoPnl = new JPanel();
		userInfoPnl.setLayout(new BoxLayout(userInfoPnl, BoxLayout.Y_AXIS));
		
		JPanel namePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel nameLbl = new JLabel("이 름");
		nameLbl.setPreferredSize(new Dimension(95,12));
		nameLbl.setHorizontalAlignment(JLabel.CENTER);
		JTextField nameTf = new JTextField(18);
		nameTf.setText(loginUser.getName());
		nameTf.setEditable(false);
		namePnl.add(nameLbl);
		namePnl.add(nameTf);
		userInfoPnl.add(namePnl);
		
		JPanel birthPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel birthLbl = new JLabel("생년월일");
		birthLbl.setPreferredSize(new Dimension(95,12));
		birthLbl.setHorizontalAlignment(JLabel.CENTER);
		JTextField birthTf = new JTextField(18);
		birthTf.setText(loginUser.getBirth());
		birthTf.setEditable(false);
		birthPnl.add(birthLbl);
		birthPnl.add(birthTf);
		userInfoPnl.add(birthPnl);
		
		JPanel uidPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel uidLbl = new JLabel("아이디");
		uidLbl.setPreferredSize(new Dimension(95,12));
		uidLbl.setHorizontalAlignment(JLabel.CENTER);
		JTextField uidTf = new JTextField(18);
		uidTf.setText(loginUser.getUid());
		uidTf.setEditable(false);
		uidPnl.add(uidLbl);
		uidPnl.add(uidTf);
		userInfoPnl.add(uidPnl);
		
		add(userInfoPnl);
		
		showGUI(loginUser.getName());
	}

	
	private void showGUI(String name) {
		setTitle(name + "님 정보");
		setLocation(700, 400);
		setSize(280, 250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
