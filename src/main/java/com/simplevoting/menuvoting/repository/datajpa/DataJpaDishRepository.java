package com.simplevoting.menuvoting.repository.datajpa;

import com.simplevoting.menuvoting.model.Dish;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public interface DataJpaDishRepository extends JpaRepository<Dish, Integer> {
    @Transactional
    @Override
    Dish save(Dish dish);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);

    Optional<Dish> findById(Integer id);

    @Override
    List<Dish> findAll(Sort sort);
}
