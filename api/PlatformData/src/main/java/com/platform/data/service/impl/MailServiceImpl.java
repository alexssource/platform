package com.platform.data.service.impl;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 23.12.16.
 */
public class MailServiceImpl implements MailService {
    private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    private String host;

    private String port;

    private String protocol;

    private String socketFactoryClass;

    private String socketFactoryPort;

    private String fallback;

    private String auth;

    private String quitwait;

    private String username;

    private String password;

    private String from;


    @Override
    public boolean sendEmail(String to, String replyTo, String subject, String message, String ccEmail) {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

        // Get a Properties object
        Properties props = System.getProperties();
        if ("smtps".equals(protocol)) {
            props.setProperty("mail.smtps.host", host);
            props.setProperty("mail.smtps.socketFactory.class", socketFactoryClass);
            props.setProperty("mail.smtps.socketFactory.fallback", fallback);
            props.setProperty("mail.smtps.port", port);
            props.setProperty("mail.smtps.socketFactory.port", socketFactoryPort);
            props.setProperty("mail.smtps.auth", auth);
        } else {
            props.setProperty("mail.smtp.host", host);
            props.setProperty("mail.smtp.socketFactory.class", socketFactoryClass);
            props.setProperty("mail.smtp.socketFactory.fallback", fallback);
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.socketFactory.port", socketFactoryPort);
            props.setProperty("mail.smtp.auth", auth);
        }

        /*
        If set to false, the QUIT command is sent and the connection is immediately closed. If set
        to true (the default), causes the transport to wait for the response to the QUIT command.

        ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
                http://forum.java.sun.com/thread.jspa?threadID=5205249
                smtpsend.java - demo program from javamail
        */
        props.put("mail.smtps.quitwait", quitwait);

        Session session = Session.getInstance(props, null);

        // -- Create a new message --
        final MimeMessage msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setReplyTo(new Address[]{new InternetAddress(replyTo)});

            if (StringUtils.isNotEmpty(ccEmail)) {
                msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
            }
        } catch (MessagingException e) {
            logger.warn("Incorrect email address (from, to or cc) specified");
            return false;
        }


        try {
            msg.setSubject(subject);
            msg.setText(message, "utf-8", "html");
            msg.setSentDate(new Date());
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            return false;
        }

        SMTPTransport t = null;
        try {
            t = (SMTPTransport) session.getTransport(protocol);
        } catch (NoSuchProviderException e) {
            logger.error("Specified email provider '{}' is not exist", protocol);
        }

        try {
            t.connect(host, username, password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            return false;
        }

        return true;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getHost() {
        return host;
    }


    public void setHost(String host) {
        this.host = host;
    }


    public String getPort() {
        return port;
    }


    public void setPort(String port) {
        this.port = port;
    }


    public String getProtocol() {
        return protocol;
    }


    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }


    public String getSocketFactoryClass() {
        return socketFactoryClass;
    }


    public void setSocketFactoryClass(String socketFactoryClass) {
        this.socketFactoryClass = socketFactoryClass;
    }


    public String getSocketFactoryPort() {
        return socketFactoryPort;
    }


    public void setSocketFactoryPort(String socketFactoryPort) {
        this.socketFactoryPort = socketFactoryPort;
    }


    public String getFallback() {
        return fallback;
    }


    public void setFallback(String fallback) {
        this.fallback = fallback;
    }


    public String getAuth() {
        return auth;
    }


    public void setAuth(String auth) {
        this.auth = auth;
    }


    public String getQuitwait() {
        return quitwait;
    }


    public void setQuitwait(String quitwait) {
        this.quitwait = quitwait;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getFrom() {
        return from;
    }


    public void setFrom(String from) {
        this.from = from;
    }
}
