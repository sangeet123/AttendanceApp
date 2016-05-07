insert into testattendanceapp.users (id,enabled,username,password) VALUES (100,true,'admin','$2a$11$6PQx/qAT2EfqB9esGCEKX.zdzXPMVYjMhZN7u4RyV1WTsoVL/6wEu');
insert into testattendanceapp.authorities(id,username,authority,user_id) VALUES (100,'admin','ROLE_ADMIN',100); 
insert into testattendanceapp.schools (id,name,telephone,email,createdOn,updatedOn) VALUES (1,'test1','123456789','test1@email.com', '2016-05-01T14:39:56.430','2016-05-01T14:39:56.430');
insert into testattendanceapp.users (id,enabled,username,password,school_id) VALUES (1,true,'test1user','test1password',1);
insert into testattendanceapp.authorities(id,username,authority,user_id) VALUES (1,'test1user','ROLE_SCHOOL_ADMIN',1);
insert into testattendanceapp.schools (id,name,telephone,email,createdOn,updatedOn) VALUES (10,'test10','234567891','test10@email.com', '1970-01-01 00:00:01','1970-01-01 00:00:01');
insert into testattendanceapp.users (id,enabled,username,password,school_id) VALUES (10,true,'test10user','test10password',10);
insert into testattendanceapp.authorities(id,username,authority,user_id) VALUES (10,'test10user','ROLE_SCHOOL_ADMIN',10);