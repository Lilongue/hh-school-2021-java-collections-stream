package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

  // !!! Редактируйте этот метод !!!
  private List<Person> findOrderedPersons(List<Integer> personIds) {
    Map<Integer, Person> personMap = PersonService.findPersons(personIds).stream()
            .collect(Collectors.toMap(Person::getId, person -> person));
    return personIds.stream()
            .map(personMap::get)
            .collect(Collectors.toList()); // Если сделать через промежуточный map, то вывод листа будет за время O(n)
    // Однако сама сборка map затрудняюсь ответить за сколько происходит, если это тоже O(n), то суммарно все еще O(n)
    // Ранее у меня O(n^2) было, так как внутри стрима еще по каждому ID при сортировке пробегал
  }

  @Override
  public boolean check() {
    List<Integer> ids = List.of(1, 2, 3);

    return findOrderedPersons(ids).stream()
        .map(Person::getId)
        .collect(Collectors.toList())
        .equals(ids);
  }

}
