delimiter $$
create procedure cargo_total_get()
begin
select DISTINCT
 c.cargo_id, c.cargo_code ,c.cargo_name, c.cargo_address, c.cargo_grade, c.cargo_field, c.cargo_total_capa, c.cargo_use_capa, round((c.cargo_use_capa / c.cargo_total_capa)*100, 2) AS utilization
from cargoes c
join cargo_management cm
on c.cargo_id = cm.cargo_id
join managers m
on m.managers = cm.manager_id;
end $$
delimiter ;
call cargo_total_get;

-- drop procedure cargo_total_get;

-- 소재지 
delimiter $$
create procedure cargo_address_get(in input_address varchar(30))
begin
select DISTINCT c.cargo_id,c.cargo_code, c.cargo_name, c.cargo_address, c.cargo_grade, c.cargo_field, c.cargo_total_capa, c.cargo_use_capa, round((c.cargo_use_capa / c.cargo_total_capa)*100, 2) AS utilization
from cargoes c
join cargo_management cm
on c.cargo_id = cm.cargo_id
join managers m
on m.managers = cm.manager_id
where c.cargo_address = input_address;
end $$
delimiter ;

 -- drop procedure cargo_address_get;

-- 이름조회
delimiter $$
create procedure cargo_name_get(in input_name varchar(30))
begin 
select DISTINCT c.cargo_id,c.cargo_code, c.cargo_name, c.cargo_address, c.cargo_grade, c.cargo_field, c.cargo_total_capa, c.cargo_use_capa, round((c.cargo_use_capa / c.cargo_total_capa)*100, 2) AS utilization
from cargoes c
join cargo_management cm
on c.cargo_id = cm.cargo_id
join managers m
on m.managers = cm.manager_id
where c.cargo_name = input_name;
end $$
delimiter ;

-- drop procedure cargo_name_get;


-- 등급조회
delimiter $$
create procedure cargo_grade_get(in input_grade varchar(30))
begin
select DISTINCT c.cargo_id,c.cargo_code, c.cargo_name, c.cargo_address, c.cargo_grade, c.cargo_field, c.cargo_total_capa, c.cargo_use_capa, round((c.cargo_use_capa / c.cargo_total_capa)*100, 2) AS utilization
    from cargoes c
             join cargo_management cm
                  on c.cargo_id = cm.cargo_id
             join managers m
                  on m.manager_id = cm.manager_id
where c.cargo_grade = input_grade  ;
end $$
delimiter ;
 -- drop procedure cargo_grade_get;

 -- drop procedure cargo_add;
-- 창고추가
DELIMITER $$

CREATE PROCEDURE cargo_add (
    in p_cargo_code CHAR(3),
    IN p_cargo_name VARCHAR(30),
    IN p_cargo_address VARCHAR(30),
    IN p_cargo_grade VARCHAR(30),
    IN p_cargo_field INT,
    IN p_cargo_total_capa INT,
    IN p_cargo_use_capa INT
)
BEGIN
	 IF EXISTS (
        SELECT 1 FROM managers WHERE manager_position = '총관리자'
    ) THEN
    INSERT INTO cargoes (cargo_code, cargo_name, cargo_address, cargo_grade, cargo_field, cargo_total_capa, cargo_use_capa)
    values(
        p_cargo_code, p_cargo_name, p_cargo_address, p_cargo_grade,
        p_cargo_field, p_cargo_total_capa, p_cargo_use_capa
	);
	end if;
END $$

DELIMITER ;
call cargo_total_get();




select * from cargoes;
SELECT * FROM managers WHERE manager_position = '총관리자';

-- 창고 수정
-- drop procedure cargo_modify;
delimiter $$
create procedure cargo_modify(In p_cargo_id int,
	IN p_cargo_code varchar(12),
	in p_cargo_name varchar(30),
	in p_cargo_address varchar(30),
	in P_cargo_grade varchar(30),
	in p_cargo_field int,
	in p_cargo_total_capa int,
	in p_cargo_use_capa int)
begin
update cargoes c
join cargo_management cm 
on c.cargo_id = cm.cargo_id
join managers m
on cm.manager_id = m.manager_id
set cargo_code =p_cargo_code,
	cargo_name = p_cargo_name,
	cargo_address = p_cargo_address,
    cargo_grade = P_cargo_grade,
    cargo_field = p_cargo_field,
    cargo_total_capa = p_cargo_total_capa,
    cargo_use_capa = p_cargo_use_capa
	where c.cargo_id = p_cargo_id
    and m.manager_position = '총관리자';
end $$
delimiter ;


-- drop procedure cargo_remove;

-- 창고 삭제
delimiter $$
create procedure cargo_remove(in p_cargo_id int)
begin
delete c 
from cargoes c
join cargo_management cm 
on c.cargo_id = cm.cargo_id
join managers m
on cm.manager_id = m.manager_id
where c.cargo_id = p_cargo_id and c.cargo_id = cm.cargo_id and m.manager_position = '총관리자';

end $$
delimiter ;
SHOW COLUMNS FROM cargoes;
SHOW TABLES IN testdb1;

SELECT DISTINCT c.*
FROM cargoes c
JOIN cargo_management cm ON c.cargo_id = cm.cargo_id