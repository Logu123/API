package testEmail;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import fileUtils.ReadPropertiesFile;

public class testEmail {

	public void sendingReports() {


//		credentials
		final String username = "no-replysimplink@trustmarkcompanies.onmicrosoft.com";
		final String password = "Fun21067";
		final String fromEmail = "no-replysimplink@trustmarkcompanies.onmicrosoft.com";
		ReadPropertiesFile property = new ReadPropertiesFile();
		String toEmail = property.toEmails();
//		"aanand-cw@trustmarkbenefits.com,mveerappan@trustmarkbenefits.com,apurvaa@hexaware.com,aravindanv@hexaware.com"

//		setting up the email properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.office365.com");
		properties.put("mail.smtp.port", "587");

//		starting a new session
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

//		body message text
		String mailBodyText = "Hi,\r\n"+"\r\nThis email contains two attachements. "
				+ "\r\nThe Spreadsheet contains the Rates from the Rate Engine in first sheet and"
				+ " the Text file contains the Summary of the test execution and details of failed scenarios, if any.\r\n"
				+"\r\n"+"\r\n"+"\r\n"+"Regards,"+"\r\nTeam"+"\r\n"+"\r\n"+"\r\n";
		
//		start our mail message
		MimeMessage msg = new MimeMessage(session);
		
		try {
			msg.setFrom(new InternetAddress(fromEmail));

//			Multiple email addresses
			msg.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail));

			// subject line of email
			msg.setSubject("Rates from Rate Engine & Summary of Scenario Execution");
			Multipart emailContent = new MimeMultipart();

			// Body text for the email
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(mailBodyText);

			// Attachments to the email
			MimeBodyPart attachment1 = new MimeBodyPart();
			attachment1.attachFile("src/main/java/Data From API/RateBook.xlsx");

			MimeBodyPart attachment2 = new MimeBodyPart();
			attachment2.attachFile("src/main/java/Data From API/Summary.txt");

			// attach body parts
			emailContent.addBodyPart(textBodyPart);
			emailContent.addBodyPart(attachment1);
			emailContent.addBodyPart(attachment2);

			// attach multipart to message
			msg.setContent(emailContent);

			Transport.send(msg);

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
