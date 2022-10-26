package com.printers.superviseur.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "menus", schema = "public", catalog = "PrinterSup")
public class MenusEntity {
    private long idmenu;
    private int idprofile;
    private String desmenu;
    private int accesmenu;
    private int addmenu;
    private int editmenu;
    private int deletemenu;
    private int exportmenu;
    private ProfilesEntity profilesByIdprofile;

    @Id
    @Column(name = "idmenu", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getIdmenu() {
        return idmenu;
    }

    public void setIdmenu(long idmenu) {
        this.idmenu = idmenu;
    }

    @Basic
    @Column(name = "idprofile", nullable = false, insertable = false, updatable = false)
    public int getIdprofile() {
        return idprofile;
    }

    public void setIdprofile(int idprofile) {
        this.idprofile = idprofile;
    }

    @Basic
    @Column(name = "desmenu", nullable = false, length = 30)
    public String getDesmenu() {
        return desmenu;
    }

    public void setDesmenu(String desmenu) {
        this.desmenu = desmenu;
    }

    @Basic
    @Column(name = "accesmenu", nullable = false)
    public int getAccesmenu() {
        return accesmenu;
    }

    public void setAccesmenu(int accesmenu) {
        this.accesmenu = accesmenu;
    }

    @Basic
    @Column(name = "addmenu", nullable = false)
    public int getAddmenu() {
        return addmenu;
    }

    public void setAddmenu(int addmenu) {
        this.addmenu = addmenu;
    }

    @Basic
    @Column(name = "editmenu", nullable = false)
    public int getEditmenu() {
        return editmenu;
    }

    public void setEditmenu(int editmenu) {
        this.editmenu = editmenu;
    }

    @Basic
    @Column(name = "deletemenu", nullable = false)
    public int getDeletemenu() {
        return deletemenu;
    }

    public void setDeletemenu(int deletemenu) {
        this.deletemenu = deletemenu;
    }

    @Basic
    @Column(name = "exportmenu", nullable = false)
    public int getExportmenu() {
        return exportmenu;
    }

    public void setExportmenu(int exportmenu) {
        this.exportmenu = exportmenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenusEntity that = (MenusEntity) o;
        return idmenu == that.idmenu &&
                idprofile == that.idprofile &&
                Objects.equals(accesmenu, that.accesmenu) &&
                Objects.equals(addmenu, that.addmenu) &&
                Objects.equals(editmenu, that.editmenu) &&
                Objects.equals(deletemenu, that.deletemenu) &&
                Objects.equals(exportmenu, that.exportmenu) &&
                Objects.equals(desmenu, that.desmenu);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idmenu, idprofile, desmenu, accesmenu, addmenu, editmenu, deletemenu, exportmenu);
    }

    @ManyToOne
    @JoinColumn(name = "idprofile", referencedColumnName = "idprofile", nullable = false)
    public ProfilesEntity getProfilesByIdprofile() {
        return profilesByIdprofile;
    }

    public void setProfilesByIdprofile(ProfilesEntity profilesByIdprofile) {
        this.profilesByIdprofile = profilesByIdprofile;
    }
}
