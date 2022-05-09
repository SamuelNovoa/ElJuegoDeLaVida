drop database if exists bd_GameOfLife;
create database if not exists bd_GameOfLife;
use bd_GameOfLife;

-- ------------------------------------------------------------ DATA BASE
drop table if exists `profiles`;
create table if not exists `profiles`(
    `id` int unsigned not null auto_increment,
    `name` varchar(30) not null,
    
    primary key (`id`)
) engine innodb;


drop table if exists figures;
create table if not exists figures(
	`profile` int unsigned not null,
    `name` varchar(40) not null,
	`id` int unsigned not null,
    `data` blob not null,
    
    primary key (`profile`, `id`),
    
    foreign key (`profile`) references `profiles` (`id`)
							on delete cascade
							on update cascade,
	index fk_profile (`profile`)
    
) engine innodb;


drop table if exists movements;
create table if not exists movements(
   actions varchar(255)
) engine innodb;


drop view if exists view_data;
create view view_data
as
	select 	p.`id` as ID, 
			p.`name` as `profile`, 
            count(f.`id`) as No_figures
	from `profiles` as p left join figures as f
						on p.`id` = f.`profile`
	group by p.`id`;


drop view if exists figures_profile;
create view figures_profile as
	select	`profile` as `profile`,
			`name` as Name_figure,
			`id` as Figure_pos,
			`data` as Figure
		from figures;


-- ------------------------------------------------------------ DEFAULT DATA
insert into `profiles` (`name`)
	values ('Incio sin registro');


-- ------------------------------------------------------------  TRIGGERS
delimiter $$

drop trigger if exists insert_profile $$
create trigger insert_profile after insert
	on `profiles`
    for each row
    begin
		insert into movements (actions)
			values (concat('ADD USER: ', new.`id`, '-', new.`name`));
    end $$

drop trigger if exists delete_profile $$
create trigger delete_profile after delete
	on `profiles`
    for each row
    begin
		insert into movements (actions)
			values (concat('RMV USER: ', old.`id`, '-', old.`name`));
    end $$

drop trigger if exists update_profile $$
create trigger update_profile after update
	on `profiles`
    for each row
    begin
		insert into movements (actions)
			values (concat('UPD USER: ', old.`name`, '-', new.`name`));
    end $$



drop trigger if exists insert_figure $$
create trigger insert_figure before insert
	on figures
    for each row
    begin
		declare counter integer;
    
		select No_figures into counter
			from view_data
            where ID = new.`profile`;
            
		set new.`id` = counter + 1;
        
    end $$

drop trigger if exists delete_figure $$
create trigger delete_figure after delete
	on figures
    for each row
    begin
		insert into movements (actions)
			values (concat('RMV FIGURE: ', old.`profile`, '-', old.`id`));
    end $$





