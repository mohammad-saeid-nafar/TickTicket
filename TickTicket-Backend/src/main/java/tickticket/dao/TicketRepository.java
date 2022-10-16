package tickticket.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tickticket.model.Ticket;

import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, UUID> {
}