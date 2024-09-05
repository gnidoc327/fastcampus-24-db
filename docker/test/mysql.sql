-- Slow Query Log 활성화
SET GLOBAL slow_query_log = 'ON';

-- 느린 쿼리 기준 시간 1초로 설정
SET GLOBAL long_query_time = 1;

-- 로그 파일 경로 확인
SHOW VARIABLES LIKE 'slow_query_log_file';
