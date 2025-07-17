create table if not exists car (
    creation_year int,
    number varchar(10),
    primary key (creation_year, number),
    color varchar(100) not null,
    brand varchar(100) not null,
    model varchar(100) not null,
    actualTechnicalInspection boolean
);