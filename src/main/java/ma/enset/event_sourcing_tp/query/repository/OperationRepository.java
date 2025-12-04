package ma.enset.event_sourcing_tp.query.repository;

import ma.enset.event_sourcing_tp.query.entites.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByAccountId(String id);

}

