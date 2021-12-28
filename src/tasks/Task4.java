package tasks;

import common.ApiPersonDto;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Задача 4
Список персон класса Person необходимо сконвертировать в список ApiPersonDto
(предположим, что это некоторый внешний формат)
Конвертер для одной персоны уже предоставлен
FYI - DTO = Data Transfer Object - распространенный паттерн, можно погуглить
 */
public class Task4 implements Task {

  // !!! Редактируйте этот метод !!!
  private List<ApiPersonDto> convert(List<Person> persons) {
    return persons.stream()
            .map((Person x) -> convert(x))
            .collect(Collectors.toList()); // Убрал промежуточные переменные
    // По поводу необязательности типа - не вполне согласен. У нас 2 перегруженных метода, но их может быть 22:
    // в таком случае может быть не вполне очевидно, какой метод задействован. Может быть в Java все иначе, но
    // типично наваять 15 перегрузок (для знака +, например) (P.S. Я помню, что Java так не умеет)
  }

  private static ApiPersonDto convert(Person person) {
    ApiPersonDto dto = new ApiPersonDto();
    dto.setCreated(person.getCreatedAt().toEpochMilli());
    dto.setId(person.getId().toString());
    dto.setName(person.getFirstName());
    return dto;
  }

  @Override
  public boolean check() {
    Person person1 = new Person(1, "Name", Instant.now());
    Person person2 = new Person(2, "Name", Instant.now());
    return List.of(convert(person1), convert(person2))
        .equals(convert(List.of(person1, person2)));
  }
}
