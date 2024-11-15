package site.ssanta.santa.api.appointment_participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.appointment_participant.domain.AppointmentParticipant;

public interface AppointmentParticipantRepository extends JpaRepository<AppointmentParticipant, Long> {
}
