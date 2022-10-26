package com.printers.superviseur.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "printers", schema = "public", catalog = "PrinterSup")
public class PrintersEntity {
    private String cpuusage;
    private Timestamp dateanalyse;
    private String firmwareversion;
    private String partnumber;
    private String serialnumber;
    private String uptime;
    private String ipadresse;
    private String macadresse;
    private String faultydotsprinthead;
    private String freememory;
    private String freeram;
    private String usedram;
    private String usedmemory;
    private String totalram;
    private String totalmemory;
    private String printheadtemp;
    private String printedlabel;
    private String numberofdots;
    private String minprintheadtemp;
    private String maxprintheadtemp;
    private String totaldistanceprint;
    private String lastping;
    private int idscan;
    private PrintersscansEntity printersscansByIdscan;

    @Basic
    @Column(name = "cpuusage", nullable = true, length = 30)
    public String getCpuusage() {
        return cpuusage;
    }

    public void setCpuusage(String cpuusage) {
        this.cpuusage = cpuusage;
    }

    @Basic
    @Column(name = "idscan", nullable = false, insertable = false, updatable = false)
    public int getIdscan() {
        return idscan;
    }

    public void setIdscan(int idscan) {
        this.idscan = idscan;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "dateanalyse", nullable = false)
    public Timestamp getDateanalyse() {
        return dateanalyse;
    }

    public void setDateanalyse(Timestamp dateanalyse) {
        this.dateanalyse = dateanalyse;
    }

    @Basic
    @Column(name = "firmwareversion", nullable = true, length = 30)
    public String getFirmwareversion() {
        return firmwareversion;
    }

    public void setFirmwareversion(String firmwareversion) {
        this.firmwareversion = firmwareversion;
    }

    @Basic
    @Column(name = "partnumber", nullable = true, length = 30)
    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
    }

    @Id
    @Column(name = "serialnumber", nullable = false, length = 20)
    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    @Basic
    @Column(name = "uptime", nullable = true, length = 30)
    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    @Basic
    @Column(name = "ipadresse", nullable = true, length = 15)
    public String getIpadresse() {
        return ipadresse;
    }

    public void setIpadresse(String ipadresse) {
        this.ipadresse = ipadresse;
    }

    @Basic
    @Column(name = "macadresse", nullable = true, length = 30)
    public String getMacadresse() {
        return macadresse;
    }

    public void setMacadresse(String macadresse) {
        this.macadresse = macadresse;
    }

    @Basic
    @Column(name = "faultydotsprinthead", nullable = true, length = 30)
    public String getFaultydotsprinthead() {
        return faultydotsprinthead;
    }

    public void setFaultydotsprinthead(String faultydotsprinthead) {
        this.faultydotsprinthead = faultydotsprinthead;
    }

    @Basic
    @Column(name = "freememory", nullable = true, length = 30)
    public String getFreememory() {
        return freememory;
    }

    public void setFreememory(String freememory) {
        this.freememory = freememory;
    }

    @Basic
    @Column(name = "freeram", nullable = true, length = 30)
    public String getFreeram() {
        return freeram;
    }

    public void setFreeram(String freeram) {
        this.freeram = freeram;
    }

    @Basic
    @Column(name = "usedram", nullable = true, length = 30)
    public String getUsedram() {
        return usedram;
    }

    public void setUsedram(String usedram) {
        this.usedram = usedram;
    }

    @Basic
    @Column(name = "usedmemory", nullable = true, length = 30)
    public String getUsedmemory() {
        return usedmemory;
    }

    public void setUsedmemory(String usedmemory) {
        this.usedmemory = usedmemory;
    }

    @Basic
    @Column(name = "totalram", nullable = true, length = 30)
    public String getTotalram() {
        return totalram;
    }

    public void setTotalram(String totalram) {
        this.totalram = totalram;
    }

    @Basic
    @Column(name = "totalmemory", nullable = true, length = 30)
    public String getTotalmemory() {
        return totalmemory;
    }

    public void setTotalmemory(String totalmemory) {
        this.totalmemory = totalmemory;
    }

    @Basic
    @Column(name = "printheadtemp", nullable = true, length = 30)
    public String getPrintheadtemp() {
        return printheadtemp;
    }

    public void setPrintheadtemp(String printheadtemp) {
        this.printheadtemp = printheadtemp;
    }

    @Basic
    @Column(name = "printedlabel", nullable = true, length = 30)
    public String getPrintedlabel() {
        return printedlabel;
    }

    public void setPrintedlabel(String printedlabel) {
        this.printedlabel = printedlabel;
    }

    @Basic
    @Column(name = "numberofdots", nullable = true, length = 30)
    public String getNumberofdots() {
        return numberofdots;
    }

    public void setNumberofdots(String numberofdots) {
        this.numberofdots = numberofdots;
    }

    @Basic
    @Column(name = "minprintheadtemp", nullable = true, length = 30)
    public String getMinprintheadtemp() {
        return minprintheadtemp;
    }

    public void setMinprintheadtemp(String minprintheadtemp) {
        this.minprintheadtemp = minprintheadtemp;
    }

    @Basic
    @Column(name = "maxprintheadtemp", nullable = true, length = 30)
    public String getMaxprintheadtemp() {
        return maxprintheadtemp;
    }

    public void setMaxprintheadtemp(String maxprintheadtemp) {
        this.maxprintheadtemp = maxprintheadtemp;
    }

    @Basic
    @Column(name = "totaldistanceprint", nullable = true, length = 30)
    public String getTotaldistanceprint() {
        return totaldistanceprint;
    }

    public void setTotaldistanceprint(String totaldistanceprint) {
        this.totaldistanceprint = totaldistanceprint;
    }

    @Basic
    @Column(name = "lastping", nullable = false, length = 30)
    public String getLastping() {
        return lastping;
    }

    public void setLastping(String lastping) {
        this.lastping = lastping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintersEntity that = (PrintersEntity) o;
        return Objects.equals(cpuusage, that.cpuusage) &&
                Objects.equals(idscan, that.idscan) &&
                Objects.equals(dateanalyse, that.dateanalyse) &&
                Objects.equals(firmwareversion, that.firmwareversion) &&
                Objects.equals(partnumber, that.partnumber) &&
                Objects.equals(serialnumber, that.serialnumber) &&
                Objects.equals(uptime, that.uptime) &&
                Objects.equals(ipadresse, that.ipadresse) &&
                Objects.equals(macadresse, that.macadresse) &&
                Objects.equals(faultydotsprinthead, that.faultydotsprinthead) &&
                Objects.equals(freememory, that.freememory) &&
                Objects.equals(freeram, that.freeram) &&
                Objects.equals(usedram, that.usedram) &&
                Objects.equals(usedmemory, that.usedmemory) &&
                Objects.equals(totalram, that.totalram) &&
                Objects.equals(totalmemory, that.totalmemory) &&
                Objects.equals(printheadtemp, that.printheadtemp) &&
                Objects.equals(printedlabel, that.printedlabel) &&
                Objects.equals(numberofdots, that.numberofdots) &&
                Objects.equals(minprintheadtemp, that.minprintheadtemp) &&
                Objects.equals(maxprintheadtemp, that.maxprintheadtemp) &&
                Objects.equals(totaldistanceprint, that.totaldistanceprint) &&
                Objects.equals(lastping, that.lastping);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpuusage, idscan, dateanalyse, firmwareversion, partnumber, serialnumber, uptime, ipadresse, macadresse, faultydotsprinthead, freememory, freeram, usedram, usedmemory, totalram, totalmemory, printheadtemp, printedlabel, numberofdots, minprintheadtemp, maxprintheadtemp, totaldistanceprint, lastping);
    }

    @ManyToOne
    @JoinColumn(name = "idscan", referencedColumnName = "idscan")
    public PrintersscansEntity getPrintersscansByIdscan() {
        return printersscansByIdscan;
    }

    public void setPrintersscansByIdscan(PrintersscansEntity printersscansByIdscan) {
        this.printersscansByIdscan = printersscansByIdscan;
    }

}
