package com.derteuffel;




import com.derteuffel.controller.FileStorageProperties;
import com.sun.mail.smtp.SMTPTransport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class CollectApiApplication  {

	public static void main(String[] args) {
		SpringApplication.run(CollectApiApplication.class, args);

	}

}
