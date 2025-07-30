package pet.project.hh.exceptions;

public class VacancyNotFoundException extends RuntimeException {
    public VacancyNotFoundException() {
        super("Такой вакансии не существует");
    }
}
