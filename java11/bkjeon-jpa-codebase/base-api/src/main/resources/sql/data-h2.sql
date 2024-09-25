insert into posts (title, author, content) values ('테스트1', 'test1@gmail.com', '테스트1의 본문');
insert into posts (title, author, content) values ('테스트2', 'test2@gmail.com', '테스트2의 본문');
insert into posts (title, author, content) values ('테스트3', 'test3@gmail.com', '테스트3의 본문');

INSERT INTO USER (USER_ID, USERNAME, PASSWORD, NICKNAME, ACTIVATED) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1);
INSERT INTO USER (USER_ID, USERNAME, PASSWORD, NICKNAME, ACTIVATED) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 1);
INSERT INTO USER (USER_ID, USERNAME, PASSWORD, NICKNAME, ACTIVATED) VALUES (3, 'bkjeon', '$2a$10$5LObc9rbmDCnzIO70RqGbe4lE1Ye1Dc.58GUIRTmcTijul2WCdF.e', 'bkjeon', 1);

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (2, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (3, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (3, 'ROLE_ADMIN');

INSERT INTO board (title, contents, author) values ('테스트11111', '테스트2의 본문111111', 'bkjeon');
INSERT INTO board (title, contents, author) values ('테스트2', '테스트2의 본문', 'bkjeon');
INSERT INTO board (title, contents, author) values ('테스트2', '테스트2의 본문', 'bkjeon1');
INSERT INTO board (title, contents, author) values ('테스트2', '테스트2의 본문', 'bkjeon2');
