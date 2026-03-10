package goltsman.bookingtableapp.repository;

import goltsman.bookingtableapp.model.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>,
        JpaSpecificationExecutor<Restaurant> {

    @EntityGraph(value = "Restaurant.cuisines", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Restaurant> findById(Long id);

    @EntityGraph(value = "Restaurant.cuisines", type = EntityGraph.EntityGraphType.LOAD)
    Page<Restaurant> findAll(Specification<Restaurant> spec, Pageable pageable);

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN r.cuisines c WHERE c.id = :cuisineId")
    List<Restaurant> findAllByCuisineId(@Param("cuisineId") Long cuisineId);
}
