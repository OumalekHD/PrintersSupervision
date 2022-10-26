package com.printers.superviseur.repository;

import com.printers.superviseur.entity.MenusEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MenuRepository extends CrudRepository<MenusEntity, Long> {

    @Query(value = "SELECT DISTINCT on (m.desmenu) m.* FROM menus m order by m.desmenu DESC", nativeQuery = true)
    List<MenusEntity> findAllMenus();

    @Query("Select m from MenusEntity m where m.idprofile=:idprofile")
    List<MenusEntity> findById(@Param("idprofile") int profile);

    @Transactional
    @Modifying
    @Query("Delete from MenusEntity m where m.idprofile=:idprofile")
    void deletebyprofile(@Param("idprofile") int idprofile);
}
