create table account(
  ID bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  user_id bigint not null,
  currency_code varchar(3) not null,
  balance numeric not null
);