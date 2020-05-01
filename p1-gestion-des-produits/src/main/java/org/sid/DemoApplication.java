package org.sid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private ProduitRepository produitRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception
    {
        /*produitRepository.save(new Produit("Camion", 45, 45));
        produitRepository.save(new Produit("voiture", 30, 45));
        produitRepository.save(new Produit("avion", 51, 7));
        produitRepository.save(new Produit("vello", 51, 7));
        produitRepository.save(new Produit("train", 51, 7));
        produitRepository.save(new Produit("jsd", 51, 7));
        produitRepository.save(new Produit("sodos", 51, 7));
        produitRepository.save(new Produit("owooes", 51, 7));
        produitRepository.save(new Produit("teue", 51, 7));
        produitRepository.save(new Produit("teue", 51, 7));
        produitRepository.save(new Produit("teue", 51, 7));
        produitRepository.save(new Produit("teue", 51, 7));
        produitRepository.save(new Produit("teue", 51, 7));
        produitRepository.save(new Produit("teue", 51, 7));
        produitRepository.save(new Produit("teue", 51, 7));
        produitRepository.save(new Produit("teue", 51, 7));
        produitRepository.save(new Produit("teue", 51, 7));
        produitRepository.save(new Produit("teue", 51, 7));*/
        produitRepository.findAll().forEach(produit -> System.out.println(produit.toString()));
    }
}