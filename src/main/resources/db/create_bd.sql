CREATE DATABASE tele2_chat_db;
CREATE User virodina WITH PASSWORD '1111';
GRANT ALL PRIVILEGES ON DATABASE tele2_chat_db TO virodina;
CREATE SCHEMA IF NOT EXISTS tele2_chat;
GRANT USAGE ON SCHEMA tele2_chat TO virodina;
GRANT CREATE ON SCHEMA tele2_chat TO virodina;