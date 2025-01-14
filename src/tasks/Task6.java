package tasks;

import common.Area;
import common.Person;
import common.Task;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 implements Task {

  private Set<String> getPersonDescriptions(Collection<Person> persons,
                                            Map<Integer, Set<Integer>> personAreaIds,
                                            Collection<Area> areas) {
    Map<Integer, String> areaMap = areas.stream()
            .collect(Collectors.toMap(Area::getId, Area::getName));
    Map<Integer, List<String>> map = personAreaIds.keySet().stream()
            .collect(Collectors.toMap(integer -> integer, personId -> personAreaIds.get(personId).stream()
                    .map(areaMap::get)
                    .collect(Collectors.toList())));
    return persons.stream()
            .map((Person p) -> getStrings(p.getFirstName(), map.get(p.getId())))
            .flatMap(Collection::stream)
            .collect(Collectors.toSet()); // Добавил промежуточные мапы. Вроде бы все выглядит лучше и быстрее
  }

  private List<String> getStrings (String name, List<String> filteredAreas){
    return filteredAreas.stream()
            .map((String s) -> name + " - " + s)
            .collect(Collectors.toList());
  }

  @Override
  public boolean check() {
    List<Person> persons = List.of(
        new Person(1, "Oleg", Instant.now()),
        new Person(2, "Vasya", Instant.now())
    );
    Map<Integer, Set<Integer>> personAreaIds = Map.of(1, Set.of(1, 2), 2, Set.of(2, 3));
    List<Area> areas = List.of(new Area(1, "Moscow"), new Area(2, "Spb"), new Area(3, "Ivanovo"));
    return getPersonDescriptions(persons, personAreaIds, areas)
        .equals(Set.of("Oleg - Moscow", "Oleg - Spb", "Vasya - Spb", "Vasya - Ivanovo"));
  }
}
