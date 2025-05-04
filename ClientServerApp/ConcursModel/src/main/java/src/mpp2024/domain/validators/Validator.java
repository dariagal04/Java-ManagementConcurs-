package src.mpp2024.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}