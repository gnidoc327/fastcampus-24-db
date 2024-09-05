-- 1. Master 서버 SQL
GRANT REPLICATION SLAVE ON *.* TO 'replication_user'@'%' IDENTIFIED BY '1q2w3e4r!!';
FLUSH PRIVILEGES;
SHOW MASTER STATUS;

-- 2. Slave 서버 SQL
-- STOP SLAVE FOR CHANNEL 'master_b_channel';
-- RESET SLAVE ALL;
CHANGE MASTER TO
    MASTER_HOST='mysql-master-a',
    MASTER_USER='replication_user',
    MASTER_PASSWORD='1q2w3e4r!!',
    MASTER_LOG_FILE='mysql-bin.000003',
    MASTER_LOG_POS=654
FOR CHANNEL 'master_a_channel';
START SLAVE FOR CHANNEL 'master_a_channel';
CHANGE MASTER TO
    MASTER_HOST='mysql-master-b',
    MASTER_USER='replication_user',
    MASTER_PASSWORD='1q2w3e4r!!',
    MASTER_LOG_FILE='mysql-bin.000003',
    MASTER_LOG_POS=654
    FOR CHANNEL 'master_b_channel';
START SLAVE FOR CHANNEL 'master_b_channel';
SHOW SLAVE STATUS;

SHOW TABLES;

-- SET GLOBAL read_only = OFF;