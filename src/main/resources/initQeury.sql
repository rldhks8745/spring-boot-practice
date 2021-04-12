CREATE DATABASE test;

CREATE TABLE `USER` (
	`num` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'pk',
	`id` VARCHAR(50) NULL DEFAULT NULL COMMENT 'id' COLLATE 'utf8mb4_general_ci',
	`pw` VARCHAR(50) NULL DEFAULT NULL COMMENT 'pw' COLLATE 'utf8mb4_general_ci',
	`name` VARCHAR(50) NULL DEFAULT NULL COMMENT 'name' COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`num`) USING BTREE
)
COMMENT='유저테이블'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

INSERT INTO USER (
  id
  ,pw
  ,name
) VALUES (
  'test1'  -- id - IN varchar(50)
  ,'test1'  -- pw - IN varchar(50)
  ,'테스트1'  -- name - IN varchar(50)
);
INSERT INTO USER (
  id
  ,pw
  ,name
) VALUES (
  'test2'  -- id - IN varchar(50)
  ,'test2'  -- pw - IN varchar(50)
  ,'테스트2'  -- name - IN varchar(50)
);
INSERT INTO USER (
  id
  ,pw
  ,name
) VALUES (
  'test3'  -- id - IN varchar(50)
  ,'test3'  -- pw - IN varchar(50)
  ,'테스트3'  -- name - IN varchar(50)
);