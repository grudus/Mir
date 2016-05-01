package com.grudus.help;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {


    private Properties properties;
    private Session session;
    private RandomString randomString;

    private MimeMessage mimeMessage;

    public EmailSender() {
        properties = new Properties();
        properties.put("mail.smtp.host", com.grudus.help.Properties.SMTP_HOST_NAME);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", com.grudus.help.Properties.SMTP_PORT);
        session = Session.getDefaultInstance(properties, new SMTPAuthenticator());
        randomString = new RandomString(32);
    }


    public void send(final String message, final String emailRecipient) throws MessagingException {
        mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(com.grudus.help.Properties.SMTP_AUTH_USER));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipient));
        mimeMessage.setSubject("MESSAGE FROM JAVA");
        mimeMessage.setText(message);

        Transport.send(mimeMessage);
        System.out.println("Sent message successfully...");
    }

    public String getURL() {
        return randomString.nextString();
    }


//  ###########################################################################

    private class SMTPAuthenticator extends Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication()
        {
            String username = com.grudus.help.Properties.SMTP_AUTH_USER;
            String password = com.grudus.help.Properties.SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }

    private class RandomString {

        private char[] symbols;
        private char[] buf;
        private final java.util.Random random = new java.util.Random();

        private void init() {
            StringBuilder tmp = new StringBuilder();
            for (char ch = '0'; ch <= '9'; ++ch)
                tmp.append(ch);
            for (char ch = 'a'; ch <= 'z'; ++ch)
                tmp.append(ch);
            symbols = tmp.toString().toCharArray();
        }



        public RandomString(int length) {
            if (length < 1)
                throw new IllegalArgumentException("length < 1: " + length);
            init();
            buf = new char[length];

        }

        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }
    }

}
