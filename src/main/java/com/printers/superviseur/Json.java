package com.printers.superviseur;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Json {

    public ResponseEntity<?> gethtml(String ip, String ref) {
        Map<String, String> printinfo = new HashMap<>();
        String content = null;
        Scanner scanner;
        HttpURLConnection connection = null;

        if (ref.equals("PD43") || ref.equals("PC43T")) {

            try {

                connection = (HttpURLConnection) new URL("http://" + ip + "/localization.lua?pageid=Home&newnodeval=1").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                scanner.close();
                connection.disconnect();

                connection = (HttpURLConnection) new URL("http://" + ip + "/statistics/systeminfo.lua?sysnodename=System&Information&sysnodeid=258&").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
                connection.disconnect();

                if (content.contains("Uptime")) {
                    printinfo.put("ipadresse", ip);
                    printinfo.put("modeleprinter", StringUtils.substringBetween(content, "Product Name\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("configurationnumber", StringUtils.substringBetween(content, "Printer Configuration Number\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("serialnumber", StringUtils.substringBetween(content, "Printer Serial Number\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("firmwareversion", StringUtils.substringBetween(content, "Firmware Version\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("totalstorage", StringUtils.substringBetween(content, "Total Memory\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("freestorage", StringUtils.substringBetween(content, "Available Memory\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("cpuusage", StringUtils.substringBetween(content, "CPU Usage\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("totalram", StringUtils.substringBetween(content, "Total RAM\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("freeram", StringUtils.substringBetween(content, "Free RAM\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("printeruptime", StringUtils.substringBetween(content, "Uptime\n</td>\n<td>\n", "</td>").replace("\n", " "));

                }

                connection = (HttpURLConnection) new URL("http://" + ip + "/statistics/displaydata.lua?sysnodename=Print%20Statistics&sysnodeid=260&").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
                connection.disconnect();

                if (content.contains("Labels Printed")) {
                    printinfo.put("printedlabels", StringUtils.substringBetween(content, "Labels Printed\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("totaldistanceprint", StringUtils.substringBetween(content, "Total Distance Printed (Printer)\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("totaldistanceprinthead", StringUtils.substringBetween(content, "Total Distance Printed (Printhead)\n</td>\n<td>", "</td>").replace("\n", ""));
                }

                connection = (HttpURLConnection) new URL("http://" + ip + "/statistics/tphInfo.lua?sysnodename=Supply&sysnodeid=261&").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
                connection.disconnect();

                if (content.toLowerCase().contains("printhead information")) {
                    //printinfo.put("datetime",StringUtils.substringBetween(content, "Date/Time"+"\n</td>\n<td>", "</td>").replace("\n",""));
                    printinfo.put("minimumtemperature", StringUtils.substringBetween(content, "Minimum Temperature\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("maximumtemperature", StringUtils.substringBetween(content, "Maximum Temperature\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("faultydots", StringUtils.substringBetween(content, "Number of Faulty Dots\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("printheadtemperature", StringUtils.substringBetween(content, "Printhead Temperature\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("ribbondistance", StringUtils.substringBetween(content, "Ribbon Remaining\n</td>\n<td>", "</td>").replace("\n", ""));
                    //printinfo.put("serialnumberprinthead",StringUtils.substringBetween(content, "Serial Number\n</td>\n<td>", "</td>").replace("\n",""));
                }

                connection = (HttpURLConnection) new URL("http://" + ip + "/main/home.lua").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
                connection.disconnect();

                if (content.toLowerCase().contains("command language")) {
                    String ipadress = StringUtils.substringBetween(content, "Ethernet\n</td>\n<td width=\"25%\" height=\"30\" padding:15px;>", "</td>").replace("\n", "");
                    printinfo.put("commandlanguage", StringUtils.substringBetween(content, "Command Language\n</td>\n<td width=\"50%\" height=\"30\" padding:15px;>", "</td>").replace("\n", ""));
                    printinfo.put("ethernetmac", StringUtils.substringBetween(content, ipadress + "\n</td>\n</td>\n<td width=\"25%\" height=\"30\" padding:15px;>", "</td>").replace("\n", ""));
                    printinfo.put("printmethodribbon", StringUtils.substringBetween(content, "Print Method\n</td>\n<td width='50%' height='25' padding:15px;>", "</td>").replace("\n", ""));
                    printinfo.put("mediatype", StringUtils.substringBetween(content, "Media Type\n</td>\n<td width='50%' height='25' padding:15px;>", "</td>").replace("\n", ""));
                    printinfo.put("marjinx", StringUtils.substringBetween(content, "Media Margin (X)\n</td>\n<td width='50%' height='25' padding:15px;>", "</td>").replace("\n", ""));
                    printinfo.put("mediawidth", StringUtils.substringBetween(content, "Media Width\n</td>\n<td width='50%' height='25' padding:15px;>", "</td>").replace("\n", ""));
                    printinfo.put("medialenght", StringUtils.substringBetween(content, "Media Length\n</td>\n<td width='50%' height='25' padding:15px;>", "</td>").replace("\n", ""));
                    if (content.contains("checkmark.png")) {
                        printinfo.put("printstatut", "Ready");
                    } else if (content.contains("caution.png")) {
                        if (content.toLowerCase().contains("printer in pause mode"))
                            printinfo.put("printstatut", StringUtils.substringBetween(content, "<div class=\"pos_fixed\">\n<div style=\"position:relative;top:10px;color:#990000;\" \nalign=\"center\" >", "</div>").replace("\n", ""));
                        else if (content.toLowerCase().contains("next label not found"))
                            printinfo.put("printstatut", StringUtils.substringBetween(content, "<div class=\"pos_fixed\">\n<div style=\"position:relative;top:10px;color:#990000;\" \nalign=\"center\" >", "</div>").replace("\n", ""));
                        else if (content.toLowerCase().contains("printing"))
                            printinfo.put("printstatut", StringUtils.substringBetween(content, "<div style=\"position:relative;top:10px;color:#990000;\" \nalign=\"center\" >", "</div>").replace("\n", ""));
                        else if (content.toLowerCase().contains("test feed failed"))
                            printinfo.put("printstatut", StringUtils.substringBetween(content, "<div style=\"position:relative;top:10px;color:#990000;\" \nalign=\"center\" >", "</div>").replace("\n", ""));
                        else if (content.toLowerCase().contains("print job complete"))
                            printinfo.put("printstatut", StringUtils.substringBetween(content, "<div class=\"pos_fixed\">\n<div style=\"position:relative;top:10px;color:#990000;\" \nalign=\"center\" >", "</div>").replace("\n", ""));
                        else
                            printinfo.put("printstatut", StringUtils.substringBetween(content, "<div class=\"pos_fixed\">\n<div style=\"position:relative;top:10px;color:#990000;\" \nalign=\"center\" ></div>", "</div>").replace("\n", ""));
                    } else if (content.contains("printhead_lifted.png")) {
                        printinfo.put("printstatut", StringUtils.substringBetween(content, "<div class=\"pos_fixed\">\n<div style=\"position:relative;top:10px;color:#990000;\" \nalign=\"center\" >", "</div>").replace("\n", ""));
                    }
                }
            } catch (SocketTimeoutException et) {
                System.out.println(ip + " : " + et.toString());
            } catch (java.lang.NullPointerException ex) {
                System.out.println(ip + " : " + ex.toString());
            } catch (NoRouteToHostException er) {
                System.out.println(ip + " : " + er.toString());
            } catch (FileNotFoundException er) {
                System.out.println(ip + " : " + er.toString());
            } catch (Exception ex) {
                System.out.println(ip + " : " + ex.toString());
            }
        } else if (ref.equals("PM43")) {

            try {

                connection = (HttpURLConnection) new URL("http://" + ip + "/localization.lua?pageid=Home&newnodeval=1").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                scanner.close();
                connection.disconnect();

                connection = (HttpURLConnection) new URL("http://" + ip + "/statistics/systeminfo.lua?sysnodename=System&Information&sysnodeid=258&").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
                connection.disconnect();

                if (content.contains("Uptime")) {
                    printinfo.put("ipadresse", ip);
                    printinfo.put("modeleprinter", StringUtils.substringBetween(content, "Product Name\n</th>\n<th>", "</th>").replace("\n", ""));
                    printinfo.put("configurationnumber", StringUtils.substringBetween(content, "Printer Configuration Number\n</th>\n<th>", "</th>").replace("\n", ""));
                    printinfo.put("serialnumber", StringUtils.substringBetween(content, "Printer Serial Number\n</th>\n<th>", "</th>").replace("\n", ""));
                    printinfo.put("firmwareversion", StringUtils.substringBetween(content, "Firmware Version\n</th>\n<th>", "</th>").replace("\n", ""));
                    printinfo.put("totalstorage", StringUtils.substringBetween(content, "Total Memory\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("freestorage", StringUtils.substringBetween(content, "Available Memory\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("cpuusage", StringUtils.substringBetween(content, "CPU Usage\n</th>\n<th>", "</th>").replace("\n", ""));
                    printinfo.put("totalram", StringUtils.substringBetween(content, "Total RAM\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("freeram", StringUtils.substringBetween(content, "Free RAM\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("printeruptime", StringUtils.substringBetween(content, "Uptime\n</th>\n<th>\n", "</th>").replace("\n", " "));

                }

                connection = (HttpURLConnection) new URL("http://" + ip + "/statistics/displaydata.lua?sysnodename=Print%20Statistics&sysnodeid=260&").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
                connection.disconnect();

                if (content.contains("Labels Printed")) {
                    printinfo.put("printedlabels", StringUtils.substringBetween(content, "Labels Printed\n</th>\n<th>", "</th>").replace("\n", ""));
                    printinfo.put("totaldistanceprint", StringUtils.substringBetween(content, "Total Distance Printed (Printer)\n</th>\n<th>", "</th>").replace("\n", ""));
                    printinfo.put("totaldistanceprinthead", StringUtils.substringBetween(content, "Total Distance Printed (Printhead)\n</th>\n<th>", "</th>").replace("\n", ""));

                }

                connection = (HttpURLConnection) new URL("http://" + ip + "/statistics/tphInfo.lua?sysnodename=Supply&sysnodeid=261&").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
                connection.disconnect();

                if (content.toLowerCase().contains("printhead information")) {
                    //printinfo.put("datetime",StringUtils.substringBetween(content, "Date/Time"+"\n</td>\n<td>", "</td>").replace("\n",""));
                    printinfo.put("minimumtemperature", StringUtils.substringBetween(content, "Minimum Temperature\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("maximumtemperature", StringUtils.substringBetween(content, "Maximum Temperature\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("faultydots", StringUtils.substringBetween(content, "Number of Faulty Dots\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("printheadtemperature", StringUtils.substringBetween(content, "Printhead Temperature\n</td>\n<td>", "</td>").replace("\n", ""));
                    printinfo.put("ribbondistance", StringUtils.substringBetween(content, "Ribbon Remaining\n</td>\n<td>", "</td>").replace("\n", ""));
                    //printinfo.put("serialnumberprinthead",StringUtils.substringBetween(content, "Serial Number\n</td>\n<td>", "</td>").replace("\n",""));
                }

                connection = (HttpURLConnection) new URL("http://" + ip + "/main/home.lua").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
                connection.disconnect();

                if (content.toLowerCase().contains("command language")) {
                    printinfo.put("commandlanguage", StringUtils.substringBetween(content, "Command Language</div>\n<div class=\"home_concent\">", "</div>").replace("\n", ""));
                    printinfo.put("printmethodribbon", StringUtils.substringBetween(content, "Print Method</div>\n<div class=\"home_concent\">", "</div>").replace("\n", ""));
                    printinfo.put("mediatype", StringUtils.substringBetween(content, "Media Type</div>\n<div class=\"home_concent\">", "</div>").replace("\n", ""));
                    printinfo.put("marjinx", StringUtils.substringBetween(content, "Media Margin (X)</div>\n<div class=\"home_concent\">", "</div>").replace("\n", ""));
                    printinfo.put("mediawidth", StringUtils.substringBetween(content, "Media Width </div>\n<div class=\"home_concent\">", "</div>").replace("\n", ""));
                    printinfo.put("medialenght", StringUtils.substringBetween(content, "Media Length </div>\n<div class=\"home_concent\">", "</div>").replace("\n", ""));
                    printinfo.put("printstatut", "NULL");

                }

                connection = (HttpURLConnection) new URL("http://" + ip + "/statistics/netdev.lua?sysnodeid=16385&sysnodename=Network%20Interfaces&snodetype=9&pageid=Statistics").openConnection();
                connection.setConnectTimeout(10 * 1000);
                scanner = new Scanner(connection.getInputStream());
                scanner.useDelimiter("\\Z");
                content = scanner.next();
                scanner.close();
                connection.disconnect();

                if (content.toLowerCase().contains("mac address")) {
                    printinfo.put("ethernetmac", StringUtils.substringBetween(content, "MAC Address\n</th>\n<th>", "</th>").replace("\n", ""));
                }
            } catch (SocketTimeoutException et) {
                System.out.println(ip + " : " + et.toString());
            } catch (java.lang.NullPointerException ex) {
                System.out.println(ip + " : " + ex.toString());
            } catch (NoRouteToHostException er) {
                System.out.println(ip + " : " + er.toString());
            } catch (FileNotFoundException er) {
                System.out.println(ip + " : " + er.toString());
            } catch (Exception ex) {
                System.out.println(ip + " : " + ex.toString());
            }

        }

        LocalTime open = LocalTime.of(7, 30, 00);
        LocalTime closed = LocalTime.of(7, 35, 00);
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isAfter(open) && currentTime.isBefore(closed)) {
            if (ref.equals("PD43") || ref.equals("PC43T") || ref.equals("PM43")) {
                try {
                    connection = (HttpURLConnection) new URL("http://" + ip + "/service/processreboot.lua?command=reboot").openConnection();
                    connection.setConnectTimeout(10 * 1000);
                    System.out.println(ip + " --------- REDEMARAGE OK");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new ResponseEntity<>(printinfo, HttpStatus.OK);
    }


    //SNMP


    public Map<String, String> getSnmp(String ip) {

        SNMPManager snmp = new SNMPManager(ip);
        List<Map<String, String>> mp = snmp.getSNMP();
        Map<String, String> printSnmpInfo = new HashMap<>();

        if (!mp.isEmpty()) {
            printSnmpInfo.put("firmwareversion", StringUtils.substringBetween("1.3.6.1.4.1.1963.20.10.5.3.0=", "}"));
            printSnmpInfo.put("printeruptime", StringUtils.substringBetween("1.3.6.1.2.1.1.3.0=", "}"));
            printSnmpInfo.put("nameprinter", StringUtils.substringBetween("1.3.6.1.4.1.1963.20.10.5.10.0=", "}"));
            printSnmpInfo.put("modeleprinter", StringUtils.substringBetween("1.3.6.1.4.1.1963.20.10.5.4.0=", "}"));
            printSnmpInfo.put("totalstorage", StringUtils.substringBetween("1.3.6.1.4.1.1963.20.10.5.9.1.5.0=", "}"));
            printSnmpInfo.put("printheadtemperature", StringUtils.substringBetween("1.3.6.1.4.1.1963.20.10.20.15.1.1.6=", "}"));
            printSnmpInfo.put("printedlabels", StringUtils.substringBetween("1.3.6.1.4.1.1963.20.10.15.15.10.1.4.0", "}"));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));
            printSnmpInfo.put("", StringUtils.substringBetween("", ""));

            String status = StringUtils.substringBetween("1.3.6.1.4.1.1963.20.10.5.6.0=", "}");
            if (status.equals("3"))
                printSnmpInfo.put("printstatut", "running");
            else if (status.equals("4"))
                printSnmpInfo.put("printstatut", "idle");
            else if (status.equals("5"))
                printSnmpInfo.put("printstatut", "warning");
            else if (status.equals("6"))
                printSnmpInfo.put("printstatut", "testing");
            else if (status.equals("7"))
                printSnmpInfo.put("printstatut", "down");
            else if (status.equals("8"))
                printSnmpInfo.put("printstatut", "setup");
            else if (status.equals("9"))
                printSnmpInfo.put("printstatut", "busy");
            else
                printSnmpInfo.put("printstatut", "upgrading");

        }

        return printSnmpInfo;
    }
}
