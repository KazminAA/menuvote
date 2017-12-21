package com.simplevoting.menuvoting.repository.datajpa;

import com.simplevoting.menuvoting.model.MenuList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public interface DataJpaMenuListRepository extends JpaRepository<MenuList, Integer> {

    @Transactional
    @Override
    MenuList save(MenuList dish);

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuList ml WHERE ml.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT ml FROM MenuList ml WHERE ml.menu.id=:id")
    List<MenuList> findAllByMenu_Id(@Param("id") int menu_id);

}
