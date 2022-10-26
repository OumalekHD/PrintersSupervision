package com.printers.superviseur.repository;

import com.printers.superviseur.entity.StationsEntity;
import com.printers.superviseur.entity.UtilisateursEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationRepository extends CrudRepository<StationsEntity, Long> {
    @Override
    List<StationsEntity> findAll();

    @Query("SELECT s FROM StationsEntity s")
    StationsEntity findAllStation();

}
