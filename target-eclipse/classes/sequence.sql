-- If "ERROR HY000: This function has none of DETERMINISTIC, NO SQL, or READS SQL DATA in its declaration and binary logging is enabled (you *might* want to use the less safe log_bin_trust_function_creators variable)"
-- Run this command as admin, before re-run the script
-- SET GLOBAL log_bin_trust_function_creators = 1;

DROP table IF EXISTS sequence_store;
CREATE TABLE sequence_store (
  seq_name varchar(20) unique not null,
  seq_current  int not null
);

insert into sequence_store  values ('token',0);
insert into sequence_store  values ('trx',0);
insert into sequence_store  values ('trxhost',0);
insert into sequence_store  values ('settlement',0);

DELIMITER $$ 
DROP function IF EXISTS trx_sequence_generator$$
CREATE function trx_sequence_generator(increment INTEGER)
RETURNS INTEGER
BEGIN
	UPDATE sequence_store SET seq_current = (@var := ((seq_current + increment-1)%9999999)+1  ) WHERE seq_name ='trx';
    return @var;
END $$

DELIMITER $$ 
DROP function IF EXISTS trx_host_sequence_generator$$
CREATE function trx_host_sequence_generator(increment INTEGER)
RETURNS INTEGER
BEGIN
	UPDATE sequence_store SET seq_current = (@var := ((seq_current + increment-1)%999999)+1  ) WHERE seq_name ='trxhost';
    return @var;
END $$

DELIMITER $$ 
DROP function IF EXISTS running_account_generator$$
CREATE function running_account_generator(increment INTEGER, vaHeaderInp VARCHAR(20))
RETURNS INTEGER
BEGIN
	DECLARE total_header INT DEFAULT 0;
	SELECT COUNT(*) INTO total_header from sequence_store where seq_name = vaHeaderInp;
	IF total_header = 0 then insert into sequence_store(seq_name,seq_current) values(vaHeaderInp,0);
	END IF;
	UPDATE sequence_store SET seq_current = (@var := ((seq_current + increment-1)%9999999)+1  ) WHERE seq_name = vaHeaderInp;
    return @var;
END $$

DELIMITER $$ 
DROP function IF EXISTS settlement_sequence_generator$$
CREATE function settlement_sequence_generator(increment INTEGER)
RETURNS INTEGER
BEGIN
	UPDATE sequence_store SET seq_current = (@var := ((seq_current + increment-1)%999999)+1  ) WHERE seq_name ='settlement';
    return @var;
END $$

DELIMITER $$ 
DROP function IF EXISTS token_sequence_generator$$
CREATE function token_sequence_generator(increment INTEGER)
RETURNS INTEGER
BEGIN
	UPDATE sequence_store SET seq_current = (@var := ((seq_current + increment-1)%999999)+1  ) WHERE seq_name ='token';
    return @var;
END $$