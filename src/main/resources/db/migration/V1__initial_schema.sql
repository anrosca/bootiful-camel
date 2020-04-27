create sequence report_sequence start with 100;

create table report
(
    id                 bigint default nextval('report_sequence') primary key,
    file_name          varchar(255) not null,
    creation_timestamp timestamp    not null,
    content            bytea         not null
);

create sequence city_sequence start with 100;

create table city
(
    id        bigint default nextval('city_sequence') primary key,
    city_name varchar(100) not null
);

create sequence report_config_sequence start with 100;

create table report_config
(
    id           bigint default nextval('report_config_sequence') primary key,
    city_id      bigint      not null references city (id),
    trigger_type varchar(10) not null
);

create sequence book_sequence start with 100;

create table book
(
    id           bigint default nextval('book_sequence') primary key,
    isbn         varchar(20) not null,
    title        varchar(50) not null,
    publish_year int not null
);


create sequence author_sequence start with 100;

create table author
(
    id           bigint default nextval('author_sequence') primary key,
    author_name  varchar(30) not null unique
);

create table book_authors
(
    book_id           bigint references book(id),
    author_id         bigint references author(id),
    primary key(book_id, author_id)
);
