package mgr;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import member.dao.MemberDAO;

@SuppressWarnings("serial")
public class LoginMgrFrame extends JFrame implements ActionListener {
 	private Font titleF,loginF;	
 	private JButton loginB;
 	private JLabel idL,pwdL,titleL,mgrL;
 	private JPanel welcomeJP;
 	private JPasswordField pwdT;
	private JTextField idT;
 	public LoginMgrFrame() {
 		Container con = getContentPane();
 		con.setLayout(null);	
 		
		//0. Ÿ��Ʋ �޼��� ����
 		titleF = new Font("Calibri Light",Font.ITALIC+Font.BOLD, 40);
 		welcomeJP = new JPanel(new FlowLayout(FlowLayout.CENTER));
 		titleL = new JLabel("***PC Cafe Program***");
 		titleL.setFont(titleF);
 		titleL.setForeground(Color.WHITE);
 		welcomeJP.setBounds(0,80,500,60);
 		welcomeJP.add(titleL);
 		welcomeJP.setOpaque(false);
 		con.add(welcomeJP);
 		loginF = new Font("Bahnschrift Light Condensed",Font.BOLD, 20);
 		mgrL = new JLabel("<������ ����>");
 		mgrL.setBounds(180,160,150, 30);
 		mgrL.setForeground(Color.RED);
 		mgrL.setFont(loginF);
 		con.add(mgrL);
		//1. �α��� ����
		//1-1. ��� ����(instance declare) + FONT ����	
 		idL = new JLabel("���̵�");
 		idL.setFont(loginF);
 		idL.setBounds(125,250,100,50);//225,300
 		pwdL = new JLabel("��й�ȣ");
 		pwdL.setFont(loginF);
 		pwdL.setBounds(125,320,150,50);//275,370		
 		
 		idT = new JTextField(10);
 		idT.setFont(loginF);	
 		idT.setBorder(null);		
		idT.setBounds(255,255,150,40);//405,295		
 		pwdT = new JPasswordField();
 		pwdT.setFont(loginF);
 		pwdT.setBorder(null);
 		pwdT.setBounds(255,325,150,40);		
 		
		BevelBorder bb = new BevelBorder(BevelBorder.RAISED);
		loginB = new JButton("�α���");
		loginB.setBorder(bb);
		loginB.setBounds(180,430,140,60);
		loginB.addActionListener(this);//�̺�Ʈ
		loginB.setFont(loginF);
		
		//1-2. �����̳ʿ� ����
 		con.add(idL);
 		con.add(idT);
 		con.add(pwdL);
 		con.add(pwdT);
		con.add(loginB);	

 		//2. Frame ����
 		setUndecorated(true);//����â �����
 		setTitle("PC Cafe Program");
 		setBounds(1200,50,500,600);
 		setResizable(false);
 		setVisible(true);
 		setDefaultCloseOperation(EXIT_ON_CLOSE);
 		
 		//�̺�Ʈ
 		idT.addActionListener(this);
 		pwdT.addActionListener(this);
 	}
 	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String id = idT.getText();
		String pw = "";
		//JPasswordFiled �� ��������
		char[] secret_pw = pwdT.getPassword();
		for(char sPW : secret_pw){ 
			Character.toString(sPW);
			pw += sPW;
		}
		
		
		if(id.equals("")){//���̵��ؽ�Ʈ �Է°� ������
			JOptionPane.showMessageDialog(this, "���̵� �Է����ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
		}else if(pw.equals("")) {//��й�ȣ �ؽ�Ʈ �Է°� ���� �� 
			JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է����ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
		}else if(!id.equals("admin")) {//������ ���� �ƴҽ�
			JOptionPane.showMessageDialog(this, "������ ������ �ƴմϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
		}else {
			MemberDAO memberDAO = MemberDAO.getInstance();
			int result = memberDAO.login(id, pw);//�α��μ���=1,����=0;
			if(result==1) {
				dispose();
				new WorkingMgrFrame();				
				new MainServer();
			}else if(result==0) {//�α��ν��н� �˸��޼���
				JOptionPane.showMessageDialog(this, "���̵� �Ǵ� ��й�ȣ�� �ٽ� Ȯ�����ּ���.", "����", JOptionPane.INFORMATION_MESSAGE);
			}				
		}
		
	}
}
