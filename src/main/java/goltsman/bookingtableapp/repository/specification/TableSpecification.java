package goltsman.bookingtableapp.repository.specification;


import goltsman.bookingtableapp.model.entity.TableEntity;
import org.springframework.data.jpa.domain.Specification;

public class TableSpecification {

    public static Specification<TableEntity> byRestaurantId(Long restaurantId) {
        return (root, query, cb) -> {
            if (restaurantId == null) return cb.conjunction();
            return cb.equal(root.get("restaurant").get("id"), restaurantId);
        };
    }

    public static Specification<TableEntity> byMinSeats(Integer minSeats) {
        return (root, query, cb) -> {
            if (minSeats == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("seats"), minSeats);
        };
    }

    public static Specification<TableEntity> byMaxSeats(Integer maxSeats) {
        return (root, query, cb) -> {
            if (maxSeats == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("seats"), maxSeats);
        };
    }

    public static Specification<TableEntity> byIsAvailable(Boolean isAvailable) {
        return (root, query, cb) -> {
            if (isAvailable == null) return cb.conjunction();
            return cb.equal(root.get("isAvailable"), isAvailable);
        };
    }
}
