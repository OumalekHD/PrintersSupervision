package com.printers.superviseur.controller;

import com.printers.superviseur.entity.MenusEntity;
import com.printers.superviseur.entity.ProfilesEntity;
import com.printers.superviseur.entity.UtilisateursEntity;
import com.printers.superviseur.repository.MenuRepository;
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

import java.util.*;

@Controller
public class ProfileController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = true)
    UserRepository User;

    @Autowired(required = true)
    ProfileRepository Profile;

    @Autowired(required = true)
    MenuRepository Menu;

    @RequestMapping("/profiles")
    public String profile(Model model) {
        model.addAttribute("profiles", Profile.findAllbyCount());
        model.addAttribute("menus", Menu.findAllMenus());
        return "profiles";
    }

    @RequestMapping(value = "/new_profile", method = RequestMethod.POST)
    @ResponseBody
    public Integer new_profile(@RequestBody List<Map<String, String>> json) {

        if (Profile.findProfileByDes(json.get(0).get("Profile")) > 0) {
            return 0;
        } else {
            ProfilesEntity profiles = new ProfilesEntity();
            profiles.setDesprofile(json.get(0).get("Profile"));
            profiles.setEtat(1);
            Profile.save(profiles);

            for (int i = 0; i < json.size(); i++) {
                MenusEntity menus = new MenusEntity();
                ProfilesEntity profile = Profile.findId(profiles.getIdprofile());
                menus.setProfilesByIdprofile(profile);
                menus.setDesmenu(json.get(i).get("Menu"));
                menus.setAccesmenu(Integer.parseInt(json.get(i).get("Consultation")));
                menus.setAddmenu(Integer.parseInt(json.get(i).get("Ajout")));
                menus.setEditmenu(Integer.parseInt(json.get(i).get("Modification")));
                menus.setDeletemenu(Integer.parseInt(json.get(i).get("Delete")));
                menus.setExportmenu(Integer.parseInt(json.get(i).get("Export")));
                Menu.save(menus);

            }
            return 1;
        }
    }

    @RequestMapping(value = "/show_profile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TreeMap<String, Map<String, String>> show_profile(@RequestBody Map<String, String> json) {
        List<MenusEntity> entityList = Menu.findById(Integer.parseInt(json.get("id")));

        TreeMap<String, Map<String, String>> entities = new TreeMap();

        for (int i = 0; i < entityList.size(); i++) {
            TreeMap<String, String> list = new TreeMap();
            list.put("AProfile", entityList.get(i).getProfilesByIdprofile().getDesprofile());
            list.put("BMenu", entityList.get(i).getDesmenu());
            list.put("Consultation", String.valueOf(entityList.get(i).getAccesmenu()));
            list.put("DAjout", String.valueOf(entityList.get(i).getAddmenu()));
            list.put("EModification", String.valueOf(entityList.get(i).getEditmenu()));
            list.put("FSuppression", String.valueOf(entityList.get(i).getDeletemenu()));
            list.put("GExportation", String.valueOf(entityList.get(i).getExportmenu()));
            entities.put(entityList.get(i).getDesmenu(), list);
        }
        System.out.println(entities);
        return entities;
    }

    @RequestMapping(value = "/edit_profile", method = RequestMethod.POST)
    @ResponseBody
    public Integer edit_user(@RequestBody List<Map<String, String>> json) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (json.size() > 0) {
            ProfilesEntity profile = Profile.findId(Long.parseLong(json.get(0).get("id")));


            if (Profile.CountfindByProfile(Long.parseUnsignedLong(json.get(0).get("id")), json.get(0).get("Profile")) > 0) {
                return 0;
            } else {
                profile.setDesprofile(json.get(0).get("Profile"));
                Profile.save(profile);
                Menu.deletebyprofile(Integer.parseInt(json.get(0).get("id")));

                for (int i = 0; json.size() > i; i++) {
                    MenusEntity menu = new MenusEntity();
                    menu.setProfilesByIdprofile(profile);
                    menu.setDesmenu(json.get(i).get("Menu"));
                    menu.setAccesmenu(Integer.parseInt(json.get(i).get("Consultation")));
                    menu.setAddmenu(Integer.parseInt(json.get(i).get("Ajout")));
                    menu.setEditmenu(Integer.parseInt(json.get(i).get("Modification")));
                    menu.setDeletemenu(Integer.parseInt(json.get(i).get("Suppression")));
                    menu.setExportmenu(Integer.parseInt(json.get(i).get("Exportation")));
                    Menu.save(menu);
                }
                return 1;
            }
        } else {
            return 2;
        }
    }


    @RequestMapping(value = "/del_profile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Integer del_user(@RequestBody Map<String, String> json) {

        ProfilesEntity entityList = Profile.findId(Long.parseLong(json.get("id")));

        if (entityList.getDesprofile().length() > 0) {
            entityList.setEtat(0);
            Profile.save(entityList);
            return 1;
        } else {
            return 0;
        }

    }

}
