insert into testattendanceapp.schools (id,name,telephone,email) VALUES (1,'test1','123456789','test1@email.com');
insert into testattendanceapp.users (id,enabled,username,password,school_id) VALUES (1,true,'test1user','test1password',1);
insert into testattendanceapp.authorities(id,username,authority,user_id) VALUES (1,'test1user','ROLE_SCHOOL_ADMIN',1);
insert into testattendanceapp.schools (id,name,telephone,email) VALUES (10,'test10','234567891','test10@email.com');
insert into testattendanceapp.users (id,enabled,username,password,school_id) VALUES (10,true,'test10user','test10password',10);
insert into testattendanceapp.authorities(id,username,authority,user_id) VALUES (10,'test10user','ROLE_SCHOOL_ADMIN',10);