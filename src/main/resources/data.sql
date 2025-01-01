-- Insert an employee
INSERT INTO employee (id, name, emp_id, email, phone_number, address, married, extra_martial_affair, dream_wish, nature_behavior, profile_picture)
VALUES (NEXTVAL('employee_seq'), 'A Manchanda', 328, 'Ankit.Mda@aabc.com', '1234567890', '29 Starsse, Frankfurt am Main, Germany', true, true, 'Becoming a CEO of Ankit and Sons Computers Ltd Company', 'Friendly Jolly and very Manipulative', null);

-- Insert spouse for the employee
INSERT INTO spouse (id, name, age, gender, current_occupation, employee_id)
VALUES (NEXTVAL('spouse_seq'), 'A Sharma', 30, 'Female', 'Learning German', (SELECT id FROM employee ORDER BY id DESC LIMIT 1));

-- Insert kids for the employee
INSERT INTO kids (id, name, age, gender, profession, employee_id)
VALUES (NEXTVAL('kids_seq'), 'ABC', 7, 'Male', 'Student', (SELECT id FROM employee ORDER BY id DESC LIMIT 1));

-- Insert professional details for the employee
INSERT INTO professional_details (id, current_company, current_designation, current_salary, current_location, employee_id)
VALUES (NEXTVAL('professional_details_seq'), 'erg GMBH', 'Senior Java Developer', 95000.00, 'Fre, Germany', (SELECT id FROM employee ORDER BY id DESC LIMIT 1));

-- Insert past employment for the employee (corrected this to past_employment)
INSERT INTO past_employment (id, company_name, designation, salary, employee_id)
VALUES (NEXTVAL('past_employment_seq'), 'abc GMBH', 'Senior Java Developer', 9556000.00, (SELECT id FROM employee ORDER BY id DESC LIMIT 1));
