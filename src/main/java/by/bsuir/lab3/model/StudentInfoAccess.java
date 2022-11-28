package by.bsuir.lab3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class StudentInfoAccess {

    @JsonIgnore
    @EmbeddedId
    StudentInfoAccessKey id;

    @ManyToOne(fetch = EAGER)
    @MapsId("studentInfoId")
    @JoinColumn(name = "student_info_id")
    private StudentInfo studentInfo;

    @JsonIgnore
    @ManyToOne(fetch = EAGER)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(value = STRING)
    private AccessStatus status;

    public StudentInfoAccess(StudentInfo studentInfo, Employee employee, AccessStatus status) {
        this.studentInfo = studentInfo;
        this.employee = employee;
        this.id = new StudentInfoAccessKey(studentInfo.getId(), employee.getId());
        this.status = status;
    }
}
