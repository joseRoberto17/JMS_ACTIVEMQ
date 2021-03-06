package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.Message;

public class TesteConsumidor {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//Inicializando
		InitialContext context = new InitialContext();
		
		//lookup eu pego o jndi
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer =  session.createConsumer(fila );
		// Fim
		Message message = (Message) consumer.receive();
		
		System.out.println("Recendo a msg: " + message);
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
