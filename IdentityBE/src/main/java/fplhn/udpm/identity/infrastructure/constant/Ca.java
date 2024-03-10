package fplhn.udpm.identity.infrastructure.constant;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Slf4j
public enum Ca {
    CA1(LocalTime.of(7, 0), LocalTime.of(9, 15)),
    CA2(LocalTime.of(9, 15), LocalTime.of(11, 30)),
    CA3(LocalTime.of(11, 45), LocalTime.of(14, 0)),
    CA4(LocalTime.of(14, 0), LocalTime.of(16, 15)),
    CA5(LocalTime.of(16, 15), LocalTime.of(18, 30)),
    CA6(LocalTime.of(18, 15), LocalTime.of(20, 30)),
    CA7,
    CA8,
    CA9,
    CA10,

    CA_CHECK(LocalTime.of(20, 45), LocalTime.of(23, 59)),
    ;

    private LocalTime startTime;

    private LocalTime endTime;

    Ca(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    Ca() {
    }

    public static Ca getCurrentShift() {
        LocalTime now = LocalTime.now();
        log.info("now: {}", now);
        for (Ca shift : Ca.values()) {
            log.info("shift: {}", shift);
            if (shift.startTime != null && shift.endTime != null) {
                if (!now.isBefore(shift.startTime) && !now.isAfter(shift.endTime)) {
                    return shift;
                }
            }
        }
        return null;
    }

}
