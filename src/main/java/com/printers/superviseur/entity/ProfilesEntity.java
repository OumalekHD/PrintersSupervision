package com.printers.superviseur.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "profiles", schema = "public", catalog = "PrinterSup")
public class ProfilesEntity {
    private long idprofile;
    private String desprofile;
    private String descprofile;
    private int etat;
    private int usercounter;
    private Collection<MenusEntity> menusByIdprofile;
    private Collection<UtilisateursEntity> utilisateursByIdprofile;

    @Id
    @Column(name = "idprofile", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getIdprofile() {
        return idprofile;
    }

    public void setIdprofile(long idprofile) {
        this.idprofile = idprofile;
    }

    @Basic
    @Column(name = "desprofile", nullable = false, length = 30)
    public String getDesprofile() {
        return desprofile;
    }

    public void setDesprofile(String desprofile) {
        this.desprofile = desprofile;
    }

    @Basic
    @Column(name = "descprofile", nullable = true, length = 256)
    public String getDescprofile() {
        return descprofile;
    }

    public void setDescprofile(String descprofile) {
        this.descprofile = descprofile;
    }

    @Basic
    @Column(name = "etat", nullable = false)
    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Basic
    @Column(name = "usercounter", nullable = false)
    public int getUsercounter() {
        return usercounter;
    }

    public void setUsercounter(int usercounter) {
        this.usercounter = usercounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfilesEntity that = (ProfilesEntity) o;
        return idprofile == that.idprofile &&
                Objects.equals(desprofile, that.desprofile) &&
                Objects.equals(etat, that.etat) &&
                Objects.equals(usercounter, that.usercounter) &&
                Objects.equals(descprofile, that.descprofile);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idprofile, desprofile, descprofile, etat, usercounter);
    }

    @OneToMany(mappedBy = "profilesByIdprofile")
    public Collection<MenusEntity> getMenusByIdprofile() {
        return menusByIdprofile;
    }

    public void setMenusByIdprofile(Collection<MenusEntity> menusByIdprofile) {
        this.menusByIdprofile = menusByIdprofile;
    }

    @OneToMany(mappedBy = "profilesByIdprofile")
    public Collection<UtilisateursEntity> getUtilisateursByIdprofile() {
        return utilisateursByIdprofile;
    }

    public void setUtilisateursByIdprofile(Collection<UtilisateursEntity> utilisateursByIdprofile) {
        this.utilisateursByIdprofile = utilisateursByIdprofile;
    }
}
