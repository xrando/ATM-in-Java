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
    /*Sample message body for invoice
    StringBuilder str = new StringBuilder();
        str.append("Transaction Ref: " + "UID" + "\n\n");
        str.append("Dear Sir / Madam,\n\n");
        str.append("We refer to your Transaction dated " + "Date.Now()" + "." + " We are pleased to confirm that the transaction was completed.\n\n");
        str.append("Date & Time: " + "DateTime.Now()" + "\n");
        str.append("Amount: " + "$9.00" + "\n");
        str.append("From: " + "Pure Savings Account A/C ending 1234" + "\n");
        str.append("To: " + "Pure Savings Account A/C ending 4321" + "\n\n");
        str.append("If unauthorised, please call our Pure hotline. To view transaction details, please login to digibank.\n\n");
        str.append("Thank you for banking with us.\n\n\n");
        str.append("Yours faithfully\n" +
                "Pure Bank Ltd\n" +
                "\n" +
                "Please do not reply to this email as it is auto-generated.\n"); */
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
