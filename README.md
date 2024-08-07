# Задание
Написать демонстрационное Android приложение для просмотра списка

товаров в виде карточек с использованием фреймворка Jetpack Compose и библиотеки Room Database.

Поощряется разумное использование вспомогательных библиотек, например для внедрения зависимостей.

Скриншоты примера выполненного задания представлены в папке /screenshots.

Дополнительные материалы для разработки мобильного приложения представлены 

в папке /assets. Рабочий прототип приложения представлен в папке /app.

# Описание функционала

• Просмотр списка всех товаров

• Поиск товаров по названию

• Карточка товара должна содержать ВСЕ поля из БД

• Редактирование количества товара с сохранением в БД

• Удаление карточки товара из БД

• Теги товара должны быть оформлены в виде чипсов (Chips)


# Обязательные требования
• Kotlin v2.0.0 и выше

• Min SDK API – 23 (Android 6)

• Gradle Kotlin DSL

• Version Catalog (TOML)

• Jetpack Compose

• Room Database

• UI должен быть полностью описан декларативным кодом (без XML)

ссылка на полное задание:  https://disk.yandex.ru/d/h4gg3wzQCbbRLA

# Мои примечания
1. База данных не нормализованна, так как присутствуют не атомарные атрибуты, надо ли ее нармолизовать?

Так как есть предзаполненная база данных, структуру не меняем, но как вариант -
Разбить Item на 2 сущности Item и Tag, создать отношение многие к многим, которое связывает, предметы и теги

3. Какой Di использовать?

использовал hilt.
