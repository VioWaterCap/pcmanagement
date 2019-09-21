package cnt;


import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import member.bean.MemberDTO;
import member.dao.MemberDAO;



@SuppressWarnings("serial")
public class Join extends JFrame implements ActionListener{	
	private JLabel idL,pwL,pwCheckL, nameL, telL,telH1L,telH2L, email1L,email2L, emailConfirmL ;
	private JTextField idT, nameT, tel2T, tel3T, email1T, email2T, emailConfirmT;
	private JPasswordField pwF, pwCF;
	private JComboBox<String> tel1C, emailC;
	private JButton idOverlapB, emailConfirmB, registB, cancelB;
	private int idCheck, emailCheck;
	private JPanel titleJP, labelJP;
	private Font titleF,loginF1,loginF2,btnF;
	private JLabel titleL;
	private JPanel textfieldJP1;
	private JPanel telfieldJP;
	private JPanel emailfieldJP;
	private JPanel emailCfieldJP;
	private JPanel confirmBtnJP;
	private String checkNum;
	public Join() {
		
		Container con = getContentPane();	
		con.setLayout(null);
		
		//0. Ÿ��Ʋ �޼��� ����
		titleJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titleJP.setBounds(0,0,500,50);
		titleF = new Font("Bahnschrift Light Condensed",Font.ITALIC+Font.BOLD,15);
		titleL = new JLabel("***����Ͻ� ȸ������ [����]�� ��Ȯ�ϰ� �Է����ּ���.");
		titleL.setFont(titleF);
		titleL.setForeground(Color.RED);
		titleJP.add(titleL);
		titleJP.setOpaque(false);
 		con.add(titleJP);
 		
 		//1.��� ����(instance declare) + FONT ����	
		
 		loginF1= new Font("Bahnschrift Light Condensed",Font.BOLD+Font.ITALIC, 15);	
		loginF2 = new Font("Bahnschrift Light Condensed",Font.PLAIN, 18);	
		btnF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
		
		String[] telCombo = {"010","011","019"};
		String[] emailCombo = {"�����Է�","naver.com","google.com"};
		
		idL = new JLabel("I                D");
		idL.setFont(loginF1);
		pwL = new JLabel("PASSWORD");
		pwL.setFont(loginF1);
		pwCheckL = new JLabel("PASSWORD Ȯ��");
		pwCheckL.setFont(loginF1);
		nameL = new JLabel("��              ��");
		nameL.setFont(loginF1);		
		telL = new JLabel("��  ��   ��  ȭ");
		telL.setFont(loginF1);
		telH1L = new JLabel("-");
		telH2L = new JLabel("-");
		email1L = new JLabel("E - MAIL");
		email1L.setFont(loginF1);
		email2L = new JLabel("@");
		emailConfirmL = new JLabel("E - MAIL   Ȯ��");
		emailConfirmL.setFont(loginF1);
		tel1C = new JComboBox<String>(telCombo);
		tel1C.setFont(loginF1);
		emailC = new JComboBox<String>(emailCombo);
		emailC.setFont(loginF1);

		idT = new JTextField(10);
		idT.setFont(loginF2);
		pwF = new JPasswordField(10);
		pwF.setFont(loginF2);	
		pwCF = new JPasswordField(10);
		pwCF.setFont(loginF2);
		nameT = new JTextField(10);
		nameT.setFont(loginF2);
		tel2T = new JTextField(4);
		tel2T.setFont(loginF2);
		tel3T = new JTextField(4);
		tel3T.setFont(loginF2);
		email1T = new JTextField(6);
		email1T.setFont(loginF2);
		email2T = new JTextField(7);
		email2T.setFont(loginF2);
		emailConfirmT = new JTextField(10);
		emailConfirmT.setFont(loginF2);
		
		idOverlapB = new JButton("�ߺ� Ȯ��");
		idOverlapB.setFont(btnF);
		emailConfirmB = new JButton("������ȣ �ޱ�");
		emailConfirmB.setFont(btnF);
		registB = new JButton("���");
		registB.setFont(btnF);
		cancelB = new JButton("���");
		cancelB.setFont(btnF);
		
		//1-2 �󺧺� ��ġ
		labelJP = new JPanel(new GridLayout(7,1,0,10));
		labelJP.setBounds(10, 40, 130, 350);//140,400
		labelJP.add(idL); 	    labelJP.add(pwL);
		labelJP.add(pwCheckL);  labelJP.add(nameL);
		labelJP.add(telL); 	    labelJP.add(email1L);	
		labelJP.add(emailConfirmL);
		con.add(labelJP);
		
		//1-2 ��� 4 textField ��ġ
		textfieldJP1 = new JPanel(new GridLayout(4,1,10,15));
		textfieldJP1.setBounds(140,45,210,190);//350,250
		textfieldJP1.add(idT);  textfieldJP1.add(pwF);
		textfieldJP1.add(pwCF); textfieldJP1.add(nameT);
		con.add(textfieldJP1);
		
		//1-3 ��ȭ��ȣ �ǳ� ��ġ-flowLayout
		telfieldJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		telfieldJP.setBounds(135, 250, 220, 50);//355,300
		telfieldJP.add(tel1C);  telfieldJP.add(telH1L);
		telfieldJP.add(tel2T);  telfieldJP.add(telH2L); 
		telfieldJP.add(tel3T);
		con.add(telfieldJP);
		
		//1-4 �̸��� �ǳ� ��ġ-flowLayout
		emailfieldJP = new JPanel(new FlowLayout());		
		emailfieldJP.setBounds(135, 300, 220, 50);//355,350
		emailfieldJP.add(email1T);
		emailfieldJP.add(email2L);
		emailfieldJP.add(email2T);
		con.add(emailfieldJP);
		
		//1-5 �̸��� Ȯ�� �ǳ� ��ġ
		emailCfieldJP = new JPanel(new GridLayout(1,1,0,0));	
		emailCfieldJP.setBounds(140,350,210,35);//350,385
		emailCfieldJP.add(emailConfirmT);
		con.add(emailCfieldJP);
		
		//1-6 ���, ��� ��ư ��ġ
		confirmBtnJP = new JPanel(new GridLayout(1,2,30,10));
		confirmBtnJP.setBounds(100,400,300,50);//400,450
		confirmBtnJP.add(registB);	confirmBtnJP.add(cancelB);
		con.add(confirmBtnJP);
		
		//1-7 ���� ��ġ�� ��ư
		idOverlapB.setBounds(360, 50, 110, 30);//470,80
		emailC.setBounds(360, 300, 120, 30);//480,330
		emailConfirmB.setBounds(360, 350, 120, 30);//480,380
		con.add(emailC);	
		con.add(idOverlapB);
		con.add(emailConfirmB);

		//2. Frame ����
		setTitle("ȸ�� ����");
		setBounds(600, 200, 500, 500);
		setVisible(true);
		setResizable(false);
		addWindowListener(new WindowAdapter(){
			@Override
			public void	windowClosing(WindowEvent e){
				dispose();
			}
		});
		
		//3. �̺�Ʈ
		idOverlapB.addActionListener(this);
		emailConfirmB.addActionListener(this);
		registB.addActionListener(this);
		cancelB.addActionListener(this);
		emailC.addActionListener(this);
		
		//3-1. ���̵� ,pw ,��ȭ��ȣ  �ؽ�Ʈ �Է� ���� 
		idT.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField id = (JTextField) e.getSource();
				if(id.getText().length()>=10) e.consume();
				
			}
		});
		pwF.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField pw = (JTextField) e.getSource();
				if(pw.getText().length()>=12) e.consume();
				
			}
		});
		pwCF.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField pw = (JTextField) e.getSource();
				if(pw.getText().length()>=12) e.consume();
				
				
			}
		});
		tel2T.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField tel = (JTextField) e.getSource();
				if(tel.getText().length()>=3) {
					tel3T.requestFocus();
					
				}
				
			}
		});
		tel3T.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e) {
				JTextField tel = (JTextField) e.getSource();
				if(tel.getText().length()>=4) e.consume();
				
				
			}
		});
		
	}//Join()-������

	@Override
	public void actionPerformed(ActionEvent e) {
		String naver = "naver.com";
		String google = "google.com";
		String userEmail = email1T.getText()+"@"+email2T.getText();
		
		
		if(idOverlapB==e.getSource()) {
			idCheck();
			
		}else if(emailConfirmB==e.getSource()) {
			checkNum = new MailSend().NaverMailSend(userEmail);//������ �̸����� MailSendŬ������ ������ ������ȣ�� ������
			JOptionPane.showMessageDialog(this, "���� ��ȣ�� �߼۵Ǿ����ϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
			emailCheck = 1;//������ȣ���߼۹޾Ҵ��� Ȯ��
			
		}
		else if(registB==e.getSource()) regist();
		else if(cancelB==e.getSource()) dispose();
		
		//�̸��� �޺��ڽ� ���ý� �ؽ�Ʈâ�� �ּ� ����
		else if(emailC==e.getSource()) {
			if(emailC.getSelectedItem()==naver)
				email2T.setText(naver);
			else if(emailC.getSelectedItem()==google)
				email2T.setText(google);
			else {
				email2T.setText("");
				email2T.requestFocus();
			}
		}	
	}//actionPerformed
	
	//id üũ �� ����� �޾Ƽ� 0 == ��밡��, 1==�����
	public void idCheck() {
		String id = idT.getText();
		if(id.equals("")) {
			JOptionPane.showMessageDialog(this, "���̵� �Է����ּ���", "����", JOptionPane.INFORMATION_MESSAGE);	
		}else {
			MemberDAO memberDAO = new MemberDAO();
			int result = memberDAO.idCheck(id);
			if(result == 0) {
				JOptionPane.showMessageDialog(this, "��밡���� ���̵� �Դϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
				idCheck=1;//ID�ߺ�Ȯ�ι�ư�� ���ȴ��� Ȯ��*****************************
			}else if(result == 1) {
				JOptionPane.showMessageDialog(this, "������� ���̵� �Դϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
				idT.setText("");
				idCheck=0;//ID�ߺ�Ȯ�ι�ư�� ���ȴ��� Ȯ��*****************************
			}
		}
	}//idcheck  method

	
	public void regist() {
		String id = idT.getText();
		String pw="";
		//�н����� �ʵ��� ��й�ȣ �������� ����
		char[] secret_pw = pwF.getPassword();
		for(char sPW : secret_pw){ 
			Character.toString(sPW);
			pw += sPW;
		}
		String pw2="";
		char[] secret_pw2 = pwCF.getPassword();
		for(char sPW : secret_pw2){ 
			Character.toString(sPW);
			pw2 += sPW;
		}
		String name = nameT.getText();
		String tel1 = (String)tel1C.getSelectedItem();
		String tel2 = tel2T.getText();
		String tel3 = tel3T.getText();
		String emaiL1 = email1T.getText();
		String email2 = email2T.getText(); 
		
		//ȸ������â�� �Է°��� ������ ���̾�α� ���
		if(id.equals(""))
			JOptionPane.showMessageDialog(this, "���̵� �Է��Ͻʽÿ�", "����", JOptionPane.INFORMATION_MESSAGE);
		else if(idCheck!=1)
			JOptionPane.showMessageDialog(this, "���̵� �ߺ�üũ ���ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
		else if(pw.equals(""))
			JOptionPane.showMessageDialog(this, "��й�ȣ�� �Է����ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
		else if(pw2.equals(""))
			JOptionPane.showMessageDialog(this, "��й�ȣ Ȯ���� �Է����ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
		else if(name.equals(""))
			JOptionPane.showMessageDialog(this, "�̸��� �Է����ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
		else if(tel2.equals("") || tel3.equals(""))
			JOptionPane.showMessageDialog(this, "�޴���ȭ������ �Է����ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
		else if(emaiL1.equals("") || email2.equals(""))
			JOptionPane.showMessageDialog(this, "�̸����� �Է����ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
		else if(emailCheck!=1)
			JOptionPane.showMessageDialog(this, "�̸����� �������ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
		else if(!checkNum.equals(emailConfirmT.getText()))
			JOptionPane.showMessageDialog(this, "������ȣ�� Ʋ�Ƚ��ϴ�, �ٽ� �Է��� �ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
		//��� �Է��� �Ǿ��ٸ� ��� ����
		else {
			MemberDTO memberDTO = new MemberDTO();
			memberDTO.setId(id);
			memberDTO.setPw(pw);
			memberDTO.setUserName(name);
			memberDTO.setTel1(tel1);
			memberDTO.setTel2(tel2);
			memberDTO.setTel3(tel3);
			memberDTO.setEmaiL1(emaiL1);
			memberDTO.setEmail2(email2);		
			
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.regist(memberDTO);
			
			JOptionPane.showMessageDialog(this, "ȸ������ �Ǿ����ϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}

		
	}//resige method

}//join class

