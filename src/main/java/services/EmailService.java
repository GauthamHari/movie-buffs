package services;
import com.sendgrid.*;

public class EmailService {

	public static void main(String[] args) {
		SendGrid sendgrid = new SendGrid("","");
		SendGrid.Email email = new SendGrid.Email();
		email.addTo("hg1990@gmail.com");
		email.setFrom("ghari1@unh.newhaven.edu");
		email.setSubject("Hello World");
		email.setText("My first email through SendGrid");

		try {
		  SendGrid.Response response = sendgrid.send(email);
		}
		catch (SendGridException e) {
		  System.out.println(e);
		}
	}
}
