package com.printers.superviseur.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "materiels", schema = "public", catalog = "PrinterSup")
public class MaterielsEntity {
    private long idmateriel;
    private int idligne;
    private String typemateriel;
    private String marquemateriel;
    private String modelmateriel;
    private String ipmateriel;
    private String usermateriel;
    private String passmateriel;
    private int etatmateriel;
    private String descmateriel;
    private String pingmateriel;
    private Collection<InfosEntity> infosByIdmateriel;
    private LignesEntity lignesByIdligne;

    @Id
    @Column(name = "idmateriel", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getIdmateriel() {
        return idmateriel;
    }

    public void setIdmateriel(long idmateriel) {
        this.idmateriel = idmateriel;
    }

    @Basic
    @Column(name = "idligne", nullable = false, insertable = false, updatable = false)
    public int getIdligne() {
        return idligne;
    }

    public void setIdligne(int idligne) {
        this.idligne = idligne;
    }

    @Basic
    @Column(name = "typemateriel", nullable = false, length = 30)
    public String getTypemateriel() {
        return typemateriel;
    }

    public void setTypemateriel(String typemateriel) {
        this.typemateriel = typemateriel;
    }

    @Basic
    @Column(name = "marquemateriel", nullable = false, length = 30)
    public String getMarquemateriel() {
        return marquemateriel;
    }

    public void setMarquemateriel(String marquemateriel) {
        this.marquemateriel = marquemateriel;
    }

    @Basic
    @Column(name = "modelmateriel", nullable = false, length = 30)
    public String getModelmateriel() {
        return modelmateriel;
    }

    public void setModelmateriel(String modelmateriel) {
        this.modelmateriel = modelmateriel;
    }

    @Basic
    @Column(name = "ipmateriel", nullable = false, length = 15)
    public String getIpmateriel() {
        return ipmateriel;
    }

    public void setIpmateriel(String ipmateriel) {
        this.ipmateriel = ipmateriel;
    }

    @Basic
    @Column(name = "usermateriel", nullable = false, length = 30)
    public String getUsermateriel() {
        return usermateriel;
    }

    public void setUsermateriel(String usermateriel) {
        this.usermateriel = usermateriel;
    }

    @Basic
    @Column(name = "passmateriel", nullable = false, length = 256)
    public String getPassmateriel() {
        return passmateriel;
    }

    public void setPassmateriel(String passmateriel) {
        this.passmateriel = passmateriel;
    }

    @Basic
    @Column(name = "etatmateriel", nullable = false)
    public int getEtatmateriel() {
        return etatmateriel;
    }

    public void setEtatmateriel(int etatmateriel) {
        this.etatmateriel = etatmateriel;
    }

    @Basic
    @Column(name = "descmateriel", length = 256)
    public String getDescmateriel() {
        return descmateriel;
    }

    public void setDescmateriel(String descmateriel) {
        this.descmateriel = descmateriel;
    }

    @Basic
    @Column(name = "pingmateriel", length = 256)
    public String getPingmateriel() {
        return pingmateriel;
    }

    public void setPingmateriel(String pingmateriel) {
        this.pingmateriel = pingmateriel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterielsEntity that = (MaterielsEntity) o;
        return idmateriel == that.idmateriel &&
                idligne == that.idligne &&
                etatmateriel == that.etatmateriel &&
                Objects.equals(typemateriel, that.typemateriel) &&
                Objects.equals(marquemateriel, that.marquemateriel) &&
                Objects.equals(modelmateriel, that.modelmateriel) &&
                Objects.equals(ipmateriel, that.ipmateriel) &&
                Objects.equals(usermateriel, that.usermateriel) &&
                Objects.equals(passmateriel, that.passmateriel) &&
                Objects.equals(descmateriel, that.descmateriel) &&
                Objects.equals(pingmateriel, that.pingmateriel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idmateriel, idligne, typemateriel, marquemateriel, modelmateriel, ipmateriel, usermateriel, passmateriel, etatmateriel, descmateriel, pingmateriel);
    }

    @ManyToOne
    @JoinColumn(name = "idligne", referencedColumnName = "idligne", nullable = false)
    public LignesEntity getLignesByIdligne() {
        return lignesByIdligne;
    }

    public void setLignesByIdligne(LignesEntity lignesByIdligne) {
        this.lignesByIdligne = lignesByIdligne;
    }
}
