package service.managers;

import model.PackageStatus;
import model.User;
import model.VacationDestination;
import model.VacationPackage;
import repository.FilterConditions;
import repository.UserRepository;
import repository.VacationDestinationRepository;
import repository.VacationPackageRepository;
import service.dto.VacationPackageConverter;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.validators.InputValidator;
import service.validators.VacationPackageValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class VacationPackageManager {
    private static VacationPackageRepository vacationPackageRepository = VacationPackageRepository.getInstance();
    private static VacationDestinationRepository vacationDestinationRepository = VacationDestinationRepository.getInstance();
    private static UserRepository userRepository = UserRepository.getInstance();
    private InputValidator<VacationPackageDTO> vacationPackageValidator;

    public VacationPackageManager() {
        vacationPackageValidator = new VacationPackageValidator();
    }

    public void add(VacationPackageDTO vacationPackageDTO) throws InvalidInputException {
        if (vacationPackageRepository.findByName(vacationPackageDTO.getName()) != null) {
            throw new InvalidInputException("Duplicate name! Another vacation package: " + vacationPackageDTO.getName() + " with the same name has already been added!");
        }
        VacationPackage vacationPackage = validateAndConvertVacationPackageDTO(vacationPackageDTO);
        vacationPackageRepository.save(vacationPackage);
    }

    private VacationPackage validateAndConvertVacationPackageDTO(VacationPackageDTO vacationPackageDTO) throws InvalidInputException {
        vacationPackageValidator.validate(vacationPackageDTO);
        VacationDestination vacationDestination = vacationDestinationRepository.findByName(vacationPackageDTO.getVacationDestinationName());
        if (vacationDestination == null) {
            throw new InvalidInputException("No vacation destination exists with the name: " + vacationPackageDTO.getVacationDestinationName() + "!");
        }
        VacationPackage vacationPackage = VacationPackageConverter.convertToEntity(vacationPackageDTO);
        vacationPackage.setVacationDestination(vacationDestination);
        return vacationPackage;
    }


    public void edit(VacationPackageDTO vacationPackageDTO) throws InvalidInputException {
        VacationPackage existingVacationPackage = vacationPackageRepository.findByName(vacationPackageDTO.getName());
        if (existingVacationPackage == null) {
            throw new InvalidInputException("Inexistent vacation package! The package cannot be updated because, no vacation package with the name: " + vacationPackageDTO.getName() + " exists!");
        }
        VacationPackage vacationPackage = validateAndConvertVacationPackageDTO(vacationPackageDTO);
        vacationPackage.setId(existingVacationPackage.getId());
        vacationPackage.setNrOfBookings(existingVacationPackage.getNrOfBookings());
        vacationPackage.setPackageStatus(existingVacationPackage.getPackageStatus());
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
            FilterConditions filterByDestination = new FilterConditions.FilterConditionsBuilder()
                    .withDestinationName(vacationDestinationName)
                    .build();
            vacationPackageRepository.filterByConditions(filterByDestination)
                    .stream()
                    .map(VacationPackage::getId)
                    .forEach(id -> vacationPackageRepository.delete(id));
        }
    }

    public List<VacationPackageDTO> findAll() {
        return vacationPackageRepository.findAll()
                .stream()
                .map(VacationPackageConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public VacationPackage findByName(String name) {
        return vacationPackageRepository.findByName(name);
    }

    public List<VacationPackageDTO> filterByStatus(List<PackageStatus> packageStatuses) {
        return vacationPackageRepository.filterByStatus(packageStatuses)
                .stream()
                .map(VacationPackageConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<VacationPackageDTO> filterByConditions(FilterConditions filterConditions) {
        if (filterConditions.getDestinationName().isPresent()) {
            filterConditions.setVacationDestination(vacationDestinationRepository.findByName(filterConditions.getDestinationName().get()));
        }
        return vacationPackageRepository.filterByConditions(filterConditions)
                .stream()
                .map(VacationPackageConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<VacationPackageDTO> findBookedVacationPackagesOfUser(User user) {
        return user.getVacationPackages()
                .stream()
                .map(VacationPackageConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public void bookVacationPackage(VacationPackageDTO vacationPackageDTO, User user) throws InvalidOperationException {
        VacationPackage vacationPackage = vacationPackageRepository.findByName(vacationPackageDTO.getName());
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

    public List<VacationPackageDTO> findAvailableVacationPackages(User user) {
        return vacationPackageRepository.filterByStatus(Arrays.asList(PackageStatus.NOT_BOOKED, PackageStatus.IN_PROGRESS))
                .stream()
                .filter(vacationPackage -> vacationPackage.getUsers()
                        .stream()
                        .noneMatch(booker -> booker.getId().equals(user.getId())))
                .map(VacationPackageConverter::convertToDTO)
                .collect(Collectors.toList());
    }
}
