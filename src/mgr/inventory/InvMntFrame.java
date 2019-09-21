package mgr.inventory;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class InvMntFrame extends JFrame implements ActionListener {
	private JButton addInvB, modifyInvB, deleteInvB;
	private DefaultTableModel model;
	private JTable tableInv;
	private JPanel tableJP, buttonJP;
	private JScrollPane scroll;
	private Vector<String> vector;


	public InvMntFrame() {
		Container con = getContentPane();
		con.setLayout(null);

		// 1-1.���̺� Ÿ��Ʋ ����
		vector = new Vector<String>();
		vector.addElement("ITEMNUM");
		vector.addElement("ITEM");
		vector.addElement("PRICE");
		vector.addElement("STOCK");

		// 1-2.tablemodel ����
		model = new DefaultTableModel(vector, 0) {
			public boolean isCellEditable(int r, int c) {
				return (c != 0) ? true : false;
			}
		};

		showView();

		// 1-3.JTable ����
		tableInv = new JTable(model);
		scroll = new JScrollPane(tableInv);

		// 1-4. Panel����
		tableJP = new JPanel();
		tableJP.setLayout(new BorderLayout());
		tableJP.add(scroll, BorderLayout.CENTER);
		tableJP.setBounds(20, 20, 660, 380);// 680,400
		con.add(tableJP);
		// 2-1. ��ư ����
		addInvB = new JButton("��ǰ �߰�");
		modifyInvB = new JButton("��ǰ ����");
		deleteInvB = new JButton("��ǰ ����");

		// 2-2. Panel����
		buttonJP = new JPanel(new GridLayout(1, 3, 10, 5));
		buttonJP.setBounds(100, 420, 500, 40);// 600,460
		buttonJP.add(addInvB);
		buttonJP.add(modifyInvB);
		buttonJP.add(deleteInvB);
		con.add(buttonJP);

		// 3. Frame ����
		setUndecorated(true);// ����â �����
		setTitle("��ǰ ����");
		setBounds(450, 100, 700, 500);
		setResizable(false);
		setVisible(true);

		// 4. Event ����
		addInvB.addActionListener(this);
		modifyInvB.addActionListener(this);
		deleteInvB.addActionListener(this);

	}

	public void showView() {// ���������� ������ ��ü��ȸ, ���������� ������ �Ϻ� ��ȸ

		model.setRowCount(0);

		InventoryDAO inventoryDAO = InventoryDAO.getInstance();
		ArrayList<InventoryDTO> list = inventoryDAO.getInventoryList();

		for (InventoryDTO inventoryDTO : list) {

			Vector<String> vec = new Vector<String>();
			vec.addElement(Integer.toString(inventoryDTO.getItemNum()));
			vec.addElement(inventoryDTO.getItem());
			vec.addElement(Integer.toString(inventoryDTO.getPrice()));
			vec.addElement(Integer.toString(inventoryDTO.getStock()));

			model.addRow(vec);
		}

	}// totalView() �޼ҵ� : ��ü����
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (e.getSource() == addInvB) {
			new InvAddFrame();
			dispose();
		} else if (e.getSource() == deleteInvB) {
			if(tableInv.getSelectedRow()==-1) return;//������ ���� ������ ����;
			String itemName = (String) model.getValueAt(tableInv.getSelectedRow(), 1);

			int result = JOptionPane.showConfirmDialog(this, itemName + "�� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				// ����
				InventoryDAO.getInstance().deleteInventory(itemName);
				showView();
			}
		} else if (e.getSource() == modifyInvB) {
			if(tableInv.getSelectedRow()==-1) return;//������ ���� ������ ����;
			String itemNum = (String) model.getValueAt(tableInv.getSelectedRow(), 0);
			String itemName = (String) model.getValueAt(tableInv.getSelectedRow(), 1);
			String itemPrice = (String) model.getValueAt(tableInv.getSelectedRow(), 2);
			String itemStock = (String) model.getValueAt(tableInv.getSelectedRow(), 3);

			InventoryDTO inventoryDTO = new InventoryDTO(Integer.parseInt(itemNum), itemName,
					Integer.parseInt(itemPrice), Integer.parseInt(itemStock));

			new InvModifyFrame(inventoryDTO);
			dispose();
		}

	}

}
