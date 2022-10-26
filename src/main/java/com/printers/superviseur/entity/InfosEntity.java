package com.printers.superviseur.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "infos", schema = "public", catalog = "PrinterSup")
public class InfosEntity {
    private long idinfo;
    private int idscan;
    private String ipadresse;
    private String serialnumber;
    private String firmwareversion;
    private String macadresse;
    private String partnumber;
    private String uptime;
    private String cpuusage;
    private String totalram;
    private String usedram;
    private String freeram;
    private String totalmemory;
    private String usedmemory;
    private String freememory;
    private String printedlabel;
    private String maxprintheadtemp;
    private String minprintheadtemp;
    private String printheadtemp;
    private String numberofdots;
    private String faultydotsprinthead;
    private String totaldistanceprint;
    private Timestamp dateanalyse;
    private PrintersscansEntity printersscansByIdscan;

    @Id
    @Column(name = "idinfo", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getIdinfo() {
        return idinfo;
    }

    public void setIdinfo(long idinfo) {
        this.idinfo = idinfo;
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
    @Column(name = "ipadresse", nullable = true, length = 15)
    public String getIpadresse() {
        return ipadresse;
    }

    public void setIpadresse(String ipadresse) {
        this.ipadresse = ipadresse;
    }

    @Basic
    @Column(name = "serialnumber", nullable = true, length = 20)
    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
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
    @Column(name = "macadresse", nullable = true, length = 30)
    public String getMacadresse() {
        return macadresse;
    }

    public void setMacadresse(String macadresse) {
        this.macadresse = macadresse;
    }

    @Basic
    @Column(name = "partnumber", nullable = true, length = 30)
    public String getPartnumber() {
        return partnumber;
    }

    public void setPartnumber(String partnumber) {
        this.partnumber = partnumber;
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
    @Column(name = "cpuusage", nullable = true, length = 30)
    public String getCpuusage() {
        return cpuusage;
    }

    public void setCpuusage(String cpuusage) {
        this.cpuusage = cpuusage;
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
    @Column(name = "usedram", nullable = true, length = 30)
    public String getUsedram() {
        return usedram;
    }

    public void setUsedram(String usedram) {
        this.usedram = usedram;
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
    @Column(name = "totalmemory", nullable = true, length = 30)
    public String getTotalmemory() {
        return totalmemory;
    }

    public void setTotalmemory(String totalmemory) {
        this.totalmemory = totalmemory;
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
    @Column(name = "freememory", nullable = true, length = 30)
    public String getFreememory() {
        return freememory;
    }

    public void setFreememory(String freememory) {
        this.freememory = freememory;
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
    @Column(name = "maxprintheadtemp", nullable = true, length = 30)
    public String getMaxprintheadtemp() {
        return maxprintheadtemp;
    }

    public void setMaxprintheadtemp(String maxprintheadtemp) {
        this.maxprintheadtemp = maxprintheadtemp;
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
    @Column(name = "printheadtemp", nullable = true, length = 30)
    public String getPrintheadtemp() {
        return printheadtemp;
    }

    public void setPrintheadtemp(String printheadtemp) {
        this.printheadtemp = printheadtemp;
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
    @Column(name = "faultydotsprinthead", nullable = true, length = 30)
    public String getFaultydotsprinthead() {
        return faultydotsprinthead;
    }

    public void setFaultydotsprinthead(String faultydotsprinthead) {
        this.faultydotsprinthead = faultydotsprinthead;
    }

    @Basic
    @Column(name = "dateanalyse", nullable = false)
    @CreationTimestamp
    public Timestamp getDateanalyse() {
        return dateanalyse;
    }

    public void setDateanalyse(Timestamp dateanalyse) {
        this.dateanalyse = dateanalyse;
    }

    @Basic
    @Column(name = "totaldistanceprint", nullable = true, length = 30)
    public String getTotaldistanceprint() {
        return totaldistanceprint;
    }

    public void setTotaldistanceprint(String totaldistanceprint) {
        this.totaldistanceprint = totaldistanceprint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfosEntity that = (InfosEntity) o;
        return idinfo == that.idinfo &&
                idscan == that.idscan &&
                ipadresse == that.ipadresse &&
                Objects.equals(serialnumber, that.serialnumber) &&
                Objects.equals(firmwareversion, that.firmwareversion) &&
                Objects.equals(macadresse, that.macadresse) &&
                Objects.equals(partnumber, that.partnumber) &&
                Objects.equals(uptime, that.uptime) &&
                Objects.equals(cpuusage, that.cpuusage) &&
                Objects.equals(totalram, that.totalram) &&
                Objects.equals(usedram, that.usedram) &&
                Objects.equals(freeram, that.freeram) &&
                Objects.equals(totalmemory, that.totalmemory) &&
                Objects.equals(usedmemory, that.usedmemory) &&
                Objects.equals(freememory, that.freememory) &&
                Objects.equals(printedlabel, that.printedlabel) &&
                Objects.equals(maxprintheadtemp, that.maxprintheadtemp) &&
                Objects.equals(minprintheadtemp, that.minprintheadtemp) &&
                Objects.equals(printheadtemp, that.printheadtemp) &&
                Objects.equals(numberofdots, that.numberofdots) &&
                Objects.equals(faultydotsprinthead, that.faultydotsprinthead) &&
                Objects.equals(dateanalyse, that.dateanalyse) &&
                Objects.equals(totaldistanceprint, that.totaldistanceprint);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idinfo, idscan, ipadresse, serialnumber, firmwareversion, macadresse, partnumber, uptime, cpuusage, totalram, usedram, freeram, totalmemory, usedmemory, freememory, printedlabel, maxprintheadtemp, minprintheadtemp, printheadtemp, numberofdots, faultydotsprinthead, dateanalyse, totaldistanceprint);
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
