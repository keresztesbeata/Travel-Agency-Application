package service;

import model.User;
import model.VacationPackage;
import repository.UserRepository;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.roles.RegularUserRole;
import service.validators.InputValidator;
import service.validators.UserValidator;

import java.util.List;

public class RegularUserService extends UserService implements RegularUserRole {
    private static UserRepository userRepository = UserRepository.getInstance();
    private InputValidator<User> userValidator = new UserValidator();

    public void register(User user) throws InvalidInputException {
        userValidator.validate(user);

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new InvalidInputException("The username: " + user.getUsername() + " is already taken! Please select another one!");
        }
        userRepository.save(user);
    }


    @Override
    public void findBookedVacationPackagesByUser(User user) {
    }

    @Override
    public void bookVacationPackage(VacationPackage vacationPackage) throws InvalidOperationException {
        // check if it is fully booked/!!!!if user has already booked this package
        // update vacation package: count and status (== 0 -> +1 -> IN_PROGRESS)
        // (==max-1 -> +1 -> BOOKED)
    }

    @Override
    public List<VacationPackage> findAvailableVacationPackages() {
        // get list of packages which are NOT_BOOKED or IN_PROGRESS
        return null;
    }
}
