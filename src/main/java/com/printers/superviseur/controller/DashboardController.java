package com.printers.superviseur.controller;

import com.printers.superviseur.entity.InfosEntity;
import com.printers.superviseur.entity.MenusEntity;
import com.printers.superviseur.entity.UtilisateursEntity;
import com.printers.superviseur.repository.InfoRepository;
import com.printers.superviseur.repository.MenuRepository;
import com.printers.superviseur.repository.PrinterRepository;
import com.printers.superviseur.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    UserRepository User;

    @Autowired
    MenuRepository Menu;

    @Autowired
    InfoRepository Info;

    @Autowired
    PrinterRepository Printer;

    @RequestMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<UtilisateursEntity> data = User.findByUsername(auth.getName());

        if (data.size() > 0) {
            List<MenusEntity> cons = Menu.findById(data.get(0).getIdprofile());
            model.addAttribute("loginNomPrenom", data.get(0).getPrenomuser() + " " + data.get(0).getNomuser());
            model.addAttribute("loginUsername", data.get(0).getMailuser());
            model.addAttribute("userprofile", cons);
            return "index";
        } else {
            return "/login?error";
        }
    }

    @RequestMapping("/dashboard")
    public String user(Model model) {
        model.addAttribute("ChartGetActifPrintersByDay", Info.ChartGetActifPrintersByDay());
        model.addAttribute("ChartGetNbPrintersByType", Info.ChartGetNbPrintersByType());
        model.addAttribute("dashboardPrinters", Printer.dashboardPrinters());
        return "dashboard";
    }

    @RequestMapping(value = "/chartgetfaultystats", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<Timestamp, String> chartgetfaultystats(@RequestBody Map<String, String> json) {
        List<InfosEntity> ent = Info.chartgetfaultystats(json.get("serial"));

        HashMap<Timestamp, String> entities = new HashMap();

        for (int i = 0; i < ent.size(); i++) {
            entities.put(ent.get(i).getDateanalyse(), ent.get(i).getFaultydotsprinthead());
        }

        return entities;
    }

}
