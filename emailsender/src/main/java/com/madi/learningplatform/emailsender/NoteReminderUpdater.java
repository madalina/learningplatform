package com.madi.learningplatform.emailsender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.madi.learningplatform.Note;
import com.madi.learningplatform.DatabaseConnection;

public class NoteReminderUpdater implements Job {

    private static final Logger log = LoggerFactory
            .getLogger(NoteReminderUpdater.class);

    public static void main(String[] args) {
        NoteReminderUpdater nru = new NoteReminderUpdater();
        try {
            nru.execute(null);
        } catch (JobExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        log.info("Preparing to fetch today's batch of reminders...");

        Statement statement = null;
        ResultSet resultSet = null;
        List<Note> expressions = new ArrayList<Note>();

        try {
            statement = DatabaseConnection.getConnection().createStatement();
            resultSet = statement
                    .executeQuery("select * from notes where remindme = true and collection = 1");

            while (resultSet.next()) {
                expressions.add(new Note(resultSet.getString("front"),
                        resultSet.getString("back")));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        
        Collections.shuffle(expressions);

        String emailContent = "Morning sunshine, here are your Norwegian phrases! :) \n --------------------------------------------------------\n\n";

        Iterator<Note> it = expressions.iterator();
        while (it.hasNext()) {
            Note note = it.next();
            emailContent += (">" + note.getFront() + " ---> " + note.getBack() + "\n\n");
        }

        emailContent += "\n\nBest regards,\nLearningplatform";
        sendEmail(emailContent);

        /*
         * DBCollection table = State.getDatabaseConn().getCollection("notes");
         * BasicDBObject whereQuery = new BasicDBObject();
         * whereQuery.put("remindme", true);
         * 
         * DBCursor cursor = table.find(whereQuery); while (cursor.hasNext()) {
         * DBObject row = cursor.next(); expressions.add(new
         * Note(row.get("front").toString(),row.get("back").toString())); }
         */
    }

    private void sendEmail(String emailContent) {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "madi.mutihac@gmail.com", "honolulu23");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("learningplatform@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("madi.mutihac@gmail.com"));
            message.setSubject("Morning cup of phrases --- grab your coffee!");
            message.setText(emailContent);

            Transport.send(message);

            log.info("Email sent successfully");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
