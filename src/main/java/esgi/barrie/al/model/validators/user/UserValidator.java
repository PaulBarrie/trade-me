package esgi.barrie.cc1.model.validators.user;

import esgi.barrie.cc1.model.user.User;
import esgi.barrie.cc1.model.validators.date.DateValidator;
import esgi.barrie.cc1.model.validators.date.LocalDateValidator;

import java.time.format.DateTimeFormatter;

public class UserValidator {
    private final User user;

    private UserValidator(User user) {
        this.user = user;
    }

    public static UserValidator of(User user) {
        return new UserValidator(user);
    }

    public void check() {
        String regexName = "^[^±!@£$%^&*_+§¡€#¢§¶•ªº«\\\\/<>?:;|=.,]{1,20}$";
        //Check firstname
        if(!this.user.getFirstName().matches(regexName)) {
            throw new IllegalArgumentException("Invalid firstname.");
        }
        //Check lastname
        if(!this.user.getLastName().matches(regexName)) {
            throw new IllegalArgumentException("Invalid Lastname.");
        }
        //Check birth
        DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;
        DateValidator validator = new LocalDateValidator(dateFormatter);
        validator.check(user.getBirth());

        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if(!this.user.getEmail().matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid Email.");
        }
    }

}
