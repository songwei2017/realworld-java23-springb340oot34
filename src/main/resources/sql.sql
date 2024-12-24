create table article
(
    id              bigint auto_increment primary key,
    created_at      timestamp(6)  not null,
    updated_at      timestamp(6)  null,
    author_id       bigint        not null,
    description     varchar(50)   not null,
    slug            varchar(50)   not null,
    title           varchar(50)   not null,
    content         varchar(1000) not null,
    favorites_count int default 0 not null,
    constraint slug unique (slug),
    constraint title unique (title)
);

create table article_comment
(

    id         bigint auto_increment    primary key,
    article_id bigint       not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    author_id  bigint       not null,
    content    varchar(500) not null,
    index article_id (article_id)
);

create table article_favorite
(

    id         bigint auto_increment   primary key,
    article_id bigint       null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    user_id    bigint       null,
    constraint user_id unique (user_id, article_id)
);

create table article_tag
(
    id         bigint auto_increment  primary key,
    article_id bigint       null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    tag_name   varchar(20)  null,
    tag_id     bigint       null,
    constraint article_id unique (article_id, tag_id)
);

create table tag
(
    id         bigint auto_increment  primary key,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    name       varchar(20)  not null,
    constraint tag_name_uindex   unique (name)
);

create table user
(

    id         bigint auto_increment   primary key,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    email      varchar(30)  not null,
    username   varchar(30)  not null,
    image_url  varchar(200) null,
    password   varchar(200) not null,
    bio        varchar(500) null,
    constraint email  unique (email),
    constraint username unique (username)
);

create table user_follow
(
    id           bigint auto_increment  primary key,
    created_at   timestamp(6) not null,
    follower_id  bigint       null,
    following_id bigint       null,
    constraint follower_id unique (follower_id, following_id)
);


