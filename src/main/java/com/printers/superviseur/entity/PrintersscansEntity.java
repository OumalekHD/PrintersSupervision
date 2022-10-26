package com.printers.superviseur.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "printersscans", schema = "public", catalog = "PrinterSup")
public class PrintersscansEntity {
    private long idscan;
    private Timestamp datescan;
    private Collection<InfosEntity> infosByIdscan;
    private Collection<PrintersEntity> printersByIdscan;

    @Id
    @Column(name = "idscan", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getIdscan() {
        return idscan;
    }

    public void setIdscan(long idscan) {
        this.idscan = idscan;
    }

    @Basic
    @Column(name = "datescan", nullable = false)
    public Timestamp getDatescan() {
        return datescan;
    }

    public void setDatescan(Timestamp datescan) {
        this.datescan = datescan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintersscansEntity that = (PrintersscansEntity) o;
        return idscan == that.idscan &&
                Objects.equals(datescan, that.datescan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idscan, datescan);
    }

    @OneToMany(mappedBy = "printersscansByIdscan")
    public Collection<InfosEntity> getInfosByIdscan() {
        return infosByIdscan;
    }

    public void setInfosByIdscan(Collection<InfosEntity> infosByIdscan) {
        this.infosByIdscan = infosByIdscan;
    }


    @OneToMany(mappedBy = "printersscansByIdscan")
    public Collection<PrintersEntity> getPrintersByIdscan() {
        return printersByIdscan;
    }

    public void setPrintersByIdscan(Collection<PrintersEntity> printersByIdscan) {
        this.printersByIdscan = printersByIdscan;
    }
}
