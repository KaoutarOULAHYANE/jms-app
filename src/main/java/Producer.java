import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

public class Producer {
    public static void main(String[] args) throws JMSException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Veuillez saisir le code du consommateur: ");
        String code = scanner.next();
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        /*Création d'une connection*/
        Connection connection = connectionFactory.createConnection();
        /*Démarrage de la connection*/
        connection.start();
        /*Création d'une sessio*/
        /*false : la session n'est pas transactionelle*/
        /*Pas besoin de faire un commit à la fin de l'envoi du message*/
        /*Session.AUTO_ACKNOWLEDGE : le mode d'accusé de réception*/
            /*Le producteur pour récupérer ses messages,
            il va envoyer au Broker un accusé de réception*/
        Session session =
                connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        /*Création de la file d'attente*/
            /*Destination destination =
                    session.createQueue("enset.queue");*/
        /*La file d'attente de type Queue, seul un consumer qui peut récupérer le message*/
        /*Le Broker utiliser le principe de RoundRobin (tour des roles)*/
        /*Utile pour déstribuer les taches*/
        Destination destination =
                session.createTopic("enset.topic");
        /*Création d'un producteur*/
        MessageProducer producer
                = session.createProducer(destination);
        /*Définition du mode de persistence*/
        /*NON_PERSISTENT : le message à envoyer n'est pas persistent*/
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        /*Création du message*/
        TextMessage textMessage = session.createTextMessage();
        textMessage.setStringProperty("code", code);
        textMessage.setText("Hello Consumer");
        /*Envoie du message*/
        producer.send(textMessage);
        /*Fermeture de la connection*/
        session.close();
        connection.close();
    }
}
