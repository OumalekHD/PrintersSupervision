package com.printers.superviseur;

import org.snmp4j.*;
import org.snmp4j.smi.*;

import java.util.HashMap;
import java.util.Map;

public class IntermecPrinter {

    public static OID[] IntermecPrinterListIOD() {
        OID[] ListIOD = new OID[]{
                new OID(".1.3.6.1"),
        };
        return ListIOD;
    }

    public static Map<String, String> IntermecPrinterListDetails() {

        Map<String, String> listed = new HashMap<>();
        //INFORMATION IMPRIMANTE
        listed.put("Version Firmware", "1.3.6.1.4.1.1963.20.10.5.3.0");
        listed.put("modèle", "1.3.6.1.4.1.1963.20.10.5.4.0");
        listed.put("Nom", "1.3.6.1.4.1.1963.20.10.5.10.0");
        listed.put("Description", "1.3.6.1.4.1.1963.20.10.5.1.0");
        listed.put("Mémoire Ram", "1.3.6.1.4.1.1963.20.10.5.5.0");
        listed.put("Mémoire total", "1.3.6.1.4.1.1963.20.10.5.9.1.5");
        listed.put("Mémoire Usée", "1.3.6.1.4.1.1963.20.10.5.9.1.6");
        listed.put("Status", "1.3.6.1.4.1.1963.20.10.5.6.0");
        listed.put("Durée de fonctionnement", "1.3.6.1.4.1.1963.20.10.5.8.0");
        //INFORMATION SUR MEDIA
        listed.put("Status de Media", "1.3.6.1.4.1.1963.20.10.10.10.1.2");
        listed.put("Unité Vitesse d'impression", "1.3.6.1.4.1.1963.20.10.10.10.1.3");
        listed.put("Vitesse d'impression", "1.3.6.1.4.1.1963.20.10.10.10.1.4");
        listed.put("Unité Marge X", "1.3.6.1.4.1.1963.20.10.10.10.1.11");
        listed.put("Marge X", "1.3.6.1.4.1.1963.20.10.10.10.1.12");
        listed.put("Type de Media", "1.3.6.1.4.1.1963.20.10.15.15.5.1.4");
        listed.put("Type de Transfert", "1.3.6.1.4.1.1963.20.10.15.15.5.1.5");
        listed.put("Unité de dimension", "1.3.6.1.4.1.1963.20.10.15.15.5.1.7");
        listed.put("Largeur", "1.3.6.1.4.1.1963.20.10.15.15.5.1.8");
        listed.put("Largeur MAX", "1.3.6.1.4.1.1963.20.10.15.15.5.1.9");
        listed.put("Hauteur", "1.3.6.1.4.1.1963.20.10.15.15.5.1.10");
        listed.put("Hauteur MAX", "1.3.6.1.4.1.1963.20.10.15.15.5.1.11");
        listed.put("Darkness", "1.3.6.1.4.1.1963.20.10.20.15.5.1.1.2");
        listed.put("Contraste", "1.3.6.1.4.1.1963.20.10.20.15.5.1.1.4");
        listed.put("Total Nombre Etiquette", "1.3.6.1.4.1.1963.20.10.15.15.10.1.4.0");
        //TETE D'IMPRESSION
        listed.put("Marque tête d'impression", "1.3.6.1.4.1.1963.20.10.20.15.1.1.3.0");
        listed.put("Unité résolution", "1.3.6.1.4.1.1963.20.10.20.15.1.1.4");
        listed.put("Résolution", "1.3.6.1.4.1.1963.20.10.20.15.1.1.5");
        listed.put("Température", "1.3.6.1.4.1.1963.20.10.20.15.1.1.6");
        listed.put("Résistence", "1.3.6.1.4.1.1963.20.10.20.15.1.1.7");
        listed.put("Status Tête d'impression", "1.3.6.1.4.1.1963.20.10.20.15.1.1.2");
        //RESEAUX
        listed.put("Adaptateur", "1.3.6.1.4.1.1963.20.10.30.10.5.1.2.0");
        listed.put("TCP/IP", "1.3.6.1.4.1.1963.5.15.5.2.1.7");
        listed.put("DHCP", "1.3.6.1.4.1.1963.20.10.30.10.5.1.3.0");
        listed.put("Adresse IP", "1.3.6.1.4.1.1963.5.15.5.2.1.2");
        listed.put("Masque", "1.3.6.1.4.1.1963.5.15.5.2.1.3");
        listed.put("Passerelle", "1.3.6.1.4.1.1963.5.15.5.2.1.4");
        listed.put("DNS1", "1.3.6.1.4.1.1963.5.15.7.1.1.1.1");
        listed.put("DNS2", "1.3.6.1.4.1.1963.5.15.7.1.1.1.2");
        listed.put("Port", "1.3.6.1.4.1.1963.20.10.30.10.5.1.3.0");

        return listed;
    }
}
