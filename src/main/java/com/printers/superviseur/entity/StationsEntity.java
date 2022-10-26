package com.printers.superviseur.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "stations", schema = "public", catalog = "PrinterSup")
public class StationsEntity {
    private long idstation;
    private String desstation;
    private String descstation;
    private Collection<ChantiersEntity> chantiersByIdstation;

    @Id
    @Column(name = "idstation", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getIdstation() {
        return idstation;
    }

    public void setIdstation(long idstation) {
        this.idstation = idstation;
    }

    @Basic
    @Column(name = "desstation", nullable = false, length = 30)
    public String getDesstation() {
        return desstation;
    }

    public void setDesstation(String desstation) {
        this.desstation = desstation;
    }

    @Basic
    @Column(name = "descstation", nullable = true, length = 256)
    public String getDescstation() {
        return descstation;
    }

    public void setDescstation(String descstation) {
        this.descstation = descstation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationsEntity that = (StationsEntity) o;
        return idstation == that.idstation &&
                Objects.equals(desstation, that.desstation) &&
                Objects.equals(descstation, that.descstation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idstation, desstation, descstation);
    }

    @OneToMany(mappedBy = "stationsByIdstation")
    public Collection<ChantiersEntity> getChantiersByIdstation() {
        return chantiersByIdstation;
    }

    public void setChantiersByIdstation(Collection<ChantiersEntity> chantiersByIdstation) {
        this.chantiersByIdstation = chantiersByIdstation;
    }
}
