package com.printers.superviseur.repository;

import com.printers.superviseur.entity.PrintersscansEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PrintersscanRepository extends CrudRepository<PrintersscansEntity, Long> {
    @Override
    List<PrintersscansEntity> findAll();

    @Query("SELECT p FROM PrintersscansEntity p WHERE p.datescan=:date")
    PrintersscansEntity findDate(@Param("date") Timestamp date);

}
