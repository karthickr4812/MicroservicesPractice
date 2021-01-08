delete from project;
ALTER TABLE project AUTO_INCREMENT=1;
delete from employee;
ALTER TABLE employee AUTO_INCREMENT=1;

insert into employee(id, userId, employeeName, employeeEmail, phoneNumber, salary, location, joiningDate) values(1,1,'suresh kumar','suresh@gmail.com', 9998887776, 50000, 'Chennai' , CURDATE());
insert into employee(id, userId, employeeName, employeeEmail, phoneNumber, salary, location, joiningDate) values(2,2,'Rohit Sharma','Rohit@gmail.com' , 9998887777, 100000, 'Shangai', CURDATE());
insert into employee(id, userId, employeeName, employeeEmail, phoneNumber, salary, location, joiningDate) values(3,3,'Irfan Khan','Irfan@gmail.com' , 9998887778, 72000, 'Chennai', CURDATE());

insert into project(id, projectCode, role, durationInMonth, startDate, endDate, currentProject, employee_id) values(101,'P100', 'Developer', 11, '2017-07-12', '2018-06-11',0, 1);
insert into project(id, projectCode, role, durationInMonth, startDate, endDate, currentProject, employee_id) values(102,'P101', 'Developer', 20,'2018-06-12', '2020-02-11',0, 1);
insert into project(id, projectCode, role, durationInMonth, startDate, endDate, currentProject, employee_id) values(103,'P102', 'Manager', 10,'2020-02-12', '2020-12-12',1, 1);
insert into project(id, projectCode, role, durationInMonth, startDate, endDate, currentProject, employee_id) values(104,'P100', 'Developer', 20,'2018-07-12', '2020-03-12',0, 2);
insert into project(id, projectCode, role, durationInMonth, startDate, endDate, currentProject, employee_id) values(105,'P101', 'Lead', 10,'2018-03-12', '2020-12-12',1, 2);
insert into project(id, projectCode, role, durationInMonth, startDate, endDate, currentProject, employee_id) values(106,'P104', 'Developer', 20,'2017-01-01', '2018-08-09',0, 3);
insert into project(id, projectCode, role, durationInMonth, startDate, endDate, currentProject, employee_id) values(107,'P100', 'Lead', 10,'2018-08-10', '2019-07-11',0, 3);
insert into project(id, projectCode, role, durationInMonth, startDate, endDate, currentProject, employee_id) values(108,'P102', 'Lead', 5,'2019-08-12', '2020-02-11',0, 3);
insert into project(id, projectCode, role, durationInMonth, startDate, endDate, currentProject, employee_id) values(109,'P101', 'Manager', 10,'2020-02-12', '2020-12-12',1,3);