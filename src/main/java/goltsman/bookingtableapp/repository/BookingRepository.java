package goltsman.bookingtableapp.repository;

import goltsman.bookingtableapp.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    @Query("SELECT b FROM Booking b WHERE b.table.id = :tableId " +
            "AND b.status != 'CANCELLED' " +
            "AND ((b.startTime < :endTime AND b.endTime > :startTime))")
    List<Booking> findConflictingBookings(@Param("tableId") Long tableId,
                                          @Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);

    List<Booking> findAllByRestaurantId(Long restaurantId);
    List<Booking> findAllByUserId(Long userId);
}
