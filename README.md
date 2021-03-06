# jmpp242
**Логика работы программы**
1. Если нет БД пользователей, то создается временный пользователь
   1. имя _admin_ 
   2. пароль _admin_
   3. роль ADMIN
2. Операции доступные для роли ADMIN
   1. работа по ссылке /admin
   2. создать пользователя
   3. изменить данные пользователя
   4. удалить пользователя
   5. выход из системы
3. Операции доступные для роли USER
   1. работа по ссылке /user
   2. просмотр своих данных
   3. выход из системы
4. Особенности:
   1. Пользователь с ролями ADMIN и USER имеет доступ к обеим ссылкам
   2. Права авторизованого администратора динамически меняются после изменения его ролей


**Планы/Вопросы/Идеи**
1. не разобрался с запросами POST, GET, PUT, DELETE
2. при вводе слишком большого числа в поле Возраст возникает ошибка 400. Почему?
3. join fetch [Теория](https://habr.com/ru/company/otus/blog/529692/)

**Сделано 2021.12.11**
<li>"причесал" код</li>
<li>сделал динамическую смену роли текущего юзера при редактировании его ролей</li>
<li>Доделал авторизацию временного пользователя admin при пустой БД</li>

**Подсказки по динамической смене ролей**
[Здесь смотри первый ответ](https://stackru.com/questions/31497180/kak-izmenit-roli-spring-security-po-kontekstu)

**Сделано 2021.12.10**
<li>Проверил создание, изменение, удаление</li>
<li>отладил запуск, аутентификацию и авторизацию при пустой БД</li>
<li>отладил бекэнд в части связи с БД</li>
<li>создал 3 таблицы: юзеры, роли, связи</li>
<li>проверил загрузку данных из БД</li>
<li>проверил создание и удаление новых пользователей</li>

**Сделано 2021.12.09**
<li>фронтенд полностью отлажен</li>
<li>сделал логаут</li>
<li>отладил форму администратора</li>
<li>отладил форму юзера</li>
<li>сделал слой DAO в памяти для операций с пользователями</li>
<li>Сделал корректное отображение данных пользователя - роль=Объект</li>
<li>Реализовал слой DAO, работающий с пользователями в памяти</li>
<li>Реализовал добавление нового пользователя в памяти</li>
<li>Сделал класс Role</li>
<li>Передал класс User. Добавил поле Role</li>

**Сделано 2021.12.08**
<li>Переделываю html-форму на вызовы PUT, PATCH, DELETE</li>
<li>?Не могу идентифицировать в контроллере вызов с html-формы</li>


**Сделано 2021.12.07**
<li>Сделал форму админа с URL /admin</li>
<li>Сделал контроллер для админа</li>

**Сделано 2021.12.06**
<ul>
<li>Добавил доступ роль-URL</li>
<li>Сделал в памяти 3 пользователя с разными ролями</li>
</ul>