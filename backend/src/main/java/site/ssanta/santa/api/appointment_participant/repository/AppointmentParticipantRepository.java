package site.ssanta.santa.api.appointment_participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ssanta.santa.api.appointment.domain.Appointment;
import site.ssanta.santa.api.appointment_participant.domain.AppointmentParticipant;
import site.ssanta.santa.api.member.domain.Member;

public interface AppointmentParticipantRepository extends JpaRepository<AppointmentParticipant, Long> {

    void deleteByAppointmentAndMember(Appointment appointment, Member member);
}
