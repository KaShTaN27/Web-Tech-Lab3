package by.bsuir.lab3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestAccessDto {
    private Long studentInfoId;
    private Long employeeId;
    private String requestedAccess;
}
