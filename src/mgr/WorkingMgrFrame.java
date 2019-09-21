package mgr;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import member.bean.MemberDTO;
import mgr.account.CashMgrFrame;
import mgr.account.CashierFrame;
import mgr.inventory.InvMntFrame;
import mgr.member.ClientMgtFrame;
import mgr.seat.Seat1;
import mgr.seat.Seat10;
import mgr.seat.Seat11;
import mgr.seat.Seat12;
import mgr.seat.Seat2;
import mgr.seat.Seat3;
import mgr.seat.Seat4;
import mgr.seat.Seat5;
import mgr.seat.Seat6;
import mgr.seat.Seat7;
import mgr.seat.Seat8;
import mgr.seat.Seat9;

@SuppressWarnings("serial")
public class WorkingMgrFrame extends JFrame implements ActionListener,ListSelectionListener {
	private JButton inventoryB,memberMgtB,cashMgtB,stopB,addTimeB,billingB; 
	JButton[] seatJP;
	private JPanel monitorJP;
	private ClientMgtFrame clientMgt;
	private InvMntFrame invMnt;
	private CashMgrFrame cashMgr;
	private CashierFrame cashier;
	private AddTime addtime;
	private MemberDTO memberDTO;
	private JList<MemberDTO> list;//�α��ε� �������
	private DefaultListModel<MemberDTO> model;
	public WorkingMgrFrame() {
		Container con = getContentPane();
		con.setLayout(null);
		
		//��Ʈ 
		Font buttonF = new Font("Bahnschrift Light Condensed",Font.BOLD,20);
		//����Ʈ
		model = new DefaultListModel<MemberDTO>();
		list = new JList<MemberDTO>(model);
		
		//1-1. ��� ����(instance declare) + FONT ����	

		inventoryB = new JButton("������");
		inventoryB.setFont(buttonF);
		memberMgtB = new JButton("ȸ������");
		memberMgtB.setFont(buttonF);
		cashMgtB = new JButton("�������");
		cashMgtB.setFont(buttonF);
		stopB = new JButton("���α׷� ����");
		stopB.setFont(buttonF);
		addTimeB = new JButton("�ð� �߰�");
		addTimeB.setFont(buttonF);
		billingB = new JButton("���");
		billingB.setFont(buttonF);
		//1-2 ����� JP-��Ƽ ������ ����
		monitorJP = new JPanel(new GridLayout(4,3,10,10));
		monitorJP.setBounds(30,20,750,600);//780,620
		// �¼�
		seatJP = new JButton[12];
		seatJP[0] = new Seat1();		seatJP[1] = new Seat2();	seatJP[2] = new Seat3();	
		seatJP[3] = new Seat4();		seatJP[4] = new Seat5();	seatJP[5] = new Seat6();
		seatJP[6] = new Seat7();		seatJP[7] = new Seat8();	seatJP[8] = new Seat9();	
		seatJP[9] = new Seat10();		seatJP[10] = new Seat11();	seatJP[11] = new Seat12();
		for(int i =0;i<12;i++) {monitorJP.add(seatJP[i]);}
		con.add(monitorJP);
		
		//1-3 �α����� ����Ʈ
		UserList loginList = new UserList();
		loginList.setBounds(800,38,300,220);
		con.add(loginList);
		list = loginList.getList();
		
		//2-1 Type1 ��ư ���� 
		JPanel buttonJP1 = new JPanel(new GridLayout(2,2,5,5));
		buttonJP1.setBounds(810,550,350,200);//1160,750
		buttonJP1.add(memberMgtB); buttonJP1.add(inventoryB);
		buttonJP1.add(cashMgtB);	buttonJP1.add(stopB);
		con.add(buttonJP1);
	
		//2-2 �ð� ������ ����
		JPanel clockJP = new ClockMgr();
		clockJP.setBounds(0,650,280,100);//280,750
		con.add(clockJP);
		

		//2-3 Type2 ��ư ���� 
		
		JPanel buttonJP2 = new JPanel(new GridLayout(1,2,10,10));
		buttonJP2.setBounds(300,650,460,100);//760,750
		buttonJP2.add(addTimeB);	buttonJP2.add(billingB);
		con.add(buttonJP2);
		
 		//3. Frame ����
 		setUndecorated(true);//����â �����
 		setTitle("PC Cafe Program");//
 		setBounds(450,100,1200,800);
 		setResizable(false);
 		setVisible(true);
 		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
 		
 		//4. Event ����
 		inventoryB.addActionListener(this);
 		memberMgtB.addActionListener(this);
 		cashMgtB.addActionListener(this);
 		stopB.addActionListener(this);
 		addTimeB.addActionListener(this);
 		billingB.addActionListener(this);
 		list.addListSelectionListener(this);
 		billingB.setEnabled(false);
	}//WorkingMgrFrame() ������
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == memberMgtB) {
			if(clientMgt == null) clientMgt = new ClientMgtFrame();
			else {clientMgt.dispose(); clientMgt = new ClientMgtFrame();}}
		else if(e.getSource() == inventoryB) {
			if(invMnt==null) invMnt= new InvMntFrame();
			else {invMnt.dispose(); invMnt= new InvMntFrame();}}
		else if(e.getSource() == cashMgtB) {
			if(cashMgr==null) cashMgr= new CashMgrFrame();
			else {cashMgr.dispose(); cashMgr =new CashMgrFrame();}}
		else if(e.getSource() == billingB) {
			if(cashier==null) cashier= new CashierFrame(memberDTO);
			else {cashier.dispose(); cashier =new CashierFrame(memberDTO);}}
		else if(e.getSource()==addTimeB) {
			if(addtime==null) addtime = new AddTime();
			else{addtime.dispose(); addtime = new AddTime();}}
		else if(e.getSource() == stopB) {
			if(list.getFirstVisibleIndex()>=0) {JOptionPane.showMessageDialog(this, "[���]���� �ִ� ����ڰ� �ִ� ��쿡�� ������ �� �����ϴ�.", "[���α׷� ���� ����]",
					JOptionPane.INFORMATION_MESSAGE); return;}
			else System.exit(0);}
	}//actionPerformed �޼ҵ�

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(list.getSelectedIndex()==-1) {billingB.setEnabled(false); return;}
		else if(list.isSelectionEmpty()){billingB.setEnabled(false); return;}
		else{
			memberDTO = list.getSelectedValue();
			billingB.setEnabled(true);}
	}



}//WorkingMgrFrame Class
