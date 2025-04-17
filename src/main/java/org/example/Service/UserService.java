package org.example.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.Enity.User;
import org.example.repository.UserRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class UserService {
   private final UserRepo userRepo;

   public UserService(UserRepo userRepo) {
       this.userRepo = userRepo;
   }

   public void createUser(String name, String email, Integer age) {
       User user = new User().builder()
               .name(name)
               .email(email)
               .age(age)
               .created_at(LocalDate.now())
               .build();
       validateUser(user);
       userRepo.save(user);
   }

   public List<User> getAllUsers() {
       return userRepo.findAll();
   }
   public User getUserById(int id) {
       return userRepo.findById(id);
   }

   public void updateUser(int id, String newName, String newEmail, Integer newAge) {
       User user = userRepo.findById(id);
       if (user != null) {
           user.setName(newName);
           user.setEmail(newEmail);
           user.setAge(newAge);
           validateUser(user);
           userRepo.update(user);
       }
   }

   public void deleteUser(int id) {
       userRepo.deleteById(id);
   }

   public void validateUser(User user) {
       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
       Validator validator = factory.getValidator();

       Set<ConstraintViolation<User>> violations = validator.validate(user);
       if (!violations.isEmpty()) {
           StringBuilder errors = new StringBuilder("Ошибки валидации:\n");
           for (ConstraintViolation<User> violation : violations) {
               errors.append("- ").append(violation.getMessage()).append("\n");
           }
           throw new IllegalArgumentException(errors.toString());
       }
   }
}
