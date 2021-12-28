package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    if (persons.size() == 0) {
      return Collections.emptyList();
    }
    //persons.remove(0); эта строка может принести проблемы, т.к. тут модифицируется переданный список
    // это как стрелять в нежелательных потенциальных посетителей боевыми на фейсконтроле в клубе
    return persons.stream()
            .skip(1) // так мы просто пропускаем первый элемент и делаем stream красивее
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return  new HashSet<>(getNames(persons)); // это как раз пример, где stream не нужен
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    return Stream.of(person.getSecondName(), person.getFirstName(), person.getMiddleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" ")); // Да через Stream определенно проще выглядит
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .collect(Collectors.toMap(person -> person.getId()
                    , person -> convertPersonToString(person)
                    , (val1, val2) -> val1)); // Переделал сбор словаря через Stream. Выглядит компактнее.
    // из соображений логики предположил, что повторение значений игнорируются (так было в исходном коде)
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    for (Person person1 : persons1) {
      if (persons2.contains(person1)) { // Предположил, что реализация contains в коллекции эффективнее и проще для чтения
        // она всегда эффективнее, кроме случая неупорядоченной нехешированной коллекции (в этом случае равна)
        return true;
      }
    }
    return false; // return без break'а и убрал переменную has, которая при таком подходе становится лишней
    // насколько смог понять, Stream.anyMatch тут - как те же яйца только в профиль
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(num -> num % 2 == 0).count();
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = true;
    boolean reviewerDrunk = false;
    return codeSmellsGood || reviewerDrunk;
  }
}
