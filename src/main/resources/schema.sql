-- Define sequences
CREATE SEQUENCE IF NOT EXISTS employee_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS spouse_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS kids_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS professional_details_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS past_employment_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS exception_email_not_sent_seq START WITH 1 INCREMENT BY 1;

-- Create tables without AUTO_INCREMENT, use sequence for id generation
CREATE TABLE IF NOT EXISTS employee (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    emp_id BIGINT,
    email VARCHAR(255),
    phone_number VARCHAR(20),
    address VARCHAR(255),
    married BOOLEAN,
    extra_martial_affair BOOLEAN,
    dream_wish VARCHAR(255),
    nature_behavior VARCHAR(255),
    profile_picture BLOB
);

CREATE TABLE IF NOT EXISTS spouse (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    age INT,
    gender VARCHAR(10),
    current_occupation VARCHAR(255),
    employee_id BIGINT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE IF NOT EXISTS kids (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    age INT,
    gender VARCHAR(10),
    profession VARCHAR(255),
    employee_id BIGINT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE IF NOT EXISTS professional_details (
    id BIGINT PRIMARY KEY,
    current_company VARCHAR(255),
    current_designation VARCHAR(255),
    current_salary DOUBLE,
    current_location VARCHAR(255),
    employee_id BIGINT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

CREATE TABLE IF NOT EXISTS past_employment (
    id BIGINT PRIMARY KEY,
    company_name VARCHAR(255),
    designation VARCHAR(255),
    salary DOUBLE,
    employee_id BIGINT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);


CREATE TABLE IF NOT EXISTS exception_email_not_sent (
    id BIGINT PRIMARY KEY,
    emp_id BIGINT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    exception_received BOOLEAN,
    email_sent BOOLEAN,
    email VARCHAR(255)
);




