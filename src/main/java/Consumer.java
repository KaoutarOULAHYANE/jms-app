import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

public class Consumer {
    public static void main(String[] args) throws JMSException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Veuillez saisir votre code : ");
        String code = scanner.next();
        /*Création d'une connection*/
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://0.0.0.0:61616");
        Connection connection = connectionFactory.createConnection();
        /*Démarrage de la connection*/
        connection.start();
        /*Création d'une sessio*/
        /*false : la session n'est pas transactionelle*/
        /*Pas besoin de faire un commit à la fin de l'envoi du message*/
        /*Session.AUTO_ACKNOWLEDGE : le mode d'accusé de réception*/
        /*Le consumateur pour récupérer ses messages,
        il va envoyer au Broker un accusé de réception*/
        Session session =
                connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        /*Création de la file d'attente*/
        /*Destination destination =
                session.createQueue("enset.queue");*/
        /*La file d'attente de type Queue, seul un consommateur qui peut récupérer le message*/
        /*Le Broker utiliser le principe de RoundRobin (tour des roles)*/
        /*Utile pour déstribuer les taches*/
        Destination destination =
                session.createTopic("enset.topic");
        /*La file d'attente de type TOPIC, tous les consommateurs peuvent récupérer le message*/
        /*Création d'un consommateur*/
        /*Pour filter les messages à récupérer, il faut utiliser un code*/
        /*Ne récupérer que les messages ayant une propriétés 'code' dont la valeur est ... */
        /*Réception du message suite à une condition*/
        MessageConsumer consumer
                = session.createConsumer(destination, "code='" + code + "'");
        /*Création d'un Listener JMS*/
        /*pour attendre la réception du message*/
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("Producer : " + textMessage.getText());
                    } catch (JMSException jmsException) {
                        jmsException.printStackTrace();
                    }
                }
            }
        });
    }
}
