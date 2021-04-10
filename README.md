<img src="https://i.ibb.co/h28txBv/androqr-bg.jpg"/>

# AndroQR - Проход на мероприятие по QR коду
---
## Переводы
🇷🇺 [Russian](README.ru.md)

### Необходимое
- Устройство (или эмулятор https://ru.bignox.com/) на ОС Android версии 5+
- Интернет браузер

## Мобильное приложение AndroQR
### Первый запуск

1) Скачать APK (https://github.com/dSe1Ex4/androQR/releases/download/Beta0.3/androQr.apk)
2) Установить приложение на ваше устройство
3) Авторизоваться
```YML
Логин: admin
Пароль: 123
```



<img src="https://i.ibb.co/F7bHmJv/image.png" alt="image" width=240px height=480px></img>

4) Отсканировать выданый QR код (см. [Регистрация на мероприятии](#регистрация-на-мероприятии))

<img src="https://i.ibb.co/HH018n5/photo-2021-04-08-22-12-06.jpg" alt="image" width=240px height=480px></img>

5) Просмотреть и проверить данные


### Тестовый QR код
<img src="https://i.ibb.co/6n46mBg/asfhaiousf.png"></img>

## Бекенд
[Наш Бекенд](https://github.com/dSe1Ex4/androQR-web)

## Сайт 
### Регистрация на мероприятии
1. Зайти на сайт https://212.22.66.109:8080/person/index
2. Так как на нашем сайте используется бесплатный *самоподписанный* сертификат (для шифрования данных), и ваш браузер не сможет его проверить, вы увидете данное окно
<img src="https://i.ibb.co/71ZpSWQ/image.png" width=860px height=480px></img>
3. Нажмите кнопку "Дополнительно" -> "Принять риск и продолжить"
4. Выбрать мероприятие 
5. Ввести необходимые данные и загрузить фотографию
6. Нажать Submit и скачать QR Code
7. Просканировать код приложением (см. [Мобильное приложение AndroQR](#мобильное-приложение-androqr))
