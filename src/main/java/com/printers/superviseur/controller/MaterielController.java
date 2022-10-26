package com.printers.superviseur.controller;

import com.printers.superviseur.Json;
import com.printers.superviseur.entity.LignesEntity;
import com.printers.superviseur.entity.MaterielsEntity;
import com.printers.superviseur.entity.ProfilesEntity;
import com.printers.superviseur.entity.UtilisateursEntity;
import com.printers.superviseur.repository.LigneRepository;
import com.printers.superviseur.repository.MaterielRepository;
import com.printers.superviseur.repository.ProfileRepository;
import com.printers.superviseur.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MaterielController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = true)
    UserRepository User;

    @Autowired(required = true)
    MaterielRepository Imprimante;

    @Autowired(required = true)
    LigneRepository Ligne;

    @RequestMapping("/imprimantes")
    public String user(Model model) {
        //Json me = new Json();
        model.addAttribute("printers", Imprimante.findAll());
        model.addAttribute("lignes", Ligne.findAll());
        //model.addAttribute("test", me.gethtml("172.16.103.193"));
        return "imprimantes";
    }

    @RequestMapping(value = "/new_printer", method = RequestMethod.POST)
    @ResponseBody
    public Integer new_printer(@RequestBody Map<String, String> json) {

        if (Imprimante.countfindByIp(json.get("ip")) > 0) {
            return 0;
        } else {
            //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MaterielsEntity imprimante = new MaterielsEntity();
            LignesEntity ligne = (Ligne.findActivatedLigneEntity(Long.parseLong(json.get("ligne"))));
            imprimante.setLignesByIdligne(ligne);
            imprimante.setTypemateriel(json.get("type"));
            imprimante.setMarquemateriel(json.get("marque"));
            imprimante.setModelmateriel(json.get("modele"));
            imprimante.setIpmateriel(json.get("ip"));
            imprimante.setUsermateriel(json.get("user"));
            imprimante.setPassmateriel(json.get("password"));
            imprimante.setDescmateriel(json.get("description"));
            imprimante.setEtatmateriel(1);
            Imprimante.save(imprimante);
            return 1;
        }
    }

    @RequestMapping(value = "/show_printer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> show_printer(@RequestBody Map<String, String> json) {
        List<MaterielsEntity> entityList = Imprimante.findById(Long.parseLong(json.get("id")));

        HashMap<String, String> entities = new HashMap();

        if (entityList.size() > 0) {
            entities.put("id", Long.toString(entityList.get(0).getIdmateriel()));
            entities.put("ligne", (String.valueOf(entityList.get(0).getIdligne())));
            entities.put("type", entityList.get(0).getTypemateriel());
            entities.put("modele", entityList.get(0).getModelmateriel());
            entities.put("marque", entityList.get(0).getMarquemateriel());
            entities.put("user", entityList.get(0).getUsermateriel());
            entities.put("password", entityList.get(0).getPassmateriel());
            entities.put("description", entityList.get(0).getDescmateriel());
            entities.put("ip", entityList.get(0).getIpmateriel());

        }
        return entities;
    }


    @RequestMapping(value = "/edit_printer", method = RequestMethod.POST)
    @ResponseBody
    public Integer edit_printer(@RequestBody Map<String, String> json) {

        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MaterielsEntity imprimante = Imprimante.findByIdEntity(Long.parseLong(json.get("id")));
        LignesEntity ligne = Ligne.findActivatedLigneEntity(Long.parseLong(json.get("ligne")));

        if (Imprimante.CountfindByIpAndId(Long.parseUnsignedLong(json.get("id")), json.get("email")) > 0) {
            return 0;
        } else {
            imprimante.setLignesByIdligne(ligne);
            imprimante.setModelmateriel(json.get("modele"));
            imprimante.setTypemateriel(json.get("type"));
            imprimante.setMarquemateriel(json.get("marque"));
            imprimante.setIpmateriel(json.get("ip"));
            imprimante.setUsermateriel(json.get("user"));
            imprimante.setPassmateriel(json.get("password"));
            imprimante.setDescmateriel(json.get("description"));
            Imprimante.save(imprimante);
            return 1;
        }
    }

    @RequestMapping(value = "/del_printer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Integer del_printer(@RequestBody Map<String, String> json) {

        MaterielsEntity entityList = Imprimante.findByIdEntity(Long.parseLong(json.get("id")));

        if (entityList.getIpmateriel().length() > 0) {
            entityList.setEtatmateriel(0);
            Imprimante.save(entityList);
            return 1;
        } else {
            return 0;
        }

    }
}
