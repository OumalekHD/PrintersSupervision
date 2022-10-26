package com.printers.superviseur.repository;

import com.printers.superviseur.entity.MaterielsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterielRepository extends CrudRepository<MaterielsEntity, Long> {
    @Override
    List<MaterielsEntity> findAll();

    @Query("SELECT u FROM MaterielsEntity u where u.etatmateriel=:state")
    List<MaterielsEntity> findByActif(@Param("state") int state);

    @Query("SELECT count(u) FROM MaterielsEntity u WHERE u.ipmateriel=:ip")
    Long countfindByIp(@Param("ip") String ip);

    @Query("SELECT u FROM MaterielsEntity u WHERE u.idmateriel=:id")
    List<MaterielsEntity> findById(@Param("id") long id);

    @Query("SELECT m FROM MaterielsEntity m WHERE m.idligne=:id order by m.typemateriel DESC")
    List<MaterielsEntity> findAllPrintersByLigne(@Param("id") int id);

    @Query("SELECT u FROM MaterielsEntity u WHERE u.idmateriel=:id")
    MaterielsEntity findByIdEntity(@Param("id") long id);

    @Query("SELECT COUNT(u) FROM MaterielsEntity u WHERE u.idmateriel <> :id and u.ipmateriel = :ip")
    long CountfindByIpAndId(@Param("id") long id, @Param("ip") String ip);


    /*@Query("SELECT count(u) as countRows FROM UtilisateursEntity u")
    Long countRows();

    @Query("SELECT u FROM UtilisateursEntity u WHERE u.iduser=:id_utilisateur")
    List<UtilisateursEntity> findById(@Param("id_utilisateur") long id);

    @Query("SELECT u FROM UtilisateursEntity u WHERE u.iduser=:id_utilisateur")
    UtilisateursEntity findByIdEntity(@Param("id_utilisateur") long id);

    @Query("SELECT COUNT(u) FROM UtilisateursEntity u WHERE u.iduser <> :id and u.mailuser = :mail")
    long CountfindByEmailAndId(@Param("id") long id, @Param("mail") String mail);

    @Query("SELECT COUNT(u) FROM UtilisateursEntity u WHERE u.mailuser = :mail")
    long findByEmail(@Param("mail") String mail);*/
}
