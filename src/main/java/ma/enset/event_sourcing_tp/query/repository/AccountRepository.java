package ma.enset.event_sourcing_tp.query.repository;

import ma.enset.event_sourcing_tp.query.entites.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}

