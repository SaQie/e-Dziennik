INSERT INTO school_level(id, name) values (1,'Primary');
INSERT INTO school_level(id, name) values (2,'Secondary');
INSERT INTO school_level(id, name) values (3,'High school');

INSERT INTO address(id, address, postal_code, city) VALUES (100,'Washington','22-50','Somewhere');
INSERT INTO address(id, address, postal_code, city) VALUES (101,'Washington2','22-502','Somewhere2');
INSERT INTO address(id, address, postal_code, city) VALUES (102,'Washington3','22-503','Somewhere3');

INSERT INTO school(id, name, nip, regon, phone_number, school_level_id, address_id) VALUES (1,'School1','120','120','120',1,100);
INSERT INTO school(id, name, nip, regon, phone_number, school_level_id, address_id) VALUES (2,'School2','1202','1202','1202',2,101);
INSERT INTO school(id, name, nip, regon, phone_number, school_level_id, address_id) VALUES (3,'School3','1203','1203','1203',3,102);

INSERT INTO school_class(id, class_name, created_date, teacher_id, school_id) VALUES (100,'1B','2022-02-01',null,1);
INSERT INTO school_class(id, class_name, created_date, teacher_id, school_id) VALUES (101,'2B','2022-02-02',null,2);
INSERT INTO school_class(id, class_name, created_date, teacher_id, school_id) VALUES (102,'3B','2022-02-03',null,3);

INSERT INTO role(id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role(id, name) VALUES (2, 'ROLE_MODERATOR');
INSERT INTO role(id, name) VALUES (3, 'ROLE_TEACHER');