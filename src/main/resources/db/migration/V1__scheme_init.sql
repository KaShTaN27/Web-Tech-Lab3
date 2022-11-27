CREATE TABlE IF NOT EXISTS employee
(
    employee_id BIGSERIAL PRIMARY KEY,
    email       VARCHAR(100) NOT NULL UNIQUE,
    name        VARCHAR(100),
    password    VARCHAR(100) NOT NULL,
    role        VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS student_info
(
    student_info_id BIGSERIAL PRIMARY KEY,
    age             SMALLINT,
    course          SMALLINT,
    faculty         VARCHAR(100),
    name            VARCHAR(100),
    patronymic      VARCHAR(100),
    surname         VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS student_info_access
(
    employee_id     BIGINT NOT NULL REFERENCES employee,
    student_info_id BIGINT NOT NULL REFERENCES student_info,
    status          VARCHAR(20),
    PRIMARY KEY (employee_id, student_info_id)
);