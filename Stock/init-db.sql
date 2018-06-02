drop table shares if exists;
drop table orders if exists;
drop table exchanges if exists;
drop table customers if exists;
drop table symbols if exists;
drop table requests if exists;
drop table configs if exists;
drop table roles if exists;

create table shares (
    shid integer not null,
    uid varchar(50) not null,
    symbol varchar(50) not null,
    quantity integer not null,
    primary key (shid)
);

create table orders (
    oid integer not null,
    status integer not null,
    symbol varchar(50) not null,
    price varchar(20) not null,
    uid varchar(50) not null,
    type varchar(20) not null,
    operation varchar(20) not null,
    remainingQuantity integer not null,
    initQuantity integer not null,
    submitDate bigint not null,
    primary key (oid)
);

create table exchanges (
    eid integer not null,
    sellPrice varchar(20) not null,
    buyPrice varchar(20) not null,
    symbol varchar(50) not null,
    type varchar(20) not null,
    sellerId varchar(20) not null,
    buyerId varchar(20) not null,
    quantity integer not null,
    sellRef integer not null,
    buyRef integer not null,
    exchangeDate bigint not null,
    primary key (eid)
);

create table customers (
    cid integer not null,
    uid varchar(50) not null,
    pass varchar(50) not null,
    name varchar(50) not null,
    family varchar(50) not null,
    email varchar(100) not null,
    credit integer not null,
    primary key (cid)
);

create table symbols (
    sid integer not null,
    name varchar(50) not null,
    company varchar(50) not null,
    status integer not null,
    total integer not null,
    primary key (sid)
);

create table requests (
    crid integer not null,
    uid varchar(50) not null,
    status integer not null,
    amount integer not null,
    type integer not null,
    primary key (crid)
);

create table configs (
    id integer not null,
    name varchar(50) not null,
    value int not null,
    primary key (id)
);

create table roles (
    uid varchar(50) not null,
    role varchar(20) not null,
    primary key (uid, role)
);

insert into customers values ('0', '1', 'pass', 'admin', 'adminian', 'admin@stock.com', '0');
insert into configs values ('0', 'upperBound', 0);
insert into configs values('1', 'limited', 0);
