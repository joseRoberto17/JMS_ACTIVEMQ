package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TesteProdutorTopico {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//Inicializando
		InitialContext context = new InitialContext();
		
		//lookup eu pego o jndi
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		
		Destination topico = (Destination) context.lookup("loja");
		
		MessageProducer producer = session.createProducer(topico);
		
		for(int i = 0; i < 2; i++) {
			Message msg = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
			producer.send(msg);
		}
//		Message msg = session.createTextMessage("<pedido><id>1</id></pedido>");
//		producer.send(msg);
		
		
		//new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
