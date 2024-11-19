package site.ssanta.santa.api.appointment_participant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.appointment.domain.Appointment;
import site.ssanta.santa.api.appointment_participant.domain.AppointmentParticipant;
import site.ssanta.santa.api.appointment_participant.repository.AppointmentParticipantRepository;
import site.ssanta.santa.api.group_participant.domain.Role;
import site.ssanta.santa.api.member.domain.Member;

@Service
@RequiredArgsConstructor
public class AppointmentParticipantService {

    private final AppointmentParticipantRepository repository;

    @Transactional
    public void join(Appointment appointment, Member user, Role role) {
        repository.save(AppointmentParticipant.builder()
                .appointment(appointment)
                .member(user)
                .role(role)
                .build());
    }

    @Transactional
    public void delete(Member member, Appointment appointment) {
        repository.deleteByAppointmentAndMember(appointment, member);
    }
}
