package service.facade;

import model.User;
import model.UserType;
import model.VacationDestination;
import repository.FilterConditions;
import service.dto.VacationPackageConverter;
import service.dto.VacationPackageDTO;
import service.exceptions.InvalidInputException;
import service.exceptions.InvalidOperationException;
import service.managers.UserManager;
import service.managers.VacationDestinationManager;
import service.managers.VacationPackageManager;
import service.roles.RegularUserRole;

import java.util.List;
import java.util.stream.Collectors;

public class RegularUserServiceFacade extends UserService implements RegularUserRole {
    private UserManager userManager = new UserManager();
    private VacationPackageManager vacationPackageManager = new VacationPackageManager();
    private VacationDestinationManager vacationDestinationManager = new VacationDestinationManager();

    @Override
    public void bookVacationPackage(VacationPackageDTO vacationPackageDTO) throws InvalidOperationException {
        vacationPackageManager.bookVacationPackage(vacationPackageDTO, getCurrentUser());
    }

    @Override
    public List<VacationPackageDTO> findBookedVacationPackagesOfCurrentUser() {
        return vacationPackageManager.findBookedVacationPackagesOfUser(getCurrentUser());
    }

    @Override
    public List<VacationPackageDTO> findAvailableVacationPackages() {
        return vacationPackageManager.findAvailableVacationPackages(getCurrentUser());
    }

    @Override
    public VacationPackageDTO findVacationPackageByName(String vacationPackageName) {
        return VacationPackageConverter.convertToDTO(vacationPackageManager.findByName(vacationPackageName));
    }

    @Override
    public List<VacationPackageDTO> filterVacationPackagesByConditions(FilterConditions filterConditions) {
        List<String> bookedPackageNames = findBookedVacationPackagesOfCurrentUser()
                .stream().map(VacationPackageDTO::getName)
                .collect(Collectors.toList());
        List<VacationPackageDTO> filteredPackages = vacationPackageManager.filterByConditions(filterConditions);
        filteredPackages.removeIf(vacationPackageDTO -> bookedPackageNames.contains(vacationPackageDTO.getName()));
        return filteredPackages;
    }

    @Override
    public void register(User user) throws InvalidInputException {
        userManager.register(user);
    }

    @Override
    public List<String> findAllDestinations() {
        return vacationDestinationManager.findAll()
                .stream()
                .map(VacationDestination::getName)
                .collect(Collectors.toList());
    }

}
