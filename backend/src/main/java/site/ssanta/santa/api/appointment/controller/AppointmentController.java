package site.ssanta.santa.api.appointment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ssanta.santa.api.appointment.domain.Appointment;
import site.ssanta.santa.api.appointment.dto.*;
import site.ssanta.santa.api.appointment.service.AppointmentService;
import site.ssanta.santa.api.appointment_participant.service.AppointmentParticipantService;
import site.ssanta.santa.api.group_participant.domain.Role;
import site.ssanta.santa.api.member.domain.Member;
import site.ssanta.santa.api.member.service.MemberService;
import site.ssanta.santa.api.mountain.domain.Mountain;
import site.ssanta.santa.api.mountain.service.MountainService;
import site.ssanta.santa.common.exception.ExceptionResponse;
import site.ssanta.santa.common.jwt.JwtUtil;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment")
@Tag(name = "Appointment API", description = "Appointment API")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentParticipantService appointmentParticipantService;
    private final MemberService memberService;
    private final MountainService mountainService;
    private final JwtUtil jwtUtil;

    @PostMapping()
    @SecurityRequirement(name = "ACCESS")
    @Operation(summary = "약속 생성", description = "그룹에 약속을 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성"),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
                    ))
    })
    public ResponseEntity<?> makeAppointment(@RequestAttribute("userId") Long userId,
                                             @RequestBody MakeAppointmentRequestDto dto) {
        Mountain mountain = mountainService.findById(dto.getMountainId());
        Appointment appointment = appointmentService.makeAppointment(userId, dto, mountain);
        Member user = memberService.getMemberById(userId);
        appointmentParticipantService.join(appointment, user, Role.ADMIN);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/list")
    @Operation(summary = "약속 조회", description = "그룹에 약속을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(schema = @Schema(implementation = AppointmentListResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
                    ))
    })
    public ResponseEntity<?> getAppointments(@CookieValue(value = "access_token", required = false) String accessToken,
                                             @RequestParam("groupId") Long groupId,
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                             @RequestParam("date") Date date) {
        Long currentId;
        if (accessToken != null) {
            currentId = jwtUtil.getUserId(accessToken);
        } else {
            currentId = null;
        }

        List<Appointment> appointments = appointmentService.getMonthlyAppointments(groupId, date);
        log.debug("count of result: {}", appointments.size());
        List<AppointmentResponseDto> responseDtoList = appointments.stream()
                .map(appointment -> {
                    List<ParticipantInfo> participants = appointment.getParticipants()
                            .stream()
                            .map(participant -> ParticipantInfo.builder()
                                    .id(participant.getMember().getId())
                                    .name(participant.getMember().getNickname())
                                            .build())
                            .toList();

                    boolean isMember = currentId != null &&
                            participants.stream().anyMatch(p -> p.getId().equals(currentId));

                    boolean isAdmin = currentId != null &&
                            appointment.getParticipants().stream().anyMatch(p ->
                                    p.getId().equals(currentId) && p.getRole().equals(Role.ADMIN));

                    return AppointmentResponseDto.builder()
                            .id(appointment.getId())
                            .countOfMembers((long) participants.size())
                            .meetAt(appointment.getMeetAt())
                            .mountainName(appointment.getMountainName())
                            .isMember(isMember)
                            .isAdmin(isAdmin)
                            .members(participants)
                            .build();
                })
                .toList();

        AppointmentListResponseDto result = new AppointmentListResponseDto(responseDtoList);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/join")
    @SecurityRequirement(name = "ACCESS")
    @Operation(summary = "약속 참가", description = "약속 참가 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "참가 성공"),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
                    )),
            @ApiResponse(responseCode = "404", description = "해당 약속이 없는 경우")
    })
    public ResponseEntity<?> joinAppointment(@RequestAttribute("userId") Long userId,
                                             @RequestBody AppointmentJoinRequestDto dto) {
        Member member = memberService.getMemberById(userId);
        Appointment appointment = appointmentService.join(dto);
        appointmentParticipantService.join(appointment, member, Role.PARTICIPANT);

        return ResponseEntity.created(null).build();
    }

    @DeleteMapping()
    @SecurityRequirement(name = "ACCESS")
    @Operation(summary = "약속 탈퇴", description = "약속 탈퇴 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "access token이 만료된 경우",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)
                    )),
            @ApiResponse(responseCode = "404", description = "해당 약속이 없는 경우")
    })
    public ResponseEntity<?> deleteFromAppointment(@RequestAttribute("userId") Long userId,
                                                   @RequestBody AppointmentDeleteRequestDto dto) {
        Member member = memberService.getMemberById(userId);
        Appointment appointment = appointmentService.findById(dto.getAppointmentId());
        appointmentParticipantService.delete(member, appointment);
        appointmentService.deleteMember(appointment.getId());

        return ResponseEntity.created(null).build();
    }
}
