package cnt;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import cnt.CntServer;
import member.bean.MemberDTO;
import member.dao.MemberDAO;
import mgr.InfoDTO;

@SuppressWarnings("serial")
public class WorkingCntFrame extends JFrame implements ActionListener,Runnable {
	private JButton messageB,orderB,stopB,exitB;
	private JLabel uTimeL1,uTimeL2,prepayMntL1,prepayMntL2;
	private JPanel buttonJP,titleJP,uTimeJP;
	private Font titleF,labelF;
	private Container titleL;
	private String userID;
	private String userName;
	private OrderMenu order;
	private ClientChat chat;
	private int stopSW; 
	private Thread t;
	private MemberDTO memberDTO;
	private int fixTime;
	
	public WorkingCntFrame(MemberDTO memberDTO) {
		this.memberDTO = memberDTO;
		this.userID  = memberDTO.getId();
		this.userName = memberDTO.getUserName();
		fixTime = memberDTO.getTime();
		
		Container con = getContentPane();
		con.setLayout(null);
		//0. Ÿ��Ʋ �޼��� ����
		titleJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titleJP.setBounds(10,0,750,50);
		titleF = new Font("Bahnschrift Light Condensed",Font.BOLD,20);
		titleL = new JLabel("["+userName+"]�� �ȳ��ϼ���. **** PC�濡 ���� ���� �������� ȯ���մϴ�.");
		titleL.setFont(titleF);
		titleL.setForeground(Color.RED);
		titleJP.add(titleL);
		titleJP.setOpaque(false);
 		con.add(titleJP);
 		
		//1-1. ��� ����(instance declare) + FONT ����	
 		
 		
 		labelF = new Font("Bahnschrift Light Condensed",Font.BOLD,18);
 		uTimeL1 = new JLabel("���� �ð�");
 		uTimeL1.setFont(labelF);
 		
		int time = memberDTO.getTime();
		int minute = 0;
		if(time>=60) {//db�� ����� �д����� ���� �ð����� ����
			minute = time%60;
			time /= 60;
			uTimeL2 = new JLabel(time+"�ð� "+minute+"��",SwingConstants.RIGHT);}
		else {
			minute = time;
			uTimeL2 = new JLabel(minute+"��",SwingConstants.RIGHT);}
 		uTimeL2.setFont(labelF);
 		
 		
 		prepayMntL1 = new JLabel("���� �ܾ�");
 		prepayMntL1.setFont(labelF);
 		prepayMntL2 = new JLabel(fixTime*25+"��",SwingConstants.RIGHT);
 		prepayMntL2.setFont(labelF);
 		messageB = new JButton(new ImageIcon("src/lib/messa.png"));
		messageB.setBorderPainted(false);
		messageB.setFocusPainted(false);
		messageB.setContentAreaFilled(false);
		
		orderB = new JButton(new ImageIcon("src/lib/foods.png"));
		orderB.setBorderPainted(false);
		orderB.setFocusPainted(false);
		orderB.setContentAreaFilled(false);
		
	
		stopB = new JButton(new ImageIcon("src/lib/chains.png"));
		stopB.setOpaque(true);
		stopB.setBorderPainted(false);
		stopB.setFocusPainted(false);
		stopB.setContentAreaFilled(false);
		
		exitB = new JButton(new ImageIcon("src/lib/stop.png"));
		exitB.setBorderPainted(false);
		exitB.setFocusPainted(false);
		exitB.setContentAreaFilled(false);
		

		//1-2.����� ����(������� ���� ǥ��)
		//JPanel compNumJP = new JPanel();
		JLabel compNumJP = new JLabel(memberDTO.getSeat()+"",SwingConstants.CENTER);
		//JLabel seatL = new JLabel("�¼���ȣ : "+memberDTO.getSeat());
		compNumJP.setFont(new Font("Bahnschrift Light Condensed",Font.BOLD,120));
		Font nameLF = new Font("Bahnschrift Light Condensed",Font.BOLD,20);
		JLabel nameL1 = new JLabel("I 		           D",SwingConstants.CENTER);
		JLabel nameL2 = new JLabel(memberDTO.getUserName(),SwingConstants.CENTER);
		nameL1.setFont(nameLF); 	nameL2.setFont(nameLF);
		compNumJP.setLayout(new GridLayout(1,1,5,5));
		compNumJP.setBounds(20,50,200,200);//220,250
		//compNumJP.add(seatL);;
		LineBorder lb = new LineBorder(Color.GREEN,5);
		compNumJP.setBorder(lb);
		con.add(compNumJP);
		
		//1-3. ��� ���� ����(�ǳ� ����, ���ҿ�� �߰�)
		uTimeJP = new JPanel(new GridLayout(3,2,20,20));
		uTimeJP.setBounds(250,50,260,200);//520,250
		uTimeJP.add(uTimeL1); 	  uTimeJP.add(uTimeL2);
		uTimeJP.add(prepayMntL1); uTimeJP.add(prepayMntL2);
		uTimeJP.add(nameL1);		  uTimeJP.add(nameL2);
		con.add(uTimeJP);
		
		
		//1-4. ��ư ����
		buttonJP = new JPanel(new GridLayout(2,2,20,20));
		buttonJP.setBounds(520,50,260,200);//780,250
		buttonJP.add(messageB);		buttonJP.add(orderB);
		buttonJP.add(stopB);		buttonJP.add(exitB);
		con.add(buttonJP);
		
		//���ð� Thread ����
		t = new Thread(this);
		t.start();
				
		
 		//2. Frame ����
 		setTitle("PC Cafe Program");
 		setBounds(450,100,800,300);
 		setResizable(false);
 		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
 		//3. event ����
 		messageB.addActionListener(this);
 		orderB.addActionListener(this);
 		stopB.addActionListener(this);
 		exitB.addActionListener(this);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == messageB) {
			if (chat == null) new ClientChat(memberDTO);
			else {chat.dispose(); chat = new ClientChat(memberDTO);}}
		if(e.getSource()==orderB) {
			if(order == null) order = new OrderMenu(memberDTO);
			else {order.dispose(); order = new OrderMenu(memberDTO);}}//�ߺ�â ����(����: â�� �������)
		if(e.getSource()== stopB){ // ������ �޼ҵ�
			if(stopSW==0) {//stopSW�⺻��0, ó�� ������ �� �۵�
				JOptionPane.showMessageDialog(this, "�Ͻ� ���� �Ǿ����ϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
				stopSW=1;//������ư�� ���ȴٴ� ǥ�� =1;
				stopB.setIcon(new ImageIcon("lib/chains1.png"));
				messageB.setEnabled(false);
				orderB.setEnabled(false);
				try {//���ͷ�Ʈ�� ����
					t.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					t.interrupt();//���ͷ�Ʈ ���� �߻�
				}
			
			}else if(stopSW==1) {
				JOptionPane.showMessageDialog(this, "�Ͻ� ������ �����Ǿ����ϴ�", "����", JOptionPane.INFORMATION_MESSAGE);
				stopSW=0;
				stopB.setIcon(new ImageIcon("lib/chains.png"));
				messageB.setEnabled(true);
				orderB.setEnabled(true);
				
			}
		}
		if(e.getSource()== exitB) {//ȭ���� ����
			int result = JOptionPane.showConfirmDialog(this,"���� �����Ͻðڽ��ϱ�?","[PC ��� ����]",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION){
				MemberDAO dao = MemberDAO.getInstance();
				dao.setOff(memberDTO.getId());
				dao.setSeatOut(userID);//�¼� ������ ���� �߼��� �� �޼�������
				CntServer cnt = CntServer.getInstance(memberDTO);
				InfoDTO infoDTO = new InfoDTO();
				infoDTO.setUserName(userName);
				infoDTO.setUserID(userID);
				infoDTO.setUserTime(memberDTO.getTime());
				infoDTO.setTotCost(memberDTO.getPostPayment());
				cnt.getExit(infoDTO);

				this.dispose();
				new LoginFrame();
			}
			else return;			
		}
		
	}//actionPerformed �޼ҵ�
	
	@Override
	public void run() {
		int time = fixTime;
		int minute =0;
		int sw=0;
		if (time<60) {
			
			minute=time;
			time=0;
		}
		
		for(int i=0; i<=fixTime; i++) {//ó�� ����ð���ŭ ����
			if(i%4==0) {//5�и��� �谨
				int lefttime = fixTime -i;
				prepayMntL2.setText(lefttime*25+"(��)");
				
			while(true) {//���ͷ�Ʈ�� �ɸ��� ��������, stopSW��0�̸� ������ư�� ������ �������̹Ƿ� ��������
				if(t.isInterrupted()||stopSW==0)break;

				}//
			}
			
			if(time>=60) {//60�� �̻��̸� 1�ð� ���� ����
				minute = time%60;
				time /= 60;
			}else
			
			if(sw==0) {
				MemberDAO memberDAO = MemberDAO.getInstance();
				memberDAO.setTime((time*60)+minute, memberDTO.getId());
				if(minute == -1) {//���� -1 �� �Ǹ� 59�� ������ �ð� 1�ð� ����
					time-=1;
					minute= 59;
				}
				if(time==0) {//�ð��� ������ �и� ǥ��
					uTimeL2.setText(minute+"��");
				}else {
					uTimeL2.setText(time+"�ð� "+minute+"��");
				}
				sw=1;
				minute-=1;
				
			}else if(sw!=0){
				MemberDAO memberDAO = MemberDAO.getInstance();
				memberDAO.setTime((time*60)+minute, memberDTO.getId());
				if(minute == -1) {
					time-=1;
					minute = 59;
				}
				if(time==0) {
					uTimeL2.setText(minute+"��");
				}else {
					uTimeL2.setText(time+"�ð� "+minute+"��");
				}
				sw=0;
				minute-=1;
			}

			
			try{
		
				Thread.sleep(900);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}//for
		
		//�ð��� �� ���� �� ���� â ���� �� �α���â ���.
		
		MemberDAO dao = MemberDAO.getInstance();  
		dao.setTime(time, userID);//DB�� ���̵�� ����ð� 0 �� ������ update��Ŵ
		dao.setOff(userID);
		dao.setSeatOut(userID);
		
		CntServer cnt = CntServer.getInstance(memberDTO);
		InfoDTO infoDTO = new InfoDTO();
		infoDTO.setUserName(userName);
		infoDTO.setUserID(userID);
		infoDTO.setUserTime(memberDTO.getTime());
		infoDTO.setTotCost(memberDTO.getPostPayment());
		cnt.getExit(infoDTO);
		
		dispose();
		new LoginFrame();
		
	}//run
	
}//WorkingCntFrame Class