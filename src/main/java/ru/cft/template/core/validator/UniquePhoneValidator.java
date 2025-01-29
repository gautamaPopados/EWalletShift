package ru.cft.template.core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.cft.template.core.repository.UserRepository;

@Component
public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return phone != null && !userRepository.existsByPhone(phone);
    }
}