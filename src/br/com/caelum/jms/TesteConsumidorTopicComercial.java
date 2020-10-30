package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;

import br.com.caelum.modelo.Pedido;


public class TesteConsumidorTopicComercial {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//Inicializando
		InitialContext context = new InitialContext();
		
		//lookup eu pego o jndi
//		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		ActiveMQConnectionFactory factory = (ActiveMQConnectionFactory) context.lookup("ConnectionFactory");
		factory.setTrustAllPackages(true);

		Connection connection = factory.createConnection();
		connection.setClientID("comercial");
		
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		
		Topic topico = (Topic) context.lookup("loja");
		
		MessageConsumer consumer =  session.createDurableSubscriber(topico, "assinatura");
		// Fim
		// Uma mensagem Message message = (Message) consumer.receive();
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {

			    ObjectMessage objectMessage = (ObjectMessage)message;

			    try {
			        Pedido pedido = (Pedido) objectMessage.getObject();
			        System.out.println(pedido.getCodigo());
			    } catch (JMSException e) {
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
