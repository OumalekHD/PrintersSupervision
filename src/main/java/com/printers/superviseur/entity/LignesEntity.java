package com.printers.superviseur.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "lignes", schema = "public", catalog = "PrinterSup")
public class LignesEntity {
    private long idligne;
    private int idchantier;
    private String desligne;
    private String descligne;
    private ChantiersEntity chantiersByIdchantier;
    private Collection<MaterielsEntity> materielsByIdligne;

    @Id
    @Column(name = "idligne", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getIdligne() {
        return idligne;
    }

    public void setIdligne(long idligne) {
        this.idligne = idligne;
    }

    @Basic
    @Column(name = "idchantier", nullable = false, insertable = false, updatable = false)
    public int getIdchantier() {
        return idchantier;
    }

    public void setIdchantier(int idchantier) {
        this.idchantier = idchantier;
    }

    @Basic
    @Column(name = "desligne", nullable = false, length = 30)
    public String getDesligne() {
        return desligne;
    }

    public void setDesligne(String desligne) {
        this.desligne = desligne;
    }

    @Basic
    @Column(name = "descligne", nullable = true, length = 256)
    public String getDescligne() {
        return descligne;
    }

    public void setDescligne(String descligne) {
        this.descligne = descligne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LignesEntity that = (LignesEntity) o;
        return idligne == that.idligne &&
                idchantier == that.idchantier &&
                Objects.equals(desligne, that.desligne) &&
                Objects.equals(descligne, that.descligne);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idligne, idchantier, desligne, descligne);
    }

    @ManyToOne
    @JoinColumn(name = "idchantier", referencedColumnName = "idchantier", nullable = false)
    public ChantiersEntity getChantiersByIdchantier() {
        return chantiersByIdchantier;
    }

    public void setChantiersByIdchantier(ChantiersEntity chantiersByIdchantier) {
        this.chantiersByIdchantier = chantiersByIdchantier;
    }

    @OneToMany(mappedBy = "lignesByIdligne")
    public Collection<MaterielsEntity> getMaterielsByIdligne() {
        return materielsByIdligne;
    }

    public void setMaterielsByIdligne(Collection<MaterielsEntity> materielsByIdligne) {
        this.materielsByIdligne = materielsByIdligne;
    }
}
