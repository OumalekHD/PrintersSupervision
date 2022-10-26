package com.printers.superviseur.repository;

import com.printers.superviseur.entity.ChantiersEntity;
import com.printers.superviseur.entity.StationsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChantierRepository extends CrudRepository<ChantiersEntity, Long> {
    @Override
    List<ChantiersEntity> findAll();

    @Query("SELECT c FROM ChantiersEntity c where c.idstation=:id")
    ChantiersEntity findByStation(@Param("id") int id);

}
