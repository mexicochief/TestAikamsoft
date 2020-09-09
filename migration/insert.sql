insert into store.users(first_name, last_name)
values ('Tom', 'Lee');
insert into store.users(first_name, last_name)
values ('John', 'Lee');
insert into store.users(first_name, last_name)
values ('Ivan', 'Ivanov');
insert into store.users(first_name, last_name)
values ('Mark', 'Smith');
insert into store.users(first_name, last_name)
values ('Jonathan', 'Joestar');


insert into store.products(product_name, cost)
VALUES ('Cola', 20.5);
insert into store.products(product_name, cost)
VALUES ('Corn', 50.0);
insert into store.products(product_name, cost)
VALUES ('Apple', 10.0);
insert into store.products(product_name, cost)
VALUES ('Cheese', 100.0);

insert into store.purchases(user_id, product_id, order_date)
VALUES (1, 2, date '2020-01-14');
insert into store.purchases(user_id, product_id, order_date)
VALUES (1, 3, date '2020-01-14');
insert into store.purchases(user_id, product_id, order_date)
VALUES (2, 1, CURRENT_DATE);
insert into store.purchases(user_id, product_id, order_date)
VALUES (2, 4, date '2020-02-15');
insert into store.purchases(user_id, product_id, order_date)
VALUES (3, 4, date '2019-01-14');
insert into store.purchases(user_id, product_id, order_date)
VALUES (4, 1, date '2020-01-14');
insert into store.purchases(user_id, product_id, order_date)
VALUES (4, 4, CURRENT_DATE);
insert into store.purchases(user_id, product_id, order_date)
VALUES (4, 1, date '2020-12-14');
insert into store.purchases(user_id, product_id, order_date)
VALUES (5, 1, date '2020-01-01');
insert into store.purchases(user_id, product_id, order_date)
VALUES (5, 1, date '2020-02-14');
insert into store.purchases(user_id, product_id, order_date)
VALUES (5, 1, CURRENT_DATE);
insert into store.purchases(user_id, product_id, order_date)
VALUES (5, 2, CURRENT_DATE);
insert into store.purchases(user_id, product_id, order_date)
VALUES (5, 3, CURRENT_DATE);