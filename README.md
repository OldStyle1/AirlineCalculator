Задание: Напишите программу на языке программирования
java, которая прочитает файл tickets.json и
рассчитает:
- Минимальное время полета между городами
Владивосток и Тель-Авив для каждого
авиаперевозчика.
- Разницу между средней ценой и медианой для
полета между городами  Владивосток и Тель-Авив. <br><br>
Программа должна вызываться из командной строки
Linux. <br> <br>
Запуск приложения через командную строку java -jar AirlineCalculator-1.0-SNAPSHOT.jar [JSON file path]
<br><br>
Пример запуска программы: java -jar target/AirlineCalculator-1.0-SNAPSHOT.jar tickets.json
<br><br>
Результаты выполнения:
1. Minimum flight time for each carrier from Владивосток to Тель-Авив:
   - BA: 8 h 5 min
   - S7: 6 h 30 min
   - SU: 6 h 0 min
   - TK: 5 h 50 min

2. Price difference for flights between Владисток and Тель-Авив:
   - Average price: 13960,0
   - Median price:  13500,0
   - Difference:    460,0


