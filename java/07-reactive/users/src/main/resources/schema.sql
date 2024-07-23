drop table if exists people cascade;
create table people (
    id uuid default random_uuid() not null,
    email varchar(255) not null,
    active boolean not null,
    gravatar_url varchar(255),
    name varchar(255),
    password varchar(255),
    user_role VARCHAR(100) array,
    primary key (id));

