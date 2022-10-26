package com.printers.superviseur.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "chantiers", schema = "public", catalog = "PrinterSup")
public class ChantiersEntity {
    private long idchantier;
    private int idstation;
    private String deschantier;
    private String descchantier;
    private StationsEntity stationsByIdstation;
    private Collection<LignesEntity> lignesByIdchantier;

    @Id
    @Column(name = "idchantier", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getIdchantier() {
        return idchantier;
    }

    public void setIdchantier(long idchantier) {
        this.idchantier = idchantier;
    }

    @Basic
    @Column(name = "idstation", nullable = false, insertable = false, updatable = false)
    public int getIdstation() {
        return idstation;
    }

    public void setIdstation(int idstation) {
        this.idstation = idstation;
    }

    @Basic
    @Column(name = "deschantier", nullable = false, length = 30)
    public String getDeschantier() {
        return deschantier;
    }

    public void setDeschantier(String deschantier) {
        this.deschantier = deschantier;
    }

    @Basic
    @Column(name = "descchantier", nullable = true, length = 256)
    public String getDescchantier() {
        return descchantier;
    }

    public void setDescchantier(String descchantier) {
        this.descchantier = descchantier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChantiersEntity that = (ChantiersEntity) o;
        return idchantier == that.idchantier &&
                idstation == that.idstation &&
                Objects.equals(deschantier, that.deschantier) &&
                Objects.equals(descchantier, that.descchantier);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idchantier, idstation, deschantier, descchantier);
    }

    @ManyToOne
    @JoinColumn(name = "idstation", referencedColumnName = "idstation", nullable = false)
    public StationsEntity getStationsByIdstation() {
        return stationsByIdstation;
    }

    public void setStationsByIdstation(StationsEntity stationsByIdstation) {
        this.stationsByIdstation = stationsByIdstation;
    }

    @OneToMany(mappedBy = "chantiersByIdchantier")
    public Collection<LignesEntity> getLignesByIdchantier() {
        return lignesByIdchantier;
    }

    public void setLignesByIdchantier(Collection<LignesEntity> lignesByIdchantier) {
        this.lignesByIdchantier = lignesByIdchantier;
    }
}
