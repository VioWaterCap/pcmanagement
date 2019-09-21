package mgr.sales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import member.bean.MemberDTO;

public class SalesInfoDAO {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@192.168.6.27:1521:xe";
	//private String url = "jdbc:oracle:thin:@192.168.6.20:1521:xe";
	private String user = "java";
	private String password = "itbank";
	
	private static SalesInfoDAO instance;
	
	public static SalesInfoDAO getInstance() {
		if(instance==null) {
			synchronized (SalesInfoDAO.class) {instance = new SalesInfoDAO();}}
		return instance;}
	
	public SalesInfoDAO() {
		try {Class.forName(driver);} 
		catch (ClassNotFoundException e) {e.printStackTrace();}}//������
	
	public Connection getConnection() {
		Connection conn = null;
		try {conn = DriverManager.getConnection(url, user, password);}
		catch (SQLException e) {e.printStackTrace();}
		return conn;}
	
	public int getSeq(){//�ŷ���ȣ�� seq���� �����´�.
		int seq=0;
		String sql = "select seq_sales.nextval from dual";
		
		Connection conn = this.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			seq = rs.getInt(1);} 
		catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();} 
			catch (SQLException e) {e.printStackTrace();}}
		return seq;}
	
	public ArrayList<SalesInfoDTO> getSalesInfoList(SalesInfoSel selection){//ȭ�鿡 ����� �Ѹ��� �뵵
		ArrayList<SalesInfoDTO> list = new ArrayList<SalesInfoDTO>();
		String sql;//��¥�Լ��� �д����� ���̴� ����� ��� ���� �����ؼ� ǥ����.
		if(selection == SalesInfoSel.SEARCHTODAY) {
			sql = "select �ŷ���ȣ,��ǰ��ȣ,��ǰ��,�������,�����,to_char(��������,'YY/MM/DD') ��������, ���Կ���, ������ID from �Ż� where to_date(��������,'YY/MM/DD') = to_date(sysdate,'YY/MM/DD')";}
		else if(selection == SalesInfoSel.SEARCHMONTH) {
			sql ="select �ŷ���ȣ,��ǰ��ȣ,��ǰ��,�������,�����,to_char(��������,'YY/MM/DD') ��������, ���Կ���, ������ID from �Ż� where trunc(to_date(��������,'YY/MM/DD'),'month') = trunc(to_date(sysdate,'YY/MM/DD'),'month')";}
		else if(selection == SalesInfoSel.SEARCHNONPAID) {
			sql="select �ŷ���ȣ,��ǰ��ȣ,��ǰ��,�������,�����,to_char(��������,'YY/MM/DD') ��������, ���Կ���, ������ID from �Ż� where ���Կ��� = 0";}
		else sql ="select �ŷ���ȣ,��ǰ��ȣ,��ǰ��,�������,�����,to_char(��������,'YY/MM/DD') ��������, ���Կ���, ������ID from �Ż�";
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SalesInfoDTO dto = new SalesInfoDTO();
				dto.setTransactionNum(rs.getInt("�ŷ���ȣ"));
				dto.setProductNum(rs.getInt("��ǰ��ȣ"));//�ĺ��ڵ�
				dto.setProductName(rs.getString("��ǰ��"));
				dto.setProductMnt(rs.getInt("�������"));
				dto.setRevenue(rs.getInt("�����"));
				dto.setSaleDate(rs.getString("��������"));
				dto.setIsPaid(rs.getBoolean("���Կ���"));
				dto.setPerchaseID(rs.getString("������ID"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			list=null;
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();		
			}
		}
		return list;
	}//getSalesInfoList() �޼ҵ� : �Ż� Ȯ�� 
	
	public int checkingDeposit() {
		int expDeposit=0;
		Connection conn = this.getConnection();
		String sql = "select sum(�����) as �����  from �Ż�  where ���Կ���=1";			
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			expDeposit = rs.getInt("�����");
			}
		catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
		return expDeposit;
		
	}//checkingDeposit() �޼ҵ�: ��ϻ� �ܰ� Ȯ���ϴ� �޼ҵ�
	
	public ArrayList<SalesInfoDTO> getPersonPurchase(MemberDTO memberDTO){//ȭ�鿡 ����� �Ѹ��� �뵵
		String memberID = memberDTO.getId();
		ArrayList<SalesInfoDTO> list = new ArrayList<SalesInfoDTO>();
		String sql;
		sql="select ��ǰ��ȣ,��ǰ��,�������,�����, ���Կ���, ������ID from �Ż� where ���Կ��� = 0 and ������ID like ?";

		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SalesInfoDTO dto = new SalesInfoDTO();
				dto.setProductNum(rs.getInt("��ǰ��ȣ"));//�ĺ��ڵ�
				dto.setProductName(rs.getString("��ǰ��"));
				dto.setProductMnt(rs.getInt("�������"));
				dto.setRevenue(rs.getInt("�����"));
				dto.setIsPaid(rs.getBoolean("���Կ���"));
				dto.setPerchaseID(rs.getString("������ID"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			list=null;
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();		
			}
		}
		return list;
	}//getSalesInfoList() �޼ҵ� : ����� �����
	
	
	public void updatingTransaction(SalesInfoDTO salesInfoDTO) {//�ŷ� �ݿ�
		SalesInfoDTO InfoDTO = salesInfoDTO;
		String sql="insert into �Ż� values(?,?,?,?,?,?,?,?)";
		int isPaid;
		if(InfoDTO.getIsPaid()) isPaid = 1;
		else isPaid = 0; 
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,InfoDTO.getTransactionNum());
			pstmt.setInt(2,InfoDTO.getProductNum());
			pstmt.setString(3, InfoDTO.getProductName());
			pstmt.setInt(4, InfoDTO.getRevenue());
			pstmt.setString(5, InfoDTO.getSaleDate());
			pstmt.setInt(6,isPaid);
			pstmt.setInt(7, InfoDTO.getProductMnt());
			pstmt.setString(8, InfoDTO.getPerchaseID());
			pstmt.executeUpdate();}
		catch (SQLException e) {e.printStackTrace(); }
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();} 
			catch (SQLException e) {e.printStackTrace();}}
	}//�Ż� �߰� �޼ҵ�

	
	public int getItemSum(MemberDTO memberDTO,boolean totalpay) {//��ǰ ���ž� �հ� ���ϱ�
		int intemSum=0;
		String userID = memberDTO.getId();
		Connection conn = this.getConnection();
		String sql;
		if(totalpay) sql = "select sum(�����) as ���Ծ�  from �Ż�  where (������ID like ?)";			
		else sql = "select sum(�����) as ���Ծ� from �Ż�  where (���Կ���=0 and ������ID like ?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userID);
		rs = pstmt.executeQuery();
		rs.next();
		intemSum = rs.getInt("���Ծ�");
		}
	catch (SQLException e) {e.printStackTrace();}
	finally {
		try {
			if(pstmt!=null) pstmt.close();
			if(conn!=null) conn.close();} 
		catch (SQLException e) {e.printStackTrace();}
	}
	return intemSum;
	}

	public void getprePaid(int nowPrePaid) {//�������ϱ�
		int payment = nowPrePaid;
		int seq =  getSeq();
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
		String salesdate = sdf.format(today)+"";
		String sql = "insert into �Ż� values(?,2002,'PC ���ҿ��',?,?,1,1,'SYSTEM')";
	
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, seq);
			pstmt.setInt(2, payment);
			pstmt.setString(3,salesdate);
			pstmt.executeUpdate();}
		catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();} 
			catch (SQLException e) {e.printStackTrace();}}
	}
	
	public void getPaid(MemberDTO memberDTO,SalesInfoSel sel) {//�����ϱ�
		int seq =  getSeq();
		String sql1="update �Ż� set ���Կ���=1,������ID= ? where ������ID like ?";
		String sql2="insert into �Ż� values(?,2001,'PC �����',?,?,1,1,'SYSTEM')";
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
		String salesdate = sdf.format(today)+"";
		int salesprice = memberDTO.getPostPayment();
		System.out.println(salesprice);
		Connection conn = getConnection();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			if(sel == SalesInfoSel.TOTALPAY || sel == SalesInfoSel.ITEMPAY) {//sql1�� ����				
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.setString(1,"SYSTEM");
				String userId = memberDTO.getId();
				pstmt1.setString(2, userId);
				pstmt1.executeUpdate();;}//�ŷ��� ����ó���� �����ϴ� ���๮
				
			if(sel == SalesInfoSel.TOTALPAY || sel == SalesInfoSel.PCPAY) {//sql2�� ����
				
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1,seq);
				pstmt2.setInt(2,salesprice);
				pstmt2.setString(3,salesdate);
				pstmt2.executeUpdate();}//PC����� ����ó���ϴ� ���๮
			}
		catch (SQLException e) {e.printStackTrace();}
		finally {
			try {
				if(pstmt1 != null) pstmt1.close();
				if(pstmt2 != null) pstmt2.close();
				if(conn != null) conn.close();} 
			catch (SQLException e) {e.printStackTrace();}}	
	}//getPaid �޼ҵ�: ���� ���� �޼ҵ�
}
