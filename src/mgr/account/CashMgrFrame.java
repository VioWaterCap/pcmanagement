package mgr.account;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mgr.sales.SalesInfoDAO;
import mgr.sales.SalesInfoMgr;

@SuppressWarnings("serial")
public class CashMgrFrame extends JFrame implements ActionListener {
	private int expMoney = 0;
	private JButton cashCheckB, saleCheckB,cancelB;
	private JLabel titleL,expMntL;
	private JPanel titleJP,expMntJP,buttonJP;
	private JTextField expMntT;
	private Font btnF,expMntF,titleF;
	
	private CountingMoney countMoney;
	private SalesInfoMgr  salesInfo;
	public CashMgrFrame() {
		Container con = getContentPane();
		con.setLayout(null);
		SalesInfoDAO dao = SalesInfoDAO.getInstance();
		expMoney = dao.checkingDeposit();//DB �ҷ��� �ܰ���� �˻�
		
		//1-1. ��� ����(instance declare) + FONT ����	
		titleF = new Font("Bahnschrift Light Condensed",Font.ITALIC+Font.BOLD,18);
		expMntF = new Font("Bahnschrift Light Condensed",Font.PLAIN, 20);	
		btnF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
		
		titleL = new JLabel("**(����)���� ������ �ܰ� �ݾ��� ������ �����ϴ�.");
		titleL.setFont(titleF);
		titleL.setForeground(Color.RED);
		
		expMntL = new JLabel("��ϵ� �ܰ� : ",SwingConstants.LEFT);
		expMntL.setFont(expMntF);
		expMntT = new JTextField(10);
		expMntT.setText(expMoney+" (��)");
		expMntT.setEditable(false);
		cashCheckB = new JButton("����Ȯ��");
		cashCheckB.setFont(btnF);
		saleCheckB = new JButton("�Ż�Ȯ��");
		saleCheckB.setFont(btnF);
		cancelB = new JButton("���ư���");
		cancelB.setFont(btnF);
		
		//1-2. title�� ����
		titleJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titleJP.setBounds(20,20,460,50);//480,70
		titleJP.add(titleL);
		con.add(titleJP);
		
		//1-3. �󺧺� ����
		expMntJP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		expMntJP.setBounds(100,100, 300, 50);//400,150
		expMntJP.add(expMntL); expMntJP.add(expMntT);
		con.add(expMntJP);
		
		//2. Button ����
		buttonJP = new JPanel(new GridLayout(1,3,10,10));
		buttonJP.setBounds(100,150, 300, 50);//400,200
		buttonJP.add(cashCheckB); buttonJP.add(saleCheckB); buttonJP.add(cancelB);
		con.add(buttonJP);
		
		//2. Frame ����
		setTitle("[����/�Ż� ����]");
		setBounds(600, 200, 500, 250);
		setVisible(true);
		setResizable(false);
		//3. Event ����
		cashCheckB.addActionListener(this);
		saleCheckB.addActionListener(this);
		cancelB.addActionListener(this);
	}//CashMgrFrame() - ������
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cashCheckB) {
			if(countMoney == null) countMoney = new CountingMoney(expMoney);
		    else {countMoney.dispose(); countMoney = new CountingMoney(expMoney);}}//�ߺ�â ����(����: â�� �������)
		else if(e.getSource()==saleCheckB) {
			if(salesInfo == null) salesInfo = new SalesInfoMgr();
		    else {salesInfo.dispose(); salesInfo = new SalesInfoMgr();}}
		else if(e.getSource()==cancelB) {dispose();}
		
	}//actionPerformed() �޼ҵ�
	
}//CashMgrFrame Class
