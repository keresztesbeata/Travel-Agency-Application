package service.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VacationPackageDTO {
    private String vacationDestinationName;
    private String name;
    private Double price;
    private LocalDate from;
    private LocalDate to;
    private String details;
    private Integer maxNrOfBookings;
}
