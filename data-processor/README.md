### Database config
- Create database
```sql
create database relay42;
```
- Create table
```sql

create table event
(
    id          varchar(100) not nullprimary key,
    event_id    varchar(100) null,
    type        varchar(100) null,
    name        varchar(100) null,
    cluster_id  varchar(100) null,
    timestamp   timestamp    null,
    value       double       null,
    initialized varchar(100) null
);


```