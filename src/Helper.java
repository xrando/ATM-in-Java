import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.text.Document;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
public class Helper
{
    public static void WriteDoc(String content)
    {
        try {
            File file = new File("Invoice.txt");

            // If file doesn't exist, then create it
            if (!file.exists())
            {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("File created successfully!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public static void SendMail(String ReceiverEmail, String Subject, String Body)
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        //Authenticate
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        //Gmail account used to send email, SMTP password generated
                        return new PasswordAuthentication("pureinc933@gmail.com", "xcgwpviomczpknrb");
                    }
                });
        //Create email message
        try {
            Message message = new MimeMessage(session);
            //Set alias for email sender
            message.setFrom(new InternetAddress("pureinc933@gmail.com", "ibanking.alert@pure.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(ReceiverEmail));
            message.setSubject(Subject);
            //Format email body
            if (Body != "")
            {
                //Add text to email
                message.setText(Body);
            }
            else
            {
                //Attach invoice to email
                // Create the attachment
                MimeBodyPart Attachment = new MimeBodyPart();
                Attachment.attachFile("Invoice.txt");

                // Add the PDF attachment to the email
                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(Attachment);
                message.setContent(multipart);
            }

            //Send email
            Transport.send(message);
            //Log
            System.out.println("Email sent successfully!");

        }
        catch (Exception e)
        {
            System.out.printf("Error due to : %s",e.getCause());
            e.printStackTrace();
        }
    }
}
