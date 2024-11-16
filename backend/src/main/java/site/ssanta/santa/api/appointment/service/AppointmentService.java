package site.ssanta.santa.api.appointment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.ssanta.santa.api.appointment.domain.Appointment;
import site.ssanta.santa.api.appointment.dto.AppointmentJoinRequestDto;
import site.ssanta.santa.api.appointment.dto.MakeAppointmentRequestDto;
import site.ssanta.santa.api.appointment.repository.AppointmentRepository;
import site.ssanta.santa.api.mountain.domain.Mountain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Transactional
    public Appointment makeAppointment(Long userId, MakeAppointmentRequestDto dto, Mountain mountain) {
        return appointmentRepository.save(Appointment.builder()
                .groupId(dto.getGroupId())
                .adminId(userId)
                .mountainId(mountain.getId())
                .mountainName(mountain.getName())
                .meetAt(dto.getDate())
                .build());
    }

    @Transactional(readOnly = true)
    public List<Appointment> getMonthlyAppointments(Long groupId, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date start = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date end = calendar.getTime();

        log.debug("start: {}, end: {}", start, end);
        return appointmentRepository.findByGroupIdAndMeetAtBetweenWithParticipants(groupId, start, end);
    }

    @Transactional
    public Appointment join(AppointmentJoinRequestDto dto) {
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow();
        appointment.join();
        return appointment;
    }
}
