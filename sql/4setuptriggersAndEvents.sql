create trigger before_thread_insert 
    before insert on Thread
    for each row
		set new.TimeToLive = 30,
		    new.MaxTimeToLive = 30;
create trigger before_post_insert
    before insert on Post
    for each row
    update Thread set TimeToLive = MaxTimeToLive where id = new.ThreadID;
delimiter $$
create trigger reject_post_on_archived_thread
    before insert on Post
    for each row
    begin
      if exists(select * from Thread where ID = new.ThreadID and ArchivedFlag = 1) then 
        signal sqlstate '45000' set message_text = 'Cannot create post: thread archived';
      end if;
    end $$
delimiter ;
set global event_scheduler = on;
delimiter $$
create event reduce_thread_time_to_live
on schedule
    every 1 day 
    starts (timestamp(current_date) + interval 1 day)
    do
      begin
        update Thread set TimeToLive = TimeToLive - 1;
        update Thread set MaxTimeToLive = 10 where TimeToLive <= 10 and ArchivedFlag = 0;
        update Thread set ArchivedFlag = 1, MaxTimeToLive = 30, TimeToLive = 30 where TimeToLive = 0 and ArchivedFlag = 0;
        delete from Thread where TimeToLive = 0 and ArchivedFlag = 1;
      END $$
delimiter ;
commit;