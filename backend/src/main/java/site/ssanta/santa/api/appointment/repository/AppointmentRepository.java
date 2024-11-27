package site.ssanta.santa.api.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.ssanta.santa.api.appointment.domain.Appointment;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT DISTINCT a FROM Appointment a " +
            "LEFT JOIN FETCH a.participants p " +
            "LEFT JOIN FETCH p.member m " +
            "WHERE a.groupId = :groupId " +
            "AND a.meetAt BETWEEN :start AND :end")
    List<Appointment> findByGroupIdAndMeetAtBetweenWithParticipants(
            @Param("groupId") Long groupId,
            @Param("start") Date start,
            @Param("end") Date end);
}
