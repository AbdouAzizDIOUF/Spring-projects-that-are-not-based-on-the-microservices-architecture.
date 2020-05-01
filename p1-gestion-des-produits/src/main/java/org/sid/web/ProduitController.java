package org.sid.web;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.Valid;


@Controller
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;


    @GetMapping("/")
    public String defaut()
    {
        return "redirect:/user/index";
    }

    //@RequestMapping(value = "/index", method = RequestMethod.GET)
    @GetMapping("/user/index")
    public String chercher(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam(name="motCle", defaultValue="") String motCle)
    {
        Page<Produit> produitList = produitRepository.findByDesignationContains(motCle, PageRequest.of(page, 5));
        model.addAttribute("produits", produitList.getContent());
        model.addAttribute("pages", new int[produitList.getTotalPages()]);
        model.addAttribute("currentPage", page).addAttribute("motCle", motCle);

        return "produit";
    }


    @GetMapping("/admin/formProduit")
    public String form(Model model)
    {
        model.addAttribute("produit", new Produit());

        return "FormProduit";
    }

    @PostMapping("/admin/save")
    public String save(Model model,  @Valid Produit produit, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) return "FormProduit";
        produitRepository.save(produit);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/edit")
    public String edit(Model model, Long id){
        Produit produit = produitRepository.findById(id).get();
        model.addAttribute("produit", produit);

        return "EditProduit";
    }

    @GetMapping("/admin/delete")
    public String delete(Long id, int page, String motCle)
    {
        produitRepository.deleteById(id);

        return "redirect:/user/index?page="+page+"&motCle="+motCle;
    }

    @GetMapping("/404")
    public String denied()
    {
        return "404";
    }

}
