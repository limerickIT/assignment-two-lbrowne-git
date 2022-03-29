package com.sd4.repository;

import com.sd4.model.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

    Page<Beer> findBeerByNameLikeIgnoreCase (String Name, Pageable pageable);
    Page<Beer> findBeerById(Long Id, Pageable pageable);
}