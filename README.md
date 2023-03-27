# Laborator JAVA

## Orar
| Ziua          | Grupa        | Ora          | Sala         |
| ------------- |:-------------|:-------------|:-------------|
| MARȚI         | 1053         | 7:30         | 2317       |

#### Mediu de dezvoltare recomandat: IntelliJ IDEA v2021.3 (JetBrains)

Versiune JDK recomandată: 11 (JetBrains Runtime version 11 - disponibilă implicit în C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2021.3.n\jbr)

#### Descărcare - versiune Community:  https://www.jetbrains.com/idea/download/#section=windows

#### Tutorial creare proiect: https://www.jetbrains.com/help/idea/creating-and-running-your-first-java-application.html

De urmărit:
- utilizare params din linia de comandă
- redirectare fluxuri standard de intrare / ieșire (în special preluarea datelor de intrare din fișiere text)

### Alte resurse utile: 
#### Stack Versus Heap: https://youtu.be/IX3fDYz0WyM

#### Interfețe funcționale standard:
- Function<T,R> - funcție apply care primește un parametru de tip T și întoarce o valoare de tip R
- Predicate<T> - funcție test care primește un parametru de tip T  și întoarce o valoare booleană
- UnaryOperator<T> - funcție apply care primește un parametru de tip T și întoarce o valoare de tip T
- BinaryOperator<T> - funcție apply care primește doi parametri de tip T și întoarce o valoare de tip T
- Consumer<T> - funcție accept care primește un parametru de tip T și consumă valoarea fără a întoarce un rezultat
- Supplier<T> - funcție get care nu primește params și generează un rezultat de tip T
