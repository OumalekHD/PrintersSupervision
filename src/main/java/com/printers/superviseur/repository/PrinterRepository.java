package com.printers.superviseur.repository;

import com.printers.superviseur.entity.PrintersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PrinterRepository extends CrudRepository<PrintersEntity, Long> {

    @Override
    List<PrintersEntity> findAll();

    @Query(value = "SELECT i.faultydotsprinthead,i.idscan,i.serialnumber,i.dateanalyse,i.partnumber,TO_CHAR(i.dateanalyse, 'DD/MM/YYYY hh:mm:ss') as totalram,i.cpuusage,i.firmwareversion,i.uptime,i.ipadresse,i.macadresse,i.freememory,i.freeram,i.usedram,i.usedmemory,i.totalmemory,i.printheadtemp,i.printedlabel,i.numberofdots,l.desligne as minprintheadtemp,c.deschantier as maxprintheadtemp,i.totaldistanceprint,s.desstation as lastping FROM Printers i inner join Materiels m ON m.ipmateriel=i.ipadresse inner join Lignes l ON l.idligne=m.idligne inner join chantiers c ON c.idchantier=l.idchantier inner join stations s ON s.idstation=c.idstation WHERE CAST(i.faultydotsprinthead AS Integer)>0 AND i.dateanalyse > (SELECT now() - INTERVAL '7 DAYS') ORDER BY CAST(i.faultydotsprinthead as Integer) DESC", nativeQuery = true)
    List<PrintersEntity> findDots();

    @Query(value = "SELECT count(*) FROM Printers i inner join Materiels m ON m.ipmateriel=i.ipadresse inner join Lignes l ON l.idligne=m.idligne inner join chantiers c ON c.idchantier=l.idchantier inner join stations s ON s.idstation=c.idstation WHERE CAST(i.faultydotsprinthead AS Integer)>0 AND i.dateanalyse > (SELECT now() - INTERVAL '7 DAYS')", nativeQuery = true)
    long findDotsCount();


    @Query("SELECT i FROM PrintersEntity i where i.serialnumber=:serial")
    List<PrintersEntity> findBySerial(@Param("serial") String serial);

    @Query(value = "SELECT i.idscan,i.ipadresse,i.faultydotsprinthead,i.serialnumber,m.modelmateriel as freeram,i.dateanalyse,i.partnumber,m.typemateriel as numberofdots,TO_CHAR(i.dateanalyse, 'DD/MM/YYYY HH24:MI:SS') as usedram,CAST(extract(epoch from (Now() - i.dateanalyse)/60) AS Integer) as freememory,i.cpuusage,i.firmwareversion,i.uptime,i.macadresse,i.freeram,i.usedram,i.usedmemory,i.totalram,i.totalmemory,i.printheadtemp,i.printedlabel,i.numberofdots,l.desligne as minprintheadtemp,c.deschantier as maxprintheadtemp,i.totaldistanceprint,s.desstation as lastping FROM (SELECT DISTINCT ON (ipadresse) * from printers order by ipadresse,dateanalyse desc) i inner join Materiels m ON m.ipmateriel=i.ipadresse inner join Lignes l ON l.idligne=m.idligne inner join chantiers c ON c.idchantier=l.idchantier inner join stations s ON s.idstation=c.idstation ORDER BY CAST(i.faultydotsprinthead as Integer) DESC", nativeQuery = true)
    List<PrintersEntity> SupPrintersAll();

    @Query(value = "SELECT count(i) as countactif,(SELECT count(s) FROM Printers s) as countall FROM Printers i where i.dateanalyse > Now() - interval '7 days'", nativeQuery = true)
    String[] findActif();

    @Query(value = "SELECT count(i),(SELECT count(s) FROM Printers s where s.dateanalyse > Now() - interval '7 days') as countall FROM Printers i WHERE CAST(i.faultydotsprinthead AS Integer) > 0", nativeQuery = true)
    String[] findDefPrinthead();

    @Query(value = "SELECT count(i),(SELECT count(s) FROM (SELECT DISTINCT ON (ipadresse) * from printers order by ipadresse,dateanalyse desc) s) as countall  FROM (SELECT DISTINCT ON (ipadresse) * from printers order by ipadresse,dateanalyse desc) i WHERE CAST(extract(epoch from (Now() - i.dateanalyse)/60) AS Integer)<7", nativeQuery = true)
    String[] PingPrinters();

    @Query(value = "SELECT p from PrintersEntity p order by p.serialnumber ASC")
    List<PrintersEntity> dashboardPrinters();

}
