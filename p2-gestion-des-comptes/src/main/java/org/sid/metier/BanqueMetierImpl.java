package org.sid.metier;

import org.sid.dao.CompteRepository;
import org.sid.dao.OperationRepository;
import org.sid.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {

    private final CompteRepository compteRepository;
    private final OperationRepository operationRepository;

    public BanqueMetierImpl(CompteRepository compteRepository, OperationRepository operationRepository) {
        this.compteRepository = compteRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public Compte consulterCompte(String codeCompte)
    {
        return compteRepository.findById(codeCompte).get();
    }

    @Override
    public void verser(String codeCompte, double montant) {
        Compte cp = consulterCompte(codeCompte);
        Versement v = new Versement(new Date(), montant, cp);
        operationRepository.save(v);
        cp.setSolde(cp.getSolde()+montant);
        compteRepository.save(cp);
    }

    @Override
    public void retirer(String codeCompte, double montant)
    {
        Compte cp = consulterCompte(codeCompte);
        double decouverte = 0;
        if (cp instanceof CompteCourant) {
            decouverte = ((CompteCourant ) cp).getDecouvert();
        }
        if (cp.getSolde() + decouverte < montant) {
            throw new RuntimeException("insufficient balance");
        }
        Retrait r = new Retrait(new Date(), montant, cp);
        operationRepository.save(r);
        cp.setSolde(cp.getSolde()-montant);
        compteRepository.save(cp);
    }

    @Override
    public void virement(String codeCompte1, String codeCompte2, double montant)
    {
        if (codeCompte1.equals(codeCompte2)) {
            throw new RuntimeException("Virement impossible "+codeCompte1+" vers "+codeCompte2);
        }
        retirer(codeCompte1,montant);
        verser(codeCompte2, montant);
    }

    @Override
    public Page<Operation> listeOperation(String codeCompte, int page, int size)
    {
        return operationRepository.listeOperation(codeCompte, PageRequest.of(page, size));
    }
}
