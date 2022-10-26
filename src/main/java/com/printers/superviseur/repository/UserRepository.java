package com.printers.superviseur.repository;

import com.printers.superviseur.entity.UtilisateursEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UtilisateursEntity, Long> {
    @Override
    List<UtilisateursEntity> findAll();

    @Query("SELECT u FROM UtilisateursEntity u")
    List<UtilisateursEntity> findByEtat();

    @Query("SELECT u FROM UtilisateursEntity u WHERE u.mailuser=:username")
    List<UtilisateursEntity> findByUsername(@Param("username") String username);

    @Query("SELECT count(u) as countRows FROM UtilisateursEntity u")
    Long countRows();

    @Query("SELECT u FROM UtilisateursEntity u WHERE u.iduser=:id_utilisateur")
    List<UtilisateursEntity> findById(@Param("id_utilisateur") long id);

    @Query("SELECT u FROM UtilisateursEntity u WHERE u.iduser=:id_utilisateur")
    UtilisateursEntity findByIdEntity(@Param("id_utilisateur") long id);

    @Query("SELECT COUNT(u) FROM UtilisateursEntity u WHERE u.iduser <> :id and u.mailuser = :mail")
    long CountfindByEmailAndId(@Param("id") long id, @Param("mail") String mail);

    @Query("SELECT COUNT(u) FROM UtilisateursEntity u WHERE u.mailuser = :mail")
    long findByEmail(@Param("mail") String mail);
}
