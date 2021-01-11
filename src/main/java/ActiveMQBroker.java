import org.apache.activemq.broker.BrokerService;

public class ActiveMQBroker {
    public static void main(String[] args) {
        try {
            /*Configuration du Broker*/
            BrokerService brokerService = new BrokerService();
            brokerService.setPersistent(false);
            /*0.0.0.0 : Le Broker accepte la connection de n'importe quel @IP*/
            brokerService.addConnector("tcp://0.0.0.0:61616");
            /*Démarrage du Brocker*/
            brokerService.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
