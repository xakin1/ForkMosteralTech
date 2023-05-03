SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'gema-dev'
AND pid <> pg_backend_pid();

DROP DATABASE IF EXISTS "gema-dev";

SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'gema'
AND pid <> pg_backend_pid();

CREATE DATABASE "gema-dev"
WITH TEMPLATE gema
OWNER postgres;
