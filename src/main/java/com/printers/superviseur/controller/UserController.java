package com.printers.superviseur.controller;

import com.printers.superviseur.Json;
import com.printers.superviseur.entity.ProfilesEntity;
import com.printers.superviseur.entity.UtilisateursEntity;
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
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = true)
    UserRepository User;

    @Autowired(required = true)
    ProfileRepository Profile;

    @RequestMapping("/users")
    public String user(Model model) {
        model.addAttribute("profiles", Profile.findAll());
        model.addAttribute("users", User.findByEtat());
        //model.addAttribute("test", me.gethtml("172.16.103.193"));
        return "users";
    }

    @RequestMapping(value = "/new_user", method = RequestMethod.POST)
    @ResponseBody
    public Integer new_user(@RequestBody Map<String, String> json) {

        if (User.findByEmail(json.get("email")) > 0) {
            return 0;
        } else {
            //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UtilisateursEntity utilisateurs = new UtilisateursEntity();
            ProfilesEntity profile = (Profile.findActivatedProfileEntity(json.get("profile")));
            utilisateurs.setProfilesByIdprofile(profile);
            utilisateurs.setNomuser(json.get("nom"));
            utilisateurs.setPrenomuser(json.get("prenom"));
            utilisateurs.setPassuser(passwordEncoder.encode(json.get("password1")));
            utilisateurs.setMailuser(json.get("email"));
            utilisateurs.setTeluser(json.get("telephone"));
            utilisateurs.setEtat(1);
            User.save(utilisateurs);
            return 1;
        }
    }

    @RequestMapping(value = "/show_user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> show_user(@RequestBody Map<String, String> json) {
        System.out.println(json.get("id"));
        List<UtilisateursEntity> entityList = User.findById(Long.parseLong(json.get("id")));

        HashMap<String, String> entities = new HashMap();

        entities.put("id", Long.toString(entityList.get(0).getIduser()));
        entities.put("nom", entityList.get(0).getNomuser());
        entities.put("prenom", entityList.get(0).getPrenomuser());
        entities.put("password", entityList.get(0).getPassuser());
        entities.put("email", entityList.get(0).getMailuser());
        entities.put("telephone", entityList.get(0).getTeluser());
        entities.put("profile", entityList.get(0).getProfilesByIdprofile().getDesprofile());

        return entities;
    }


    @RequestMapping(value = "/edit_user", method = RequestMethod.POST)
    @ResponseBody
    public Integer edit_user(@RequestBody Map<String, String> json) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UtilisateursEntity utilisateurs = User.findByIdEntity(Long.parseLong(json.get("id")));
        ProfilesEntity profile = (Profile.findActivatedProfileEntity(json.get("profile")));

        if (User.CountfindByEmailAndId(Long.parseUnsignedLong(json.get("id")), json.get("email")) > 0) {
            return 0;
        } else if (!json.get("password1").equals(json.get("password2"))) {
            return 2;
        } else if (json.get("password1") == "" || json.get("password2") == "") {
            utilisateurs.setProfilesByIdprofile(profile);
            utilisateurs.setNomuser(json.get("nom"));
            utilisateurs.setPrenomuser(json.get("prenom"));
            utilisateurs.setMailuser(json.get("email"));
            utilisateurs.setTeluser(json.get("telephone"));
            User.save(utilisateurs);
            return 1;
        } else {
            utilisateurs.setProfilesByIdprofile(profile);
            utilisateurs.setNomuser(json.get("nom"));
            utilisateurs.setPrenomuser(json.get("prenom"));
            utilisateurs.setPassuser(passwordEncoder.encode(json.get("password1")));
            utilisateurs.setMailuser(json.get("email"));
            utilisateurs.setTeluser(json.get("telephone"));
            User.save(utilisateurs);
            return 1;
        }
    }

    @RequestMapping(value = "/del_user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Integer del_user(@RequestBody Map<String, String> json) {

        UtilisateursEntity entityList = User.findByIdEntity(Long.parseLong(json.get("id")));

        if (entityList.getMailuser().length() > 0) {
            entityList.setEtat(0);
            User.save(entityList);
            return 1;
        } else {
            return 0;
        }

    }
}
