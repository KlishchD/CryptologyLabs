<h3>Імплементація</h3>
Для кожного з алгоритмів виконано дві імплементації: самостійні та з використанням функціоналу бібліотек Java. У кожному з класів є свій метод main, звідки можна запустити відповідний алгоритм.

<h3>AES</h3>

*AES*, що стоїть за "Advanced Encryption Standard", це симетричний блочний алгоритм шифрування, прийнятий у 2001 році NIST (Національним інститутом стандартів та технологій США) як стандарт. Він замінив алгоритм DES, що був широко використовувався раніше, але був підданий критиці за низьку розмірність ключа та потенційну вразливість до атак "вгадуванням" ключа.

AES - це блочний шифр, що працює з даними розміром в 128 біт і використовує ключі довжиною 128, 192 або 256 бітів. Це означає, що він шифрує дані по блоках по 128 бітів за допомогою ключа вказаної довжини. AES базується на дизайні, названому Rijndael, розробленим двома бельгійськими криптографами, Йоаном Дайменом та Вінсентом Рейменом.

Алгоритм AES використовує ряд трансформацій, що включають:

Розширення ключа
Заміну байтів за допомогою S-блоку,
Перестановку байтів у рядках,
Зміну кожного стовпця матриці,
Додавання раундового ключа до блоку.
Ці трансформації повторюються кілька разів в процесі шифрування, кількість циклів (або "раундів") залежить від довжини ключа: 10 раундів для 128-бітних ключів, 12 для 192-бітних і 14 для 256-бітних ключів.

AES вважається дуже безпечним і використовується урядами, військовими організаціями та багатьма корпораціями по всьому світу. Він використовується в багатьох протоколах безпеки, включаючи SSL і TLS для безпечного з'єднання в інтернеті, а також у бездротових мережах стандарту IEEE 802.11i (WPA2).

<h3>DSA</h3>
*DSA* (Digital Signature Algorithm) є криптографічним алгоритмом підпису, який використовується для забезпечення цілісності, аутентифікації та невідмовності повідомлень. Він був розроблений Національним інститутом стандартів і технологій (NIST) США і опублікований у 1994 році.

DSA базується на математичних проблемах теорії чисел, зокрема, на проблемі дискретного логарифму. Цей алгоритм використовується для створення цифрового підпису повідомлення, який може бути перевірений отримувачем, щоб переконатися в автентичності повідомлення та його невідмовності.

Основні кроки в DSA наступні:

Генерація ключів:

Генерується випадковий приватний ключ, який є секретним.
З приватного ключа обчислюється відповідний публічний ключ.

Підпис повідомлення:

Вибирається випадкове число (називається "криптографічною випадковістю") і обчислюється підпис, який включає дане число.
Підпис обчислюється шляхом взяття дискретного логарифму за певними правилами.

Перевірка підпису:

Отримувач повідомлення використовує публічний ключ, повідомлення та підпис для перевірки автентичності.
Виконується обчислення, яке дозволяє перевірити, чи є підпис автентичним.
DSA має криптографічні властивості, такі як унікальність підпису, незворотність підпису, відсутність конфліктів підпису, а також важко обчислюваність приватного ключа або дискретного логарифму.

Важливо зазначити, що DSA використовується лише для підпису повідомлень і не забезпечує конфіденційність шляхом шифрування.
