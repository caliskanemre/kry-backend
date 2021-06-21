create table if not exists contact
(
    id bigint not null
        constraint contact_pkey
            primary key,
    url varchar(255) not null,
    name varchar(255) not null,
    condition varchar(255)
);

create sequence contact_id_seq;
