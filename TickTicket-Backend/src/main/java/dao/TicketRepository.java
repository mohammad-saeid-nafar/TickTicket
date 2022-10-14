package dao;

import model.Ticket;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface TicketRepository extends CrudRepository<Ticket, UUID> {
}