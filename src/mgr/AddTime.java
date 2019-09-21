package mgr;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import member.dao.MemberDAO;
import mgr.sales.SalesInfoDAO;

@SuppressWarnings("serial")
public class AddTime extends JFrame implements ActionListener{
	private JLabel idL;
	private JTextField idT;
	private JButton time1,time2,time3,time4, addB,cancelB;
	private Font idF;
	private int time, money;
	
	public AddTime() {
		setLayout(null);
		
		idF= new Font("Bahnschrift Light Condensed",Font.BOLD, 15);
		
		Container con = getContentPane();
		//��� �߰�
		idL = new JLabel("I   D :");
		idL.setFont(idF);
		idL.setBounds(30, 5, 100, 50);
		idT = new JTextField(10);
		idT.setFont(idF);
		idT.setBounds(80, 5, 250, 40);
		
		time1 = new JButton("1�ð�  1,000��");
		time1.setFont(idF);
		time2 = new JButton("2�ð�  2,000��");
		time2.setFont(idF);
		time3 = new JButton("3�ð�  3,000��");
		time3.setFont(idF);
		time4 = new JButton("4�ð�  4,000��");
		time4.setFont(idF);

		JPanel timeP = new JPanel();
		timeP.setLayout(new GridLayout(2,2,30,30));
		timeP.add(time1);
		timeP.add(time2);
		timeP.add(time3);
		timeP.add(time4);
		timeP.setBounds(50, 70, 300, 150);
		
		addB = new JButton("�ð� �߰�");
		addB.setFont(idF);
		cancelB = new JButton("���");
		cancelB.setFont(idF);
		
		JPanel buttonP = new JPanel();
		buttonP.setLayout(new GridLayout(1,2,15,15));
		buttonP.add(addB);
		buttonP.add(cancelB);
		buttonP.setBounds(50, 250, 300, 50);
		
		//�����̳� �߰�
		con.add(idL);
		con.add(idT);
		con.add(timeP);
		con.add(buttonP);
		
		setBounds(450,100,400,350);
 		setResizable(false);
 		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//��ư���θ� �������� ����
		
		//�̺�Ʈ
		time1.addActionListener(this);
		time2.addActionListener(this);
		time3.addActionListener(this);
		time4.addActionListener(this);
		addB.addActionListener(this);
		cancelB.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(time1==e.getSource()) {//1,000�� ��ư �������� ������ ����
			time = 60;
			money = 1000;
		}else if(time2==e.getSource()) {
			time = 120;
			money = 1000;
		}else if(time3==e.getSource()) {
			time = 180;
			money = 1000;
		}else if(time4==e.getSource()) {
			time = 240;
			money = 1000;
		}else if(addB == e.getSource()) {
			if(idT.getText().equals("")){
				JOptionPane.showMessageDialog(this, "ID�� �Է����ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
			}else if(time==0||money==0){
				JOptionPane.showMessageDialog(this, "�ð��� �������ּ���", "����", JOptionPane.INFORMATION_MESSAGE);
			}else {
				
				String id = idT.getText();
				MemberDAO memberDAO = MemberDAO.getInstance();
				SalesInfoDAO salesInfoDAO = SalesInfoDAO.getInstance(); 
				int result = memberDAO.idCheck(id);
				if(result==0) {
					JOptionPane.showMessageDialog(this, "���� ���̵��Դϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
					time=0;
					money=0;
				}else {
					int dbTime = memberDAO.getUserTime(id);
					int prepaid = money*time/60;
					time += dbTime;
					memberDAO.setTime(time, id);
					salesInfoDAO.getprePaid(prepaid);
					JOptionPane.showMessageDialog(this, id+"���� �ð��� �߰��Ǿ����ϴ�.", "����", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}				
			}
		}else if(cancelB==e.getSource()) {
			dispose();
		}
		
	}
}
