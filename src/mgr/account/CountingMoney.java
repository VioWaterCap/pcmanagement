package mgr.account;
//������� �� ���� ���� ��� class
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import mgr.sales.SalesInfoDAO;
import mgr.sales.SalesInfoDTO;

@SuppressWarnings("serial")
public class CountingMoney extends JFrame implements ActionListener {
	private JButton commitB, cancelB;
	private JLabel titleL,expMntL,realMntL,gapMntL,won1L,won2L,won3L;
	private JPanel titleJP,labelJP,textFieldJP,wonJP,buttonJP;
	private JTextField expMntT,realMntT,gapMntT;
	private Font btnF,mntF,titleF;
	
	public CountingMoney(int ExpDeposit) {
		Container con = getContentPane();
		con.setLayout(null);
		//1.
		titleF = new Font("Bahnschrift Light Condensed",Font.ITALIC+Font.BOLD,18);
		mntF = new Font("Bahnschrift Light Condensed",Font.PLAIN, 20);	
		btnF = new Font("Bahnschrift Light Condensed",Font.BOLD,15);
		
		titleL = new JLabel("**���� �ܰ� Ȯ���Ͽ� �Է����ֽñ� �ٶ��ϴ�.");
		titleL.setFont(titleF);
		titleL.setForeground(Color.RED);
		
		expMntL = new JLabel("��ϵ� �ܰ� : ",SwingConstants.LEFT);
		expMntL.setFont(mntF);
		expMntT = new JTextField();
		expMntT.setText(ExpDeposit+"");
		expMntT.setFont(mntF);
		expMntT.setEditable(false);
		won1L = new JLabel("(��)",SwingConstants.LEFT);
		won1L.setFont(mntF);	
		
		realMntL = new JLabel("���� �ܰ� : ");
		realMntL.setFont(mntF);
		realMntT = new JTextField();
		realMntT.setText(0+"");
		realMntT.setFont(mntF);
		won2L = new JLabel("(��)",SwingConstants.LEFT);
		won2L.setFont(mntF);	
		
		gapMntL = new JLabel("�ܰ� ���� : ",SwingConstants.LEFT);
		gapMntL.setFont(mntF);
		gapMntT = new JTextField();				
		gapMntT.setText("");
		gapMntT.setFont(mntF);
		gapMntT.setEditable(false);
		won3L = new JLabel("(��)",SwingConstants.LEFT);
		won3L.setFont(mntF);
		
		commitB = new JButton("�ܰ����� �ݿ�");
		commitB.setFont(btnF);
		cancelB = new JButton("���ư���");
		cancelB.setFont(btnF);
		
		//1-2. title�� ����
		titleJP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titleJP.setBounds(20,10,460,50);//480,60
		titleJP.add(titleL);
		con.add(titleJP);
		
		//1-3. �󺧺� ����
		labelJP = new JPanel(new GridLayout(3,1,10,5));
		labelJP.setBounds(50,60,200,200);//250,260
		labelJP.add(expMntL);		labelJP.add(realMntL);		labelJP.add(gapMntL);
		con.add(labelJP);
		
		//1-4. �ؽ�Ʈ�� ����
		textFieldJP = new JPanel(new GridLayout(3,1,10,10));
		textFieldJP.setBounds(250,60, 150,200);//400,260
		textFieldJP.add(expMntT);	textFieldJP.add(realMntT);	textFieldJP.add(gapMntT);
		con.add(textFieldJP);
		
		//1-5. ������ ����
		wonJP = new JPanel(new GridLayout(3,1,10,10));		
		wonJP.setBounds(420,60,60,200);//480,260
		wonJP.add(won1L);			wonJP.add(won2L);			wonJP.add(won3L);
		con.add(wonJP);
		
		//2. Button ����
		buttonJP = new JPanel(new GridLayout(1,2,10,10));
		buttonJP.setBounds(120,280,260, 50);//380,200
		buttonJP.add(commitB); buttonJP.add(cancelB);
		con.add(buttonJP);
		
		//3. Frame ����
		setTitle("[���� ����/�ܰ����� Ȯ��]");
		setBounds(600, 200, 500, 400);
		setVisible(true);
		setResizable(false);
		
		//4. event ����
		commitB.addActionListener(this);
		cancelB.addActionListener(this);
		realMntT.addActionListener(this);
	}//������

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancelB) dispose();
		else if(e.getSource()==commitB) {
			int option = JOptionPane.showConfirmDialog(this,"�������̸� �ݿ��Ͻðڽ��ϱ�?","[���� Ȯ��]",JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION) {
				int input;
				try{input = Integer.parseInt(realMntT.getText());}
				catch(NumberFormatException nf) {
					String warning ="[����]���ڸ� �Է��ϼž߸� �մϴ�.";
					JOptionPane.showMessageDialog(this,warning,"[�߸��� �Է� �˸�]",JOptionPane.INFORMATION_MESSAGE);
					realMntT.setText("");
					gapMntT.setText("");
					return;}
				int exp = Integer.parseInt(expMntT.getText());
				int gap = exp - input;
				getBalanced(gap);
				String confirnMsg="[�˸�] ���������� �ݿ��Ǿ����ϴ�.";
				JOptionPane.showMessageDialog(this,confirnMsg,"[���� ���� �ݿ�]",JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			else return;
		}
		
		if(e.getSource()==realMntT) {
			int real;
			try{real = Integer.parseInt(realMntT.getText());}
			catch(NumberFormatException nf) {
				String warning ="[����]���ڸ� �Է��ϼž߸� �մϴ�.";
				JOptionPane.showMessageDialog(this,warning,"[�߸��� �Է� �˸�]",JOptionPane.INFORMATION_MESSAGE);
				realMntT.setText("");
				gapMntT.setText("");
				return;}
			int exp = Integer.parseInt(expMntT.getText());
			int gap = exp - real;
			gapMntT.setText(gap+"");
			if(gap==0) gapMntT.setForeground(Color.BLACK);//�ٸ� �۾� �� ���ͽÿ� ����
			else if(gap<0) gapMntT.setForeground(Color.RED);//���̳ʽ��� ������
			else if(gap>0) gapMntT.setForeground(Color.BLUE);//�÷����� �Ķ���
		}//realMntT�� ������ �־��� ��
	}

	public void getBalanced(int gap) {
		int balance = (-1)*gap;
		SalesInfoDAO dao = SalesInfoDAO.getInstance();
		int transactionNum= dao.getSeq();//�ŷ���ȣ ȹ��
		
		SalesInfoDTO salesInfoDTO = new SalesInfoDTO();
		
		salesInfoDTO.setTransactionNum(transactionNum);//�ŷ���ȣ
		salesInfoDTO.setProductNum(9999);//��ǰ��ȣ
		salesInfoDTO.setProductMnt(1);//��ǰ ����
		salesInfoDTO.setProductName("�ܰ� ����");//��ǰ�̸�
		salesInfoDTO.setRevenue(balance);//�Ǵ� �Ż��
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");//���ó�¥ ����+String format
		salesInfoDTO.setSaleDate(sdf.format(today)+"");
		salesInfoDTO.setIsPaid(true);
		salesInfoDTO.setPerchaseID("SYSTEM");
		dao.updatingTransaction(salesInfoDTO);
	}//getBalanced() �޼ҵ� : DB�� �ݿ�
}
