-- auto-generated definition
create table posts
(
    username     varchar(15)   not null,
    post_text    varchar(2048)  not null,
    post_id      bigint default
                            nextval('posts_post_id_seq'::regclass) not null primary key,
    publish_date timestamp
);

alter table posts
    owner to postgres;
