package com.printers.superviseur.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "utilisateurs", schema = "public", catalog = "PrinterSup")
public class UtilisateursEntity {
    private long iduser;
    private int idprofile;
    private String nomuser;
    private String prenomuser;
    private String mailuser;
    private String passuser;
    private String teluser;
    private String descriptionuser;
    private int etat;
    private ProfilesEntity profilesByIdprofile;

    @Id
    @Column(name = "iduser", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getIduser() {
        return iduser;
    }

    public void setIduser(long iduser) {
        this.iduser = iduser;
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
    @Column(name = "nomuser", nullable = false, length = 20)
    public String getNomuser() {
        return nomuser;
    }

    public void setNomuser(String nomuser) {
        this.nomuser = nomuser;
    }

    @Basic
    @Column(name = "prenomuser", nullable = false, length = 20)
    public String getPrenomuser() {
        return prenomuser;
    }

    public void setPrenomuser(String prenomuser) {
        this.prenomuser = prenomuser;
    }

    @Basic
    @Column(name = "mailuser", nullable = false, length = 30)
    public String getMailuser() {
        return mailuser;
    }

    public void setMailuser(String mailuser) {
        this.mailuser = mailuser;
    }

    @Basic
    @Column(name = "passuser", nullable = false, length = 256)
    public String getPassuser() {
        return passuser;
    }

    public void setPassuser(String passuser) {
        this.passuser = passuser;
    }

    @Basic
    @Column(name = "teluser", nullable = true, length = 10)
    public String getTeluser() {
        return teluser;
    }

    public void setTeluser(String teluser) {
        this.teluser = teluser;
    }

    @Basic
    @Column(name = "descriptionuser", nullable = true, length = 256)
    public String getDescriptionuser() {
        return descriptionuser;
    }

    public void setDescriptionuser(String descriptionuser) {
        this.descriptionuser = descriptionuser;
    }

    @Basic
    @Column(name = "etat", nullable = false)
    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilisateursEntity that = (UtilisateursEntity) o;
        return iduser == that.iduser &&
                idprofile == that.idprofile &&
                Objects.equals(nomuser, that.nomuser) &&
                Objects.equals(prenomuser, that.prenomuser) &&
                Objects.equals(mailuser, that.mailuser) &&
                Objects.equals(passuser, that.passuser) &&
                Objects.equals(teluser, that.teluser) &&
                Objects.equals(etat, that.etat) &&
                Objects.equals(descriptionuser, that.descriptionuser);
    }

    @Override
    public int hashCode() {

        return Objects.hash(iduser, idprofile, nomuser, prenomuser, mailuser, passuser, teluser, etat, descriptionuser);
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
