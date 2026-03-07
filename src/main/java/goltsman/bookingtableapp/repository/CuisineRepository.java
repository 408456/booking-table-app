package goltsman.bookingtableapp.repository;

import goltsman.bookingtableapp.model.entity.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

    Optional<Cuisine> findByName(String name);

    boolean existsByName(String name);

}