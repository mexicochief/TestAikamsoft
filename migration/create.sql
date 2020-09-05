CREATE schema Store;
create table Store.Users
(
    id         serial primary key,
    first_name varchar(30),
    last_name  varchar(30)
);

create table Store.Products
(
    id           serial primary key,
    product_name varchar(30),
    cost         numeric(1000,2)
);

create table Store.Purchases
(
    id         serial primary key,
    user_id    bigint,
    product_id bigint,
    order_date date,
    foreign key (user_id) references Store.Users (id),
    foreign key (product_id) references Store.Products (id)
);
