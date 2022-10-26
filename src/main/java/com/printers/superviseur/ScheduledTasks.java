package com.printers.superviseur;

import com.printers.superviseur.entity.InfosEntity;
import com.printers.superviseur.entity.MaterielsEntity;
import com.printers.superviseur.entity.PrintersEntity;
import com.printers.superviseur.entity.PrintersscansEntity;
import com.printers.superviseur.repository.InfoRepository;
import com.printers.superviseur.repository.MaterielRepository;
import com.printers.superviseur.repository.PrinterRepository;
import com.printers.superviseur.repository.PrintersscanRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class ScheduledTasks {

    public InfosEntity info;
    public PrintersEntity printer;
    public PrintersEntity pr;

    @Autowired
    NotificationService notificationService;

    @Autowired
    MaterielRepository Materiel;

    @Autowired
    InfoRepository Info;

    @Autowired
    PrintersscanRepository PrinterScan;

    @Autowired
    PrinterRepository Printers;

    @Async
    @Scheduled(fixedRate = 300000)
    public void getprintersinfo() {
        System.out.println("ANOTHER TEST");
        Timestamp MyTime = new Timestamp(System.currentTimeMillis());
        PrintersscansEntity printerscan = new PrintersscansEntity();
        printerscan.setDatescan(MyTime);
        PrinterScan.save(printerscan);

        PrintersscansEntity Updt = PrinterScan.findDate(MyTime);
        if (Updt.getDatescan() != null) {
            List<MaterielsEntity> listmateriels = Materiel.findByActif(1);
            for (MaterielsEntity v : listmateriels) {
                info = new InfosEntity();
                printer = new PrintersEntity();
                pr = new PrintersEntity();
                Threaded t = new Threaded(v.getIpmateriel(), v.getModelmateriel(), info, Info, printer, Printers, pr, Updt);
                t.start();
            }
        }
    }

    @Async
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendmail() {
        notificationService.SendEmailAllFaultyDots();
    }


}

class Threaded extends Thread {

    String ipmateriel;
    String typemateriel;
    InfosEntity info;
    InfoRepository Info;
    PrintersEntity printer;
    PrinterRepository Printers;
    PrintersEntity pr;
    PrintersscansEntity printerscan;


    public Threaded(String ipmateriel, String typemateriel, InfosEntity info, InfoRepository Info, PrintersEntity printer, PrinterRepository Printers, PrintersEntity pr, PrintersscansEntity printerscan) {
        this.ipmateriel = ipmateriel;
        this.Info = Info;
        this.info = info;
        this.Printers = Printers;
        this.printer = printer;
        this.pr = pr;
        this.printerscan = printerscan;
        this.typemateriel = typemateriel;
    }

    public void run() {

        Json JS = new Json();
        ResponseEntity<?> ME = JS.gethtml(ipmateriel, typemateriel);
        JSONObject myObject = new JSONObject(ME);

        if (myObject.getJSONObject("body").has("ipadresse") == true) {

            //ADD to INFOS (Historique scans) ******************************
            info.setIpadresse(myObject.getJSONObject("body").getString("ipadresse"));
            info.setCpuusage(myObject.getJSONObject("body").getString("cpuusage"));
            info.setFirmwareversion(myObject.getJSONObject("body").getString("firmwareversion"));
            info.setFaultydotsprinthead(myObject.getJSONObject("body").getString("faultydots"));
            info.setFreememory(myObject.getJSONObject("body").getString("freestorage"));
            info.setFreeram(myObject.getJSONObject("body").getString("freeram"));
            info.setMacadresse(myObject.getJSONObject("body").getString("ethernetmac"));
            info.setMaxprintheadtemp(myObject.getJSONObject("body").getString("maximumtemperature"));
            info.setMinprintheadtemp(myObject.getJSONObject("body").getString("minimumtemperature"));
            //info.setNumberofdots(myObject.getJSONObject("body").getString("cpuusage"));
            info.setPartnumber(myObject.getJSONObject("body").getString("configurationnumber"));
            info.setPrintedlabel(myObject.getJSONObject("body").getString("printedlabels"));
            info.setSerialnumber(myObject.getJSONObject("body").getString("serialnumber"));
            info.setPrintheadtemp(myObject.getJSONObject("body").getString("printheadtemperature"));
            info.setTotalmemory(myObject.getJSONObject("body").getString("totalstorage"));
            info.setTotalram(myObject.getJSONObject("body").getString("totalram"));
            info.setUptime(myObject.getJSONObject("body").getString("printeruptime"));
            info.setTotaldistanceprint(myObject.getJSONObject("body").getString("totaldistanceprint"));
            info.setPrintersscansByIdscan(printerscan);
            //info.setUsedmemory(myObject.getJSONObject("body").getString("cpuusage"));
            //info.setUsedram(myObject.getJSONObject("body").getString("cpuusage"));
            Info.save(info);

            //ADD to Printers (Historique printers) ******************************
            if (Printers.findBySerial(myObject.getJSONObject("body").getString("serialnumber")).size() == 0) {
                printer.setIpadresse(myObject.getJSONObject("body").getString("ipadresse"));
                printer.setCpuusage(myObject.getJSONObject("body").getString("cpuusage"));
                printer.setFirmwareversion(myObject.getJSONObject("body").getString("firmwareversion"));
                printer.setFaultydotsprinthead(myObject.getJSONObject("body").getString("faultydots"));
                printer.setFreememory(myObject.getJSONObject("body").getString("freestorage"));
                printer.setFreeram(myObject.getJSONObject("body").getString("freeram"));
                printer.setMacadresse(myObject.getJSONObject("body").getString("ethernetmac"));
                printer.setMaxprintheadtemp(myObject.getJSONObject("body").getString("maximumtemperature"));
                printer.setMinprintheadtemp(myObject.getJSONObject("body").getString("minimumtemperature"));
                //info.setNumberofdots(myObject.getJSONObject("body").getString("cpuusage"));
                printer.setPartnumber(myObject.getJSONObject("body").getString("configurationnumber"));
                printer.setPrintedlabel(myObject.getJSONObject("body").getString("printedlabels"));
                printer.setSerialnumber(myObject.getJSONObject("body").getString("serialnumber"));
                printer.setPrintheadtemp(myObject.getJSONObject("body").getString("printheadtemperature"));
                printer.setTotalmemory(myObject.getJSONObject("body").getString("totalstorage"));
                printer.setTotalram(myObject.getJSONObject("body").getString("totalram"));
                printer.setUptime(myObject.getJSONObject("body").getString("printeruptime"));
                printer.setTotaldistanceprint(myObject.getJSONObject("body").getString("totaldistanceprint"));
                printer.setLastping("Yes");
                printer.setPrintersscansByIdscan(printerscan);
                //info.setUsedmemory(myObject.getJSONObject("body").getString("cpuusage"));
                //info.setUsedram(myObject.getJSONObject("body").getString("cpuusage"));

                Printers.save(printer);
            } else {
                //UPDATE PRINTERS IF SERIAL NUMBER EXIST
                if (!myObject.getJSONObject("body").getString("ipadresse").equals(pr.getIpadresse())) {

                }
                pr.setIpadresse(myObject.getJSONObject("body").getString("ipadresse"));
                pr.setCpuusage(myObject.getJSONObject("body").getString("cpuusage"));
                pr.setFirmwareversion(myObject.getJSONObject("body").getString("firmwareversion"));
                pr.setFaultydotsprinthead(myObject.getJSONObject("body").getString("faultydots"));
                pr.setFreememory(myObject.getJSONObject("body").getString("freestorage"));
                pr.setFreeram(myObject.getJSONObject("body").getString("freeram"));
                pr.setMacadresse(myObject.getJSONObject("body").getString("ethernetmac"));
                pr.setMaxprintheadtemp(myObject.getJSONObject("body").getString("maximumtemperature"));
                pr.setMinprintheadtemp(myObject.getJSONObject("body").getString("minimumtemperature"));
                //info.setNumberofdots(myObject.getJSONObject("body").getString("cpuusage"));
                pr.setPartnumber(myObject.getJSONObject("body").getString("configurationnumber"));
                pr.setPrintedlabel(myObject.getJSONObject("body").getString("printedlabels"));
                pr.setSerialnumber(myObject.getJSONObject("body").getString("serialnumber"));
                pr.setPrintheadtemp(myObject.getJSONObject("body").getString("printheadtemperature"));
                pr.setTotalmemory(myObject.getJSONObject("body").getString("totalstorage"));
                pr.setTotalram(myObject.getJSONObject("body").getString("totalram"));
                pr.setUptime(myObject.getJSONObject("body").getString("printeruptime"));
                pr.setTotaldistanceprint(myObject.getJSONObject("body").getString("totaldistanceprint"));
                pr.setLastping("Yes");
                pr.setPrintersscansByIdscan(printerscan);

                Printers.save(pr);
            }

            System.out.println(myObject.getJSONObject("body").getString("ipadresse"));
        }


        Thread.currentThread().interrupt();
    }
}