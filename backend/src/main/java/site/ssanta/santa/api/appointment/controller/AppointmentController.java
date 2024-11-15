package site.ssanta.santa.api.appointment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ssanta.santa.api.appointment.dto.MakeAppointmentRequestDto;
import site.ssanta.santa.api.appointment.service.AppointmentService;
import site.ssanta.santa.api.appointment_participant.service.AppointmentParticipantService;
import site.ssanta.santa.api.group_participant.domain.Role;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentParticipantService appointmentParticipantService;

    @PostMapping()
    public ResponseEntity<?> makeAppointment(@RequestAttribute("userId") Long userId,
                                             @RequestBody MakeAppointmentRequestDto dto) {
        Long appointmentId = appointmentService.makeAppointment(userId, dto);
        appointmentParticipantService.join(appointmentId, userId, Role.ADMIN);
        return ResponseEntity.created(null).build();
    }
}
