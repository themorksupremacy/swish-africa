/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Swish.Backend;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//unnsecure apps needs to be on in the gmail account's security settings
public class EmailSender {

    public static boolean sendEmail(String to, String title, String msg) {
		final String username = "africa.swish@gmail.com"; //constant username for gmail account
		final String password = "Admin@SwishAfrica"; //constant password for gmail account

		//properties/settings for the mail to be sent
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		//new sessiion created using the given properties to be used
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		}); //checks that the username and password are correct

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("africa.swish@gmail.com")); //sets the sender of the email
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); //sets recipients of the email
			message.setSubject(title); //sets title of email
			message.setText(msg); //sets content of the email
			Transport.send(message); // message is sent
			return true; //returns true after the message is sent

		} catch (MessagingException e) {
			return false; //returns false if the message was not sent
		}
	}
}
