DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
create table users (id bigint not null auto_increment, username varchar(45) not null, password varchar(60) not null, enabled tinyint not null default 1, unique key (username), primary key (id)) engine=InnoDB;
create table authorities (id bigint not null auto_increment, user_id bigint not null, authority varchar(45) not null, unique key unique_user_id_authority (user_id, authority), primary key (id)) engine=InnoDB;
alter table authorities add constraint fk_authorities_user foreign key (user_id) references users (id);

INSERT INTO users (username, password, enabled) VALUES('desjgr', '$2a$10$r52jglwp29UHjGaMd6/bW.mbOoIOJdaj1iu3a.F/kMDzrGy/dmACO', 1);
INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$G0NkscKuydoUaINYU6Gn7OSLBYaD2WztOvV/5oQdokoMTfPhow7ry', 1);

INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_ADMIN');