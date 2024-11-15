package site.ssanta.santa.api.appointment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.appointment.domain.Appointment;
import site.ssanta.santa.api.appointment.dto.MakeAppointmentRequestDto;
import site.ssanta.santa.api.appointment.repository.AppointmentRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Transactional
    public Appointment makeAppointment(Long userId, MakeAppointmentRequestDto dto) {
        return appointmentRepository.save(Appointment.builder()
                .groupId(dto.getGroupId())
                .adminId(userId)
                .meetAt(dto.getDate())
                .build());
    }

    @Transactional(readOnly = true)
    public List<Appointment> getMonthlyAppointments(Long groupId, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date start = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date end = calendar.getTime();

        return appointmentRepository.findByGroupIdAndMeetAtBetweenWithParticipants(groupId, start, end);
    }
}
