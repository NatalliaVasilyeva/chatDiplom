create table if not exists authenticationlog
(
    id         int auto_increment
        primary key,
    createdAt  datetime     null,
    loginTime  datetime     null,
    logoutTime datetime     null,
    user_id    varchar(255) null
)
    engine = MyISAM;

create table if not exists friendlist
(
    id        int auto_increment
        primary key,
    createdAt datetime     null,
    enable    bit          not null,
    fromId    varchar(255) null,
    toId      varchar(255) null
)
    engine = MyISAM;

create table if not exists ggroup
(
    id               varchar(20)  not null
        primary key,
    createdAt        datetime     null,
    name             varchar(50)  null,
    profileImagePath varchar(100) null,
    status           varchar(100) null
)
    engine = MyISAM;

create table if not exists groupmember
(
    id        int auto_increment
        primary key,
    createdAt datetime     null,
    enable    bit          not null,
    isLeader  bit          not null,
    groupId   varchar(255) null,
    toId      varchar(255) null
)
    engine = MyISAM;

create table if not exists groupmessagetransaction
(
    id         int auto_increment
        primary key,
    createdAt  datetime     null,
    fromId     varchar(255) null,
    groupId    varchar(255) null,
    message_id int          null
)
    engine = MyISAM;

create index FK8nj8aowkovyod61gqn1vsju4s
    on groupmessagetransaction (message_id);

create table if not exists groupmessagetransactiondetail
(
    id                        int auto_increment
        primary key,
    createdAt                 datetime     null,
    isReaded                  bit          not null,
    groupMessageTransactionId int          null,
    userId                    varchar(255) null
)
    engine = MyISAM;

create index FKppqr2j4yf7ri6xmhkjc8v5km8
    on groupmessagetransactiondetail (groupMessageTransactionId);

create table if not exists message
(
    id      int auto_increment
        primary key,
    message varchar(255) null,
    path    varchar(255) null,
    type    varchar(255) null
)
    engine = MyISAM;

create table if not exists notification
(
    id        int auto_increment
        primary key,
    createdAt datetime     null,
    isReaded  bit          not null,
    message   varchar(100) null,
    purpose   varchar(20)  null,
    user_id   varchar(255) null
)
    engine = MyISAM;

create table if not exists personalmessagetransaction
(
    id         int auto_increment
        primary key,
    createdAt  datetime     null,
    isReaded   bit          not null,
    fromId     varchar(255) null,
    message_id int          null,
    toId       varchar(255) null
)
    engine = MyISAM;

create index FKe8jq0vk4yw5nfuh6be9l2rdwd
    on personalmessagetransaction (message_id);

create table if not exists roles
(
    id      int auto_increment
        primary key,
    role    varchar(10)  null,
    user_id varchar(255) null
)
    engine = MyISAM;

create table if not exists user
(
    id               varchar(100) not null
        primary key,
    activeTime       datetime     null,
    address          varchar(255) null,
    birthdate        date         null,
    createdAt        datetime     null,
    email            varchar(50)  null,
    enabled          bit          not null,
    gender           varchar(10)  null,
    isBlock          bit          not null,
    name             varchar(50)  null,
    password         varchar(100) null,
    profileImagePath varchar(100) null,
    status           varchar(100) null,
    constraint UK_t8tbwelrnviudxdaggwr1kd9b
        unique (email)
)
    engine = MyISAM;

create table if not exists verificationtoken
(
    id         int auto_increment
        primary key,
    expiryDate datetime     null,
    purpose    varchar(255) null,
    token      varchar(50)  null,
    user_id    varchar(255) null
)
    engine = MyISAM;


