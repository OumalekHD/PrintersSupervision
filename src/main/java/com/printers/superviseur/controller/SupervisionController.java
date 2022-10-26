package com.printers.superviseur.controller;

import com.printers.superviseur.Json;
import com.printers.superviseur.NotificationService;
import com.printers.superviseur.entity.MaterielsEntity;
import com.printers.superviseur.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.*;
import java.util.*;

@Controller
public class SupervisionController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = true)
    UserRepository User;

    @Autowired(required = true)
    MaterielRepository Imprimante;

    @Autowired(required = true)
    LigneRepository Ligne;

    @Autowired(required = true)
    StationRepository Station;

    @Autowired(required = true)
    ChantierRepository Chantier;

    @Autowired(required = true)
    PrinterRepository Printer;

    @Autowired(required = true)
    InfoRepository Info;

    @Autowired
    private NotificationService notificationService;


    @RequestMapping("/supervision")
    public String supervision(Model model) {
        //Json me = new Json();
        model.addAttribute("lignes", Ligne.findAll());
        //model.addAttribute("test", me.gethtml("172.16.103.193"));
        return "supervision";
    }

    @RequestMapping("/supprinters")
    public String supprinters(Model model) {
        //Json me = new Json();
        model.addAttribute("printers", Printer.SupPrintersAll());
        model.addAttribute("actifprinters", Printer.findActif());
        model.addAttribute("findDefPrinthead", Printer.findDefPrinthead());
        model.addAttribute("PingPrinters", Printer.PingPrinters());
        model.addAttribute("NumberOfRows", Info.NumberOfRows());
        //model.addAttribute("test", me.gethtml("172.16.103.193"));
        return "supprinters";
    }


    @RequestMapping(value = "/get_printers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TreeMap<Integer, Map<String, String>> get_printers(@RequestBody Map<String, String> json) {

        List<MaterielsEntity> entityList = Imprimante.findAllPrintersByLigne(Integer.parseInt(json.get("id")));
        TreeMap<Integer, Map<String, String>> entities = new TreeMap();

        for (int i = 0; i < entityList.size(); i++) {
            TreeMap<String, String> list = new TreeMap();
            list.put("type", entityList.get(i).getTypemateriel());
            list.put("ip", entityList.get(i).getIpmateriel());
            entities.put(i, list);
        }
        return entities;
    }


    @RequestMapping(value = "/get_Json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> get_Json(@RequestBody Map<String, String> json) {

        Json me = new Json();
        return me.gethtml(json.get("ip"), "PD43");
    }

    @RequestMapping(value = "/check_ping", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Integer check_ping(@RequestBody Map<String, String> json) {
        int state = 0;
        InetAddress geek = null;
        try {
            geek = InetAddress.getByName(json.get("ip"));
            if (geek.isReachable(2000))
                state = 1;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return state;
    }

    @RequestMapping(value = "/reboot_printer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Integer reboot_printer(@RequestBody Map<String, String> json) {
        int state = 0;
        HttpURLConnection connection = null;
        try {
            if (json.get("model").toUpperCase().equals("PD43") || json.get("model").toUpperCase().equals("PM43") || json.get("model").toUpperCase().equals("PX6I") || json.get("model").toUpperCase().equals("PC43T")) {
                System.out.println(json.get("model"));
                connection = (HttpURLConnection) new URL("http://" + json.get("id") + "/service/processreboot.lua?command=reboot").openConnection();
                connection.setConnectTimeout(5 * 1000);
                connection.disconnect();
                state = 1;
                //notificationService.SendEmailAllFaultyDots();
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return state;
    }

}
