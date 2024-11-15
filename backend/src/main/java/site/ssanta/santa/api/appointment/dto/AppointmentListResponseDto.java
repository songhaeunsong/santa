package site.ssanta.santa.api.appointment.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AppointmentListResponseDto {

    @ArraySchema(schema = @Schema(implementation = AppointmentResponseDto.class))
    List<AppointmentResponseDto> appointments;
}
