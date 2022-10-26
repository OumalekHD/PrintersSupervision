package com.printers.superviseur;

import java.io.*;

public class testsocket {

    public static void main(String[] args) {

        testsocket s2 = new testsocket();
        s2.runSample();
    }

    void runSample() {

        ProcessBuilder launcher = new ProcessBuilder();

        launcher.redirectErrorStream(true);

        String[] command = new String[10];
        command[0] = "d:\\Paexec.exe";
        command[1] = "\\\\172.16.94.65";
        command[2] = "-u";
        command[3] = "Azura-Maroc\\M.OUMALEK";
        command[4] = "-p";
        command[5] = "@dminRoot2021";
        command[6] = "XCOPY";
        command[7] = "/Y";
        command[8] = "\"\\\\Srv-storage-2\\IT MARAISSA\\SUPPORT\\Logiciels Maraissa\\AnyDesk.msi\"";
        command[9] = "\"c:\\temp\\\"";

        launcher.command(command);

        Process p = null;
        try {
            //PrintStream out = new PrintStream(System.out, true, "UTF-8");
            p = launcher.start();
            BufferedReader output = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = output.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}