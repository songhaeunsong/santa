package site.ssanta.santa.api.appointment_participant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.appointment_participant.domain.AppointmentParticipant;
import site.ssanta.santa.api.appointment_participant.repository.AppointmentParticipantRepository;
import site.ssanta.santa.api.group_participant.domain.Role;

@Service
@RequiredArgsConstructor
public class AppointmentParticipantService {

    private final AppointmentParticipantRepository repository;

    public void join(Long appointmentId, Long userId, Role role) {
        repository.save(AppointmentParticipant.builder()
                .appointmentId(appointmentId)
                .memberId(userId)
                .role(role)
                .build());
    }
}
