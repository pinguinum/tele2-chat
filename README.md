# Проект tele2-chat

Проект **tele2-chat** представляет собой чат-приложение с поддержкой аутентификации через Google и регистрацию по электронной почте.

## Шаги по запуску проекта

### 1. Подготовка базы данных

Перед запуском приложения необходимо создать и настроить базу данных. Для этого выполните следующие действия:

1. **Запустите SQL скрипт `create_bd.sql`**:
    - Найдите файл `create_bd.sql` в директории `src/main/resources`.
    - Выполните этот SQL скрипт в вашей базе данных PostgreSQL.
2. **Запустите SpringBoot**:
   - Класс Tele2ChatApplication
3. **Откройте браузер**:
   - Перейдите по пути http://localhost:8080/
4. **Авторизация**:
   - Авторизуйтесь с помощью почты или google

P.S к сожалению, на выходных было мало времени на кодинг, поэтому как всегда разработка полным ходом велась в последний день, есесьна. Только что увидела условие на отображение пользователей в сети, почему то не увидела это условие в начале разработки. Если будет время в течении рабочего дня добавлю эту фичу. Спасибо :) Сама вижу, что хочется переделать и сделать краивше, но имеем что имеем)
    

