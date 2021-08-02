Тестовое задание:

Написать автоматический сценарий/сценарии для проверки фильтрации записей по тегам на сайте https://demo.realworld.io. Добавить документацию, необходимую для работы с проектом автоматизации.

Поправка:

Оригинальный сайт работает крайне нестабильно, API отдает 504 ошибки. То же и с большинством других реализаций данного приложения. Удалось найти наиболее близкий и рабочий аналог по адресу https://cirosantilli-realworld-next.herokuapp.com/ (https://github.com/cirosantilli/node-express-sequelize-nextjs-realworld-example-app)

Требования к системе:
- Windows 7-10
- [Java 8](https://www.oracle.com/java/technologies/javase-jdk16-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [NodeJS](https://nodejs.org/uk/download/)
- [Allure commandline](https://www.npmjs.com/package/allure-commandline)

Запуск: 

Открыть системную консоль (cmd), в ней перейти к директории с проектом и там запустить следующую команду: `mvn clean test -e -DxmlSuite=mainSuite.xml`

Отчет:

Чтобы открыть отчет по прошедшим тестам, запустите файл `allureServe.bat` в папке проекта
