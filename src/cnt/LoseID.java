package cnt;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import member.dao.MemberDAO;


@SuppressWarnings("serial")
public class LoseID extends JFrame implements ActionListener{
	private JLabel nameL,emailL,email2L,titleL;
	private JTextField nameT, email1T, email2T;
	private JComboBox<String> emailC ;
	private JButton searchB, cancelB;
	private JPanel titleJP;
	private Font titleF;
	private Font loginF1;
	private Font loginF2;
	private Font btnF;
	private JPanel labelJP;
	private JPanel emailJP;
	private JPanel nameJP;
	private JPanel buttonJP;
	public LoseID(){
		Container con = getContentPane();
		con.setLayout(null);
		//0. Ÿ��Ʋ �޼��� ����
		titleJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titleF = new Font("Bahnschrift Light Condensed",Font.ITALIC+Font.BOLD, 15);
		titleL = new JLabel("***ȸ������ [�̸�]�� [�̸��� �ּ�]�� �Է����ּ���.");
		titleL.setFont(titleF);
		titleL.setForeground(Color.RED);
		titleJP.setBounds(20,20,430,50);//450,70
		titleJP.add(titleL);
		titleJP.setOpaque(false);
 		con.add(titleJP);
 		
		//1-1. ��� ����(instance declare) + FONT ����	
 		
		String[] emailCombo = {"�����Է�","naver.com","google.com"};	
		
		loginF1 = new Font("Bahnschrift Light Condensed",Font.BOLD+Font.ITALIC, 15);
		nameL = new JLabel("��         ��   ");
		nameL.setFont(loginF1);
		emailL = new JLabel("��  ��  ��  ");
		emailL.setFont(loginF1);
		email2L = new JLabel("@");
		email2L.setFont(loginF1);
		emailC = new JComboBox<String>(emailCombo);
		emailC.setFont(loginF1);	
		
		loginF2 = new Font("Bahnschrift Light Condensed",Font.PLAIN, 18);	
		nameT = new JTextField(15);
		nameT.setFont(loginF2);
		email1T = new JTextField(6);
		email1T.setFont(loginF2);
		email2T = new JTextField(7);
		email2T.setFont(loginF2);
		
		btnF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
		searchB = new JButton("ã��");
		searchB.setFont(btnF);
		cancelB = new JButton("���");
		cancelB.setFont(btnF);
		
		//1-2 �󺧺� ��ġ
		labelJP = new JPanel(new GridLayout(2,1,0,20));
		labelJP.setBounds(20,80,100,100);//120,180
		labelJP.add(nameL);		labelJP.add(emailL);
		con.add(labelJP);
		
		//1-3 �̸� ��ġ
		nameJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		nameJP.setBounds(120,80,230,40);//350,120
		nameJP.add(nameT);
		con.add(nameJP);
		
		//1-4 �̸��� ��ġ
		emailJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		emailJP.setBounds(120,140,230,40);//350,180
		emailJP.add(email1T);	emailJP.add(email2L);
		emailJP.add(email2T);		
		con.add(emailJP);
		
		//1-5 �޺��ڽ� ��ġ(���� ��ġ)
		emailC.setBounds(350,145,120,35);//480,180
		con.add(emailC);
		
		//1-6 ��ư ��ġ
		buttonJP = new JPanel(new GridLayout(1,2,20,5));
		buttonJP.setBounds(120,200,260,50);//380,250
		buttonJP.add(searchB);	buttonJP.add(cancelB);		
		con.add(buttonJP);

		//2. Frame ����
		setTitle("���̵� ã��");
		setBounds(600, 200, 500, 300);
		setVisible(true);
		setResizable(false);
		addWindowListener(new WindowAdapter(){
			@Override
			public void	windowClosing(WindowEvent e){
				dispose();
			}
		});
		
		//�̺�Ʈ
		searchB.addActionListener(this);
		cancelB.addActionListener(this);
		emailC.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String naver = "naver.com";
		String google = "google.com";
		
		if(searchB == e.getSource()) {
			searchID();
		}else if(cancelB == e.getSource()) {
			dispose();
		}else if(emailC==e.getSource()) {
			if(emailC.getSelectedItem()==naver)
				email2T.setText(naver);
			else if(emailC.getSelectedItem()==google)
				email2T.setText(google);
			else {
				email2T.setText("");
				email2T.requestFocus();
			}
		
		}
	}

	public void searchID() {
		String name = nameT.getText();
		String email1 = email1T.getText();
		String email2 = email2T.getText();
		
		MemberDAO memberDAO = MemberDAO.getInstance();
		String id = memberDAO.searchID(name, email1, email2);
		if(id==null) {
			JOptionPane.showMessageDialog(this, "�߸��� �����Է��Դϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(this, "������ ���̵�� "+id+" �Դϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}
		
}//loseID class
	

