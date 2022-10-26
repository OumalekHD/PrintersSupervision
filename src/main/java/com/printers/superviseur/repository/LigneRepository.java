package com.printers.superviseur.repository;

import com.printers.superviseur.entity.LignesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneRepository extends CrudRepository<LignesEntity, Long> {
    @Override
    List<LignesEntity> findAll();

    @Query("SELECT u FROM LignesEntity u")
    List<LignesEntity> findByAll();

    @Query("SELECT u FROM LignesEntity u Where u.idchantier=:id")
    LignesEntity findByChantier(@Param("id") int id);

    @Query("SELECT u FROM LignesEntity u Where u.idligne=:id")
    LignesEntity findActivatedLigneEntity(@Param("id") long id);

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
