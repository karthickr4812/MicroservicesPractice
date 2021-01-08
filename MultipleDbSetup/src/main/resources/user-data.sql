delete from ADDRESS;
delete from USER;

insert into USER (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate) values(1,'suresh','Kumar','suresh@gmail.com', 9998887776,123456789123, CURDATE());
insert into USER (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate) values(2,'Rohit','Sharma','Rohit@gmail.com', 9998887777, 231456789123, CURDATE());
insert into USER (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate) values(3,'Irfan','Khan','Irfan@gmail.com', 9998887778, 321456789123, CURDATE());
insert into USER (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate) values(4,'Ramesh','Kumar','ramesh@gmail.com', 9999988888,123456789666, CURDATE());
insert into USER (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate) values(5,'john','Becham','john@gmail.com', 8888899999, 231456789777, CURDATE());
insert into USER (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate) values(6,'Salman','Khan','salman@gmail.com', 9090908080, 321456789888, CURDATE());


insert into ADDRESS (id, street, city, zip, country, user_id, primaryAddress) values(1,'MCN Nagar','Chennai', 600097, 'India', 1, 1);
insert into ADDRESS (id, street, city, zip, country, user_id, primaryAddress) values(2,'Meenakshi Nagar','Madurai', 610102, 'India', 1, 0);
insert into ADDRESS (id, street, city, zip, country, user_id, primaryAddress) values(3,'10th Street','Shangai', 600097, 'Japan', 2, 1);
insert into ADDRESS (id, street, city, zip, country, user_id, primaryAddress) values(4,'15th Avenue','Kula Lambur', 600097, 'Singapore', 2, 0);
insert into ADDRESS (id, street, city, zip, country, user_id, primaryAddress) values(5,'MGR Nagar','Chennai', 600097, 'India', 3, 1);
insert into ADDRESS (id, street, city, zip, country, user_id, primaryAddress) values(6,'MCN Nagar','Trichy', 600097, 'India', 3, 0);

delete from UserTableOne;
delete from UserTableTwo;
delete from UserTableThree;
insert into UserTableOne (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate, street, city, zip, country) values(1,'suresh','Kumar','suresh@gmail.com', 9998887776,123456789123, CURDATE(),'MCN Nagar','Chennai', 600097, 'India');
insert into UserTableOne (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate, street, city, zip, country) values(2,'Rohit','Sharma','Rohit@gmail.com', 9998887777, 231456789123, CURDATE(),'Meenakshi Nagar','Madurai', 610102, 'India');
insert into UserTableTwo (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate, street, city, zip, country) values(3,'Irfan','Khan','Irfan@gmail.com', 9998887778, 321456789123, CURDATE(),'10th Street','Shangai', 600097, 'Japan');
insert into UserTableTwo (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate, street, city, zip, country) values(4,'Ramesh','Kumar','ramesh@gmail.com', 9999988888,123456789666, CURDATE(),'15th Avenue','Kula Lambur', 600097, 'Singapore');
insert into UserTableThree (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate, street, city, zip, country) values(5,'john','Becham','john@gmail.com', 8888899999, 231456789777, CURDATE(),'MGR Nagar','Chennai', 600097, 'India');
insert into UserTableThree (id, firstName, lastName, email, phoneNumber, aadharNumber, createdDate, street, city, zip, country) values(6,'Salman','Khan','salman@gmail.com', 9090908080, 321456789888, CURDATE(),'MCN Nagar','Trichy', 600097, 'India');
