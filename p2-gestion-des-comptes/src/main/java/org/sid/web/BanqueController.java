package org.sid.web;

import org.sid.entities.Compte;
import org.sid.entities.Operation;
import org.sid.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BanqueController {

    @Autowired
    private IBanqueMetier iBanqueMetier;

    @GetMapping("/")
    public String def(){ return "comptes"; }

    @GetMapping("/operations")
    public String index()
    {
        return "comptes";
    }

    @GetMapping("/consulterCompte")
    public String consulter(Model model, String codeCompte, @RequestParam(name ="page", defaultValue="0") int page)
    {
        model.addAttribute("codeCompte", codeCompte);
        try{
            Compte cp = iBanqueMetier.consulterCompte(codeCompte);
            Page<Operation> operations = iBanqueMetier.listeOperation(codeCompte, page, 4);
            model.addAttribute("listOperations", operations.getContent());
            model.addAttribute("compte", cp);
            model.addAttribute("codeCompte", codeCompte);
            model.addAttribute("pages", new int[operations.getTotalPages()]);
            model.addAttribute("currentPage", page);
        }catch (Exception e){
            model.addAttribute("exception", e.getMessage());
        }
        return "comptes";
    }


    @PostMapping(value="/saveOperation")
    public String saveOperation(Model model, String typeOperation, String codeCompte,double montant, String codeCompte2) {   //cette methode retourne une vue tous simplement

        try {
            if(typeOperation.equals("VERS")) {
                iBanqueMetier.verser(codeCompte,montant);
            } else if(typeOperation.equals("RETR")) {

                iBanqueMetier.retirer(codeCompte,montant);
            } else  {

                iBanqueMetier.virement(codeCompte,codeCompte2,montant);
            }
        }catch (Exception e) {
            model.addAttribute("error",e);
            return "redirect:/consulterCompte?codeCompte="+codeCompte+"&error="+e.getMessage();
        }


        return "redirect:/consulterCompte?codeCompte="+codeCompte;
    }
}
