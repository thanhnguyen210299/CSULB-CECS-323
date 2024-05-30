package model;

import java.time.LocalTime;
import java.util.Objects;

import jakarta.persistence.*;

@Entity(name = "TIMESLOTS")
//TimeSlot: unique (daysOfWeek, startTime, endTime)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "daysOfWeek", "startTime", "endTime"
        })})
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeSlotId;

    @Column(nullable = false)
    private byte daysOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public TimeSlot() {
    }

    public TimeSlot(byte daysOfWeek, LocalTime startTime, LocalTime endTime) {
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public byte getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(byte daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
