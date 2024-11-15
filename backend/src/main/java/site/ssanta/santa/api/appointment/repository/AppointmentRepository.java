package site.ssanta.santa.api.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.appointment.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
