create table users
(
    id       uuid primary key not null,
    username varchar unique   not null
);

create table services
(
    id   uuid primary key not null,
    name varchar unique   not null
);

create table subscriptions
(
    id         uuid primary key not null,
    user_id    uuid references users (id),
    service_id uuid references services (id),
    price      numeric          not null,
    status     varchar          not null default 'active',
    start_date timestamp        not null,
    end_date   timestamp
);
