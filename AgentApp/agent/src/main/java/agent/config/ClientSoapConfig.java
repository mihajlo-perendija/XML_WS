package agent.config;

import agent.soap.VehicleClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class ClientSoapConfig {


        @Bean
        public Jaxb2Marshaller marshaller() {
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
            marshaller.setContextPath("agent.soap.gen");
            return marshaller;
        }
        @Bean
        public VehicleClient countryClient(Jaxb2Marshaller marshaller) {
            VehicleClient client = new VehicleClient();
            client.setDefaultUri("http://localhost:8084/vehicle/ws");
            client.setMarshaller(marshaller);
            client.setUnmarshaller(marshaller);
            return client;
        }

}