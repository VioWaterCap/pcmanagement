package mgr.inventory;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class InvDeleteFrame extends JFrame {
	private JLabel deleteL, titleL;
	private JPanel labelJP;
	private JPanel buttonJP;
	private JPanel textJP;
	private JPanel delJP;
	private JTextField deleteT;
	private JButton deleteB, cancelB;
	private Font labelF, textF,titleF, ConfirmF;
	
	//
	public InvDeleteFrame() {
		Container con = getContentPane();
		con.setLayout(null);
		
		
		titleF = new Font("����ü", Font.BOLD, 40);
		delJP = new JPanel(new FlowLayout(FlowLayout.CENTER));
		titleL = new JLabel("**** ��ǰ����");
		titleL.setFont(titleF);
		titleL.setForeground(Color.WHITE);
		delJP.setBounds(0, 30, 500, 60);
		delJP.add(titleL);
		delJP.setOpaque(false);
		con.add(delJP);
		
		//��, �ؽ�Ʈ�ʵ�, ��ư ��Ʈ
		labelF = new Font("Bahnschrift Light Condensed", Font.BOLD + Font.ITALIC, 15);
		textF = new Font("Bahnschrift Light Condensed", Font.PLAIN, 18);
		ConfirmF = new Font("Bahnschrift Light Condensed", Font.BOLD, 15);

		//���� �󺧻���
		deleteL = new JLabel("���� ǰ��");
		deleteL.setFont(labelF);
		
		//���� �ؽ�Ʈ�ʵ� ����
		deleteT = new JTextField(20);
		deleteT.setFont(textF);
		
		//���� ��ư ����
		deleteB = new JButton("����");
		deleteB.setFont(ConfirmF);
		cancelB = new JButton("���");
		cancelB.setFont(ConfirmF);

		// �� ��ġ
		labelJP = new JPanel(new GridLayout(1, 1, 0, 20));
		labelJP.setBounds(50, 10, 70, 30);
		labelJP.add(deleteL);
		con.add(labelJP);

		textJP = new JPanel(new GridLayout(1, 1, 0, 15));
		textJP.setBounds(150, 10, 100, 30);
		textJP.add(deleteT);
		con.add(textJP);

		buttonJP = new JPanel(new GridLayout(1, 2, 15, 5));
		buttonJP.setBounds(70, 85, 150, 30);
		buttonJP.add(deleteB);
		buttonJP.add(cancelB);
		con.add(buttonJP);
		
		//������ ����
		setUndecorated(true);// ����â �����
		setTitle("��ǰ����");

		setBounds(600, 50, 300, 200);
		setResizable(false);
		setVisible(true);

	}

	public static void main(String[] args) {
		new InvDeleteFrame();
	}

}