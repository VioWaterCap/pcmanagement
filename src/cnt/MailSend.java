package cnt;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSend {
	public MailSend() {}
	  public String NaverMailSend(String userEmail) {
		    Properties props;
		    @SuppressWarnings("unused")
			String host = "smtp@naver.com";
			String user= "yull052@naver.com";
			String password = "!@#phoenix5";
			String checkNum = null;

			props = new Properties();
			props.put("mail.smtp.host", "smtp.naver.com");
			props.put("mail.smtp.port", 587);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.trust", "smtp.naver.com");

			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);

				}
			});
			try {
				MimeMessage message = new MimeMessage(session);
				//���Ž� �߽��� ǥ��
				message.setFrom(new InternetAddress(user,"XXPC��"));

				//������ ���� �ּ�
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(""+userEmail+""));

				//���� ����
				message.setSubject("���� ��ȣ Ȯ��");

				checkNum = checkNum();//������ȣ ���� �޼���
				//���� ����
				message.setText("XXPC�� ������ȣ : "+checkNum);

				Transport.send(message);
			}catch (Exception e) {
				e.printStackTrace();
			}

			return checkNum;
	  }//mailSend()


	  //������ȣ ����
	  public String checkNum() {
		  String checkNum = "";
		  for(int i=0; i<5; i++) {
			  checkNum += Integer.toString((int)(Math.random()*10));//���� ���� ������ ������ȣ�� ����
		  }

		return checkNum;
	  }
}
