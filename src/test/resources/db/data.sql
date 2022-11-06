INSERT INTO school_level(id, name) values (1,'Primary');
INSERT INTO school_level(id, name) values (2,'Secondary');
INSERT INTO school_level(id, name) values (3,'High school');

INSERT INTO school(id, name, adress, postal_code, city, nip, regon, phone_number, school_level_id) VALUES (1,'School1','Washington','22-50','Somewhere','120','120','120',1);
INSERT INTO school(id, name, adress, postal_code, city, nip, regon, phone_number, school_level_id) VALUES (2,'School2','Washington2','22-502','Somewhere2','1202','1202','1202',2);
INSERT INTO school(id, name, adress, postal_code, city, nip, regon, phone_number, school_level_id) VALUES (3,'School3','Washington3','22-503','Somewhere3','1203','1203','1203',3);

INSERT INTO school_class(id, class_name, created_date, teacher_id, school_id) VALUES (1,'1B','2022-02-01',null,1);
INSERT INTO school_class(id, class_name, created_date, teacher_id, school_id) VALUES (2,'2B','2022-02-02',null,2);
INSERT INTO school_class(id, class_name, created_date, teacher_id, school_id) VALUES (3,'3B','2022-02-03',null,3);

INSERT INTO role(id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role(id, name) VALUES (2, 'ROLE_MODERATOR');
INSERT INTO role(id, name) VALUES (3, 'ROLE_TEACHER');

