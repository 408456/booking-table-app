package goltsman.bookingtableapp.repository;

import goltsman.bookingtableapp.model.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TableRepository extends JpaRepository<TableEntity, Long>, JpaSpecificationExecutor<TableEntity> {
    List<TableEntity> findAllByRestaurantId(Long restaurantId);

    @Query("SELECT t FROM TableEntity t WHERE t.restaurant.id = :restaurantId AND t.isAvailable = true AND NOT EXISTS (" +
            "SELECT b FROM Booking b WHERE b.table.id = t.id AND b.status != 'CANCELLED' " +
            "AND (b.startTime < :endTime AND b.endTime > :startTime))")
    List<TableEntity> findAvailableTables(@Param("restaurantId") Long restaurantId,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);
}
