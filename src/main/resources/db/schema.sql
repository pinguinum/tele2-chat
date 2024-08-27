CREATE SCHEMA IF NOT EXISTS tele2_chat;
GRANT CREATE ON SCHEMA tele2_chat TO admin;

-- Создание таблицы chat_users
CREATE TABLE IF NOT EXISTS tele2_chat.chat_users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100),
    email VARCHAR(100) NOT NULL,
    picture TEXT
    );

-- Создание таблицы messages
CREATE TABLE IF NOT EXISTS tele2_chat.message (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES tele2_chat.chat_users(id) ON DELETE CASCADE,
    text TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Создание индексов
CREATE INDEX IF NOT EXISTS idx_chat_users_username ON tele2_chat.chat_users(username);
CREATE INDEX IF NOT EXISTS idx_chat_users_id ON tele2_chat.chat_users(id);
CREATE INDEX IF NOT EXISTS idx_messages_timestamp ON tele2_chat.messages(timestamp);
CREATE INDEX IF NOT EXISTS idx_messages_user_id ON tele2_chat.messages(user_id);
;