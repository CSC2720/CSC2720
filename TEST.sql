SELECT MAX(Employee_ID) FROM EMPLOYEE;
select *from employee;
select *from emergency_contact;
select *from shifts;
SET FOREIGN_KEY_CHECKS = 0; 
TRUNCATE table employee; 
TRUNCATE table emergency_contact; 
SET FOREIGN_KEY_CHECKS = 1;