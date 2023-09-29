DROP TABLE IF EXISTS yarnshop.user;
DROP TABLE IF EXISTS yarnshop.authorities;

create table user
(
    id BIGINT not null primary key auto_increment,
    username VARCHAR(255) not null,
    password VARCHAR(255) not null,
    created_date       timestamp,
    last_modified_date timestamp DEFAULT NOW(),
    enabled boolean not null default 1
) ENGINE = InnoDB;
create unique index ix_user_username on user (username);
create table authority
(
    id BIGINT not null primary key auto_increment,
    user_id BIGINT not null ,
    roll VARCHAR(255) not null,
    created_date       timestamp,
    last_modified_date timestamp DEFAULT NOW(),
    constraint fk_authorities_userid foreign key (user_id) references user (id)
) ENGINE = InnoDB;
create unique index ix_auth_username on authority (user_id, roll);