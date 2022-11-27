package by.bsuir.lab3.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class StudentInfoAccessKey implements Serializable {

    @Column(name = "student_info_id")
    private Long studentInfoId;

    @Column(name = "employee_id")
    private Long employeeId;

    @Override
    public int hashCode() {
        return Objects.hash(studentInfoId, employeeId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        var key = (StudentInfoAccessKey) obj;
        return Objects.equals(studentInfoId, key.studentInfoId) && Objects.equals(employeeId, key.employeeId);
    }
}
