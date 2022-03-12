package service.managers;

import model.PackageStatus;
import model.User;
import model.VacationDestination;
import model.VacationPackage;
import repository.UserRepository;
import repository.VacationDestinationRepository;
import repository.VacationPackageRepository;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.validators.InputValidator;
import service.validators.VacationPackageValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VacationPackageManager {
    private static VacationPackageRepository vacationPackageRepository = VacationPackageRepository.getInstance();
    private static VacationDestinationRepository vacationDestinationRepository = VacationDestinationRepository.getInstance();
    private static UserRepository userRepository = UserRepository.getInstance();
    private InputValidator<VacationPackage> vacationPackageValidator;

    public VacationPackageManager() {
        vacationPackageValidator = new VacationPackageValidator();
    }

    public void add(VacationPackage vacationPackage) throws InvalidInputException {
        vacationPackageValidator.validate(vacationPackage);

        if (vacationPackageRepository.findByName(vacationPackage.getName()) != null) {
            throw new InvalidInputException("Duplicate name! Another vacation package: " + vacationPackage.getName() + " with the same name has already been added!");
        }
        vacationPackageRepository.save(vacationPackage);
    }

    public void edit(VacationPackage vacationPackage) throws InvalidInputException {
        vacationPackageValidator.validate(vacationPackage);

        if (vacationPackageRepository.findByName(vacationPackage.getName()) != null) {
            throw new InvalidInputException("Duplicate name! Another vacation package: " + vacationPackage.getName() + " with the same name has already been added!");
        }
        vacationPackageRepository.update(vacationPackage);
    }

    public void delete(String name) throws InvalidOperationException {
        VacationPackage vacationPackage = vacationPackageRepository.findByName(name);

        if (vacationPackage == null) {
            throw new InvalidOperationException("The vacation package: " + name + " cannot be deleted, because it doesn't exist!");
        }

        if (vacationPackage.getPackageStatus().equals(PackageStatus.NOT_BOOKED) && !vacationPackage.getUsers().isEmpty()) {
            throw new InvalidOperationException("The vacation package: " + name + " cannot be deleted, because it is booked by some users!");
        }
        vacationPackageRepository.delete(vacationPackage.getId());
    }

    public void deletePackagesOfDestination(String vacationDestinationName) {
        VacationDestination vacationDestination = vacationDestinationRepository.findByName(vacationDestinationName);
        if (vacationDestination != null) {
            vacationPackageRepository.filterByDestination(vacationDestination)
                    .stream()
                    .map(VacationPackage::getId)
                    .forEach(id -> vacationPackageRepository.delete(id));
        }
    }

    public List<VacationPackage> findAll() {
        return vacationPackageRepository.findAll();
    }

    public VacationPackage findByName(String name) {
        return vacationPackageRepository.findByName(name);
    }

    public List<VacationPackage> filterByStatus(List<PackageStatus> packageStatuses) {
        return vacationPackageRepository.filterByStatus(packageStatuses);
    }

    public List<VacationPackage> filterVacationPackagesByDestination(String destination) {
        VacationDestination vacationDestination = vacationDestinationRepository.findByName(destination);
        if (vacationDestination == null) {
            return findAll();
        }
        return vacationPackageRepository.filterByDestination(vacationDestination);
    }

    public List<VacationPackage> filterVacationPackagesByPeriod(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return vacationPackageRepository.filterByPeriod(startDate, endDate);
        }
        if (startDate != null) {
            return vacationPackageRepository.filterByStartDate(startDate);
        }
        if (endDate != null) {
            return vacationPackageRepository.filterByEndDate(endDate);
        }
        return findAll();
    }

    public List<VacationPackage> filterVacationPackagesByPrice(Double minPrice, Double maxPrice) {
        return vacationPackageRepository.filterByPrice(minPrice, maxPrice);
    }

    public List<VacationPackage> filterVacationPackagesByKeyword(String keyword) {
        return vacationPackageRepository.filterByKeyword(keyword);
    }

    public List<VacationPackage> findBookedVacationPackagesOfUser(User user) {
        return new ArrayList<>(user.getVacationPackages());
    }

    public void bookVacationPackage(VacationPackage vacationPackage, User user) throws InvalidOperationException {
        if (vacationPackage.getNrOfBookings() >= vacationPackage.getMaxNrOfBookings()) {
            throw new InvalidOperationException("This vacation package is already fully booked! Please choose another package.");
        }
        Set<User> users = vacationPackage.getUsers();
        if (users.contains(user)) {
            throw new InvalidOperationException("This vacation package is already booked by you!");
        }
        int nrOfBookings = vacationPackage.getNrOfBookings();
        if (nrOfBookings == 0) {
            vacationPackage.setPackageStatus(PackageStatus.IN_PROGRESS);
        } else if (nrOfBookings == vacationPackage.getMaxNrOfBookings() - 1) {
            vacationPackage.setPackageStatus(PackageStatus.BOOKED);
        }
        user.addVacationPackage(vacationPackage);
        userRepository.update(user);
        vacationPackageRepository.update(vacationPackage);
    }

    public List<VacationPackage> findAvailableVacationPackages(User user) {
        return vacationPackageRepository.filterByStatus(Arrays.asList(PackageStatus.NOT_BOOKED, PackageStatus.IN_PROGRESS))
                .stream()
                .filter(vacationPackage -> vacationPackage.getUsers()
                        .stream()
                        .noneMatch(booker -> booker.getId().equals(user.getId())))
                .collect(Collectors.toList());
    }
}
