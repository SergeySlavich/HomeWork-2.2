import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args){
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        //1. Найти количество несовершеннолетних (т.е. людей младше 18 лет).

        System.out.println("1. Количество несовершеннолетних.");
        long count = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println(count);
        System.out.println();

        //2. Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).

        System.out.println("2. Список фамилий призывников.");
        persons.stream()
                .filter(person -> person.getSex().equals(Sex.MAN))
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() <= 27)
                .map(Person::getFamily)
                .forEach(System.out::println);
        System.out.println();

        //3. Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
        // (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).

        System.out.println("3. Отсортированный по фамилии список потенциально работоспособных людей с высшим образованием.");
        List<Person> workableMan = persons.stream()
                .filter(person -> person.getSex().equals(Sex.MAN))
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() <= 65)
                .collect(Collectors.toList());

        List<Person> workableWoman = persons.stream()
                .filter(person -> person.getSex().equals(Sex.WOMAN))
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getAge() <= 60)
                .collect(Collectors.toList());

        List<Person> workable = new ArrayList<>();
        workable.addAll(workableMan);
        workable.addAll(workableWoman);
        workable.stream()
                .sorted(Comparator.comparing(Person::getFamily))
                .forEach(System.out::println);
    }
}
