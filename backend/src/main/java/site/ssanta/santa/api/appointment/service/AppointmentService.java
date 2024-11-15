package site.ssanta.santa.api.appointment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.appointment.domain.Appointment;
import site.ssanta.santa.api.appointment.dto.MakeAppointmentRequestDto;
import site.ssanta.santa.api.appointment.repository.AppointmentRepository;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public Long makeAppointment(Long userId, MakeAppointmentRequestDto dto) {
        Appointment result = appointmentRepository.save(Appointment.builder()
                .groupId(dto.getGroupId())
                .adminId(userId)
                .meetAt(dto.getDate())
                .build());

        return result.getId();
    }
}
