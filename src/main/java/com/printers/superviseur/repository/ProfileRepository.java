package com.printers.superviseur.repository;

import com.printers.superviseur.entity.ProfilesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProfileRepository extends CrudRepository<ProfilesEntity, Long> {

    @Override
    List<ProfilesEntity> findAll();

    @Query("Select p from ProfilesEntity p where p.desprofile=:profile and p.etat=1")
    ProfilesEntity findActivatedProfileEntity(@Param("profile") String profile);

    @Query("Select p from ProfilesEntity p where p.idprofile=:idprofile")
    ProfilesEntity findId(@Param("idprofile") long profile);

    @Query("Select p from ProfilesEntity p where p.idprofile=:idprofile")
    List<ProfilesEntity> findById(@Param("idprofile") long profile);

    @Query("Select COUNT(p) from ProfilesEntity p where p.desprofile=:profile")
    Long findProfileByDes(@Param("profile") String profile);

    @Query(value = "SELECT p.idprofile,desprofile,descprofile,p.etat,(SELECT count(*) from utilisateurs u where u.idprofile=p.idprofile) as usercounter from profiles p", nativeQuery = true)
    List<ProfilesEntity> findAllbyCount();

    @Query("SELECT COUNT(p) FROM ProfilesEntity p WHERE p.idprofile <> :id and p.desprofile = :profile")
    long CountfindByProfile(@Param("id") long id, @Param("profile") String profile);
}
