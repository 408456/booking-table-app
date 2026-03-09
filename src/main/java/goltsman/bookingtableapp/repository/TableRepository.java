package goltsman.bookingtableapp.repository;

import goltsman.bookingtableapp.model.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TableRepository extends JpaRepository<TableEntity, Long>, JpaSpecificationExecutor<TableEntity> {

}
