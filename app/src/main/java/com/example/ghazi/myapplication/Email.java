package com.example.ghazi.myapplication;
import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by ghazi on 26/07/2016.
 */
public class Email {

  private Session session = null;
    private MimeMessage message = null;



    public Email(Setting setting,String path){


        final String adresseMail = setting.getEmail();


        //Création de la Session
        Properties props = new Properties();

        props.setProperty("mail.transport.protocol","smtp");
        props.setProperty("mail.from","ghazivdlz07@gmail.com");
        props.setProperty("mail.smtp.user","ghazivdlz07@gmail.com");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.user","ghazivdlz07@gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "25");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.EnableSSL.enable", "true");

         session = Session.getDefaultInstance(props);



        //Création du Message
         message = new MimeMessage(session);
        try {


            message.setSubject("App Service QoS 2G/3G");

            message.addRecipients(Message.RecipientType.TO,adresseMail);


        } catch (MessagingException e) {
            e.printStackTrace();
        }



        //les piéce jointes       -----------------------------------------




        File f = new File(path);



        FileDataSource dataSource = null;


        if(f.exists()){
            dataSource = new FileDataSource(f);

        }

        DataHandler dataHandler = new DataHandler(dataSource);



        //le MimeBodyPart represente la piéce jointe
        MimeBodyPart fich = new MimeBodyPart();


        try {


            fich.setDataHandler(dataHandler);

            fich.setFileName(dataSource.getName());



            MimeMultipart multiPart = new MimeMultipart();
            // on peut ajouter plusieurs piéces jointe avec plusieur apelle de addBodyPart
            multiPart.addBodyPart(fich);


            message.setContent(multiPart);

        } catch (MessagingException e1) {
            e1.printStackTrace();
        }



        //Transport du message

       new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Transport transport = session.getTransport("smtp");
                    transport.connect("ghazivdlz07@gmail.com","northvandals07");
                    transport.sendMessage(message,new Address[] {new InternetAddress(adresseMail)});


                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }



}
