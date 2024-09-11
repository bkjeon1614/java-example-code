create table student (
 id          bigint not null auto_increment,
 teacher_id  bigint not null,
 name        varchar(255),
 primary key (id)
) engine = InnoDB;

insert into student (name, teacher_id) VALUES ('전봉근', 1);
insert into student (name, teacher_id) VALUES ('홍길동', 2);
insert into student (name, teacher_id) VALUES ('정도전', 3);
insert into student (name, teacher_id) VALUES ('이성계', 4);
