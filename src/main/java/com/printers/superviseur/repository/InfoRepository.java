package com.printers.superviseur.repository;

import com.printers.superviseur.entity.InfosEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public interface InfoRepository extends CrudRepository<InfosEntity, Long> {

    @Override
    List<InfosEntity> findAll();

    @Query("SELECT i FROM InfosEntity i where i.serialnumber=:serial")
    InfosEntity findBySerialnumber(@Param("serial") long serial);

    @Query("SELECT count(i) FROM InfosEntity i")
    long NumberOfRows();

    @Query(value = "SELECT datedisp as dt, numberdisp as count from printersdisp order by datedisp ASC", nativeQuery = true)
    List ChartGetActifPrintersByDay();

    //@Query(value="SELECT i.dt as dt, (SELECT COUNT(DISTINCT serialnumber) from infos d inner join printersscans pr on d.idscan=pr.idscan where date(pr.datescan)=dt) from (select DISTINCT date(pr.datescan) as dt,f.idscan as idscan from infos f inner join printersscans pr on pr.idscan=f.idscan) i group by dt order by dt",nativeQuery = true)
    //List ChartGetActifPrintersByDay();

    @Query(value = "SELECT distinct modelmateriel,COUNT(*) as modelcount,(SELECT count(*) from materiels) as allcount from materiels m group by modelmateriel", nativeQuery = true)
    List ChartGetNbPrintersByType();

    @Query(value = "select '' as cpuusage,'' as totalram,'' as firmwareversion,max(idinfo) as idinfo,'' as partnumber,'' as serialnumber,'' as uptime,'' as ipadresse,'' as macadresse,'' as freememory, '' as freeram, '' as usedram, '' as usedmemory, '' as totalmemory,CAST(max(cast(faultydotsprinthead AS INTEGER)) AS TEXT) as faultydotsprinthead,cast(date(dateanalyse) as timestamp) as dateanalyse,'' as printheadtemp,'' as printedlabel,'' as numberofdots, '' as minprintheadtemp, '' as maxprintheadtemp, '' as totaldistanceprint, 1 as idscan from infos i where i.serialnumber=:serial group by cast(date(dateanalyse) as timestamp)  order by cast(date(dateanalyse) as timestamp) ASC", nativeQuery = true)
    List<InfosEntity> chartgetfaultystats(@Param("serial") String serial);

}
