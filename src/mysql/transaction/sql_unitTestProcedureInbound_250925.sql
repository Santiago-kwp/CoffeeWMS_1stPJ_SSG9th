use testDB1;
select * from inbound_request_items;

-- 관리자가 회원의 미승인된 입고 요청 내역이 있는 회원의 아이디를 조회하는 프로시저
-- -> 선택한 회원의 입고요청상세 전체 내역 조회 : procedure show_all_inbound_request_items;
drop procedure  get_all_member_has_unapproved_inbound_request;
delimiter @@
create procedure get_all_member_has_unapproved_inbound_request()
begin
-- 회원 ID, 미승인 건수 출력
select member_id, count(member_id) as unapproved_request_num
from inbound_request
where inbound_request_approved = 0
group by member_id
order by count(member_id) desc;
end @@
delimiter ;

call get_all_member_has_unapproved_inbound_request();

