package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TesteConsumidorTopicEstoque {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//Inicializando
		InitialContext context = new InitialContext();
		
		//lookup eu pego o jndi
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.setClientID("estoque");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		
		Topic topico = (Topic) context.lookup("loja");
		
		MessageConsumer consumer =  session.createDurableSubscriber(topico, "assinatura");
		// Fim
		// Uma mensagem Message message = (Message) consumer.receive();
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				
				TextMessage txtMessage = (TextMessage) message;
				try {
					System.out.println(txtMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
   
			
		});
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
