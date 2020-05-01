package org.sid;

import org.sid.dao.ClientRepository;
import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.*;
import org.sid.metier.IBanqueMetier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class MamabanquejeeApplication  implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final CompteRepository compteRepository;
    private final OperationRepository operationRepository;
    private final IBanqueMetier iBanqueMetier;

    public MamabanquejeeApplication(ClientRepository clientRepository, CompteRepository compteRepository, OperationRepository operationRepository, IBanqueMetier iBanqueMetier) {
        this.clientRepository = clientRepository;
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
        this.iBanqueMetier = iBanqueMetier;
    }

    public static void main(String[] args) {
        SpringApplication.run(MamabanquejeeApplication.class, args);
        //ClientRepository clientRepository = ctx.getBean(ClientRepository.class);
        //clientRepository.save(new Client("DIOUF", "abdouazizdiouf75@gmail.com"));
    }

    @Override
    public void run(String... args) throws Exception {
    Client c1 = clientRepository.save(new Client("DIOUF", "abdouazizdiouf75@gmail.com"));
    Client c2 = clientRepository.save(new Client("SOW", "mameousmane75@gmail.com"));
    Compte cp1 = compteRepository.save(new CompteCourant("c1", new Date(), 90000, c1, 7000));
    Compte cp2 = compteRepository.save(new CompteEpargne("c2", new Date(), 60000, c2, 5.40));
    operationRepository.save(new Versement(new Date(), 9000, cp1));
    operationRepository.save(new Versement(new Date(), 9000, cp1));
    operationRepository.save(new Versement(new Date(), 9000, cp1));
    operationRepository.save(new Retrait(new Date(), 4400, cp1));

    operationRepository.save(new Versement(new Date(), 9000, cp2));
    operationRepository.save(new Versement(new Date(), 9000, cp2));
    operationRepository.save(new Versement(new Date(), 9000, cp2));
    operationRepository.save(new Retrait(new Date(), 4400, cp2));

    //clientRepository.findAll().forEach(client -> System.out.println(client.toString()));
    //operationRepository.findAll().forEach(operation -> System.out.println(operation.toString()));

    iBanqueMetier.verser("c1" ,100000001);

    clientRepository.findAll().forEach(client -> System.out.println(client.toString()));
    }
}
