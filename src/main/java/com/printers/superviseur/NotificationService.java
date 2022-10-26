package com.printers.superviseur;

import com.printers.superviseur.entity.PrintersEntity;
import com.printers.superviseur.repository.PrinterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class NotificationService {

    private JavaMailSender javaMailSender;

    @Autowired
    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PrinterRepository printer;

    @Async
    public void SendEmailAllFaultyDots() {

        try {
            List<PrintersEntity> listprinters = printer.findDots();
            long findDotsCountbyprinters = printer.findDotsCount();
            String htmlMsg = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "#customers {\n" +
                    "  font-family: Arial, Helvetica, sans-serif;\n" +
                    "  border-collapse: collapse;\n" +
                    "  width: 100%;\n" +
                    "}\n" +
                    "\n" +
                    "#customers td, #customers th {\n" +
                    "  border: 1px solid #ddd;\n" +
                    "  padding: 8px;\n" +
                    "}\n" +
                    "\n" +
                    "#customers tr:nth-child(even){background-color: #f2f2f2;}\n" +
                    "\n" +
                    "#customers tr:hover {background-color: #ddd;}\n" +
                    "\n" +
                    "#customers th {\n" +
                    "  padding-top: 9px;\n" +
                    "  padding-bottom: 9px;\n" +
                    "  text-align: left;\n" +
                    "  background-color: #4CAF50;\n" +
                    "  color: white;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div>\n" +
                    "  <br>Bonjour,</br>\n" +
                    "  <br>Veuillez trouver en dessous la liste des imprimantes avec des têtes d'impression déféctueuses :</br>\n" +
                    "  <br>Le Nombre Total des têtes des impressions déféctueuses est : <strong>" + findDotsCountbyprinters + "</strong> </br>\n" +
                    "<br></br>" +
                    "  <table id=\"customers\">\n" +
                    "    <tr>\n" +
                    "      <th>Station</th>\n" +
                    "      <th>Chantier</th>\n" +
                    "      <th>Ligne</th>\n" +
                    "      <th>Imprimante</th>\n" +
                    "      <th>S/N</th>\n" +
                    "      <th>Nombre de dot erronées</th>\n" +
                    "      <th>Dérnier Scan</th>\n" +
                    "    </tr>\n";

            for (PrintersEntity L : listprinters) {
                htmlMsg = htmlMsg + "<tr>\n" +
                        "      <td>" + L.getLastping() + "" + "</td>\n" +
                        "      <td>" + L.getMaxprintheadtemp() + "" + "</td>\n" +
                        "      <td>" + L.getMinprintheadtemp() + "" + "</td>\n" +
                        "      <td>" + L.getPartnumber() + "" + "</td>\n" +
                        "      <td>" + L.getSerialnumber() + "</td>\n" +
                        "      <td>" + L.getFaultydotsprinthead() + "</td>\n" +
                        "      <td>" + L.getTotalram() + "</td>\n" +
                        "    </tr>\n";
            }
            htmlMsg = htmlMsg + " </table>\n" +
                    " <br>Cordialement,</br>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
            String[] emailslist = {"moumalek@azura-group.com"};
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(emailslist);
            helper.setFrom("noreply@azura-maroc.com");
            helper.setSubject("Rapport quotidien : Têtes d'Impression");
            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void SendEmailUpdateFaultyDots(String IP, String Imprimante, String SN, String ANDE, String NNDE) {
        try {
            String htmlMsg = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "#customers {\n" +
                    "  font-family: Arial, Helvetica, sans-serif;\n" +
                    "  border-collapse: collapse;\n" +
                    "  width: 100%;\n" +
                    "}\n" +
                    "\n" +
                    "#customers td, #customers th {\n" +
                    "  border: 1px solid #ddd;\n" +
                    "  padding: 8px;\n" +
                    "}\n" +
                    "\n" +
                    "#customers tr:nth-child(even){background-color: #f2f2f2;}\n" +
                    "\n" +
                    "#customers tr:hover {background-color: #ddd;}\n" +
                    "\n" +
                    "#customers th {\n" +
                    "  padding-top: 9px;\n" +
                    "  padding-bottom: 9px;\n" +
                    "  text-align: left;\n" +
                    "  background-color: #4CAF50;\n" +
                    "  color: white;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div>\n" +
                    "  <br>Bonjour,</br>\n" +
                    "  <br>Plus de dots défectueuses sur La tête d'impression de l'imprimante en dessous :</br>\n" +
                    "<br></br>" +
                    "  <table id=\"customers\">\n" +
                    "    <tr>\n" +
                    "      <th>Imprimante</th>\n" +
                    "      <th>S/N</th>\n" +
                    "      <th>Adresse IP</th>\n" +
                    "      <th>Ancien Nombre de dot erronnées</th>\n" +
                    "      <th>Nouveau Nombre de dot erronées</th>\n" +
                    "    </tr>\n";

            htmlMsg = htmlMsg + "<tr>\n" +
                    "      <td>" + Imprimante + "" + "</td>\n" +
                    "      <td>" + SN + "" + "</td>\n" +
                    "      <td>" + IP + "" + "</td>\n" +
                    "      <td>" + ANDE + "" + "</td>\n" +
                    "      <td>" + NNDE + "</td>\n" +
                    "    </tr>\n";

            htmlMsg = htmlMsg + " </table>\n" +
                    " <br>Cordialement,</br>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
            String[] emailslist = {"helpdesk@azura-group.com", "moumalek@azura-group.com"};
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(emailslist);
            helper.setFrom("moumalek@azura-group.com");
            helper.setSubject("Mise à jour de Nombres de dot erronnés : S/N " + SN);
            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
