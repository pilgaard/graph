drop table if exists person;
create table person(
id				int,
name 		VARCHAR(100) not null,
job			varchar(100),
birthday	varchar(20),
primary key (id)	
);

drop table if exists knows;
create table knows(
id			bigint auto_increment, 
user_1	int not null,
user_2	int not null,
primary key (id)
);

alter table knows
add index 		FK416055ABC6132571 (user_1),add constraint FK416055ABC6132571foreign key (user_1) references person (id);

alter table knowsadd index 		FK416055ABC6132572 (user_2),add constraint FK416055ABC6132572foreign key (user_2) references person (id);

-- CREATE INDEX idx_name
-- ON person (name);

load data infile '/var/lib/mysql-files/social_network_nodes.csv' into table person fields terminated by ','
  optionally enclosed by '"'
  lines terminated by '\n'
  ignore 1 lines
    (id, name, job, birthday);
    
load data infile '/var/lib/mysql-files/social_network_edges.csv' into table knows fields terminated by ','
  optionally enclosed by '"'
  lines terminated by '\n'
  ignore 1 lines
    (user_1, user_2);


DELIMITER $$
CREATE PROCEDURE `getNames`()
begin
select id, name from person;
end$$
DELIMITER ;

-- dept 1
DELIMITER $$
CREATE PROCEDURE `dept1`(nId int)
BEGIN
select uf1.user_2 as id, person.name from knows uf1
INNER JOIN person ON uf1.`user_2` = person.`id`
where uf1.user_1 = nId;
END$$
DELIMITER ;

drop procedure dept2;

-- dept 2   
DELIMITER $$
CREATE PROCEDURE dept2(nId int)
BEGIN
select distinct uf2.`user_2` as id, person.name from knows uf1inner join knows uf2 on uf1.user_2 = uf2.user_1
inner join person on uf2.`user_2` = person.`id` 
where uf1.user_1 = nId;
END$$
DELIMITER ;

-- dept 3 
DELIMITER $$
CREATE PROCEDURE dept3(nId int)
BEGIN  
select distinct uf3.`user_2` as id, person.name from knows uf1inner join knows uf2 on uf1.user_2 = uf2.user_1
inner join knows uf3 on uf2.user_2 = uf3.user_1
inner join person on uf3.`user_2` = person.`id` 
where uf1.user_1 = nId;
END$$
DELIMITER ;

-- dept 4
DELIMITER $$
CREATE PROCEDURE dept4(nId int)
BEGIN   
select distinct uf4.`user_2` as id, person.name from knows uf1inner join knows uf2 on uf1.user_2 = uf2.user_1
inner join knows uf3 on uf2.user_2 = uf3.user_1
inner join knows uf4 on uf3.user_2 = uf4.user_1
inner join person on uf4.`user_2` = person.`id` 
where uf1.user_1 = nId;
END$$
DELIMITER ;

-- dept 5
DELIMITER $$
CREATE PROCEDURE dept5(nId int)
BEGIN
select distinct uf5.`user_2` as id, person.name from knows uf1inner join knows uf2 on uf1.user_2 = uf2.user_1
inner join knows uf3 on uf2.user_2 = uf3.user_1
inner join knows uf4 on uf3.user_2 = uf4.user_1
inner join knows uf5 on uf4.user_2 = uf5.user_1
inner join person on uf5.`user_2` = person.`id` 
where uf1.user_1 = nId;
END$$
DELIMITER ;

call dept1("Alba Carrol");
    