package service.dto;

import model.VacationPackage;

public class VacationPackageConverter {
    public static VacationPackageDTO convertToDTO(VacationPackage vacationPackage) {
        VacationPackageDTO vacationPackageDTO = new VacationPackageDTO();

        vacationPackageDTO.setVacationDestinationName(vacationPackage.getName());
        vacationPackageDTO.setName(vacationPackage.getName());
        vacationPackageDTO.setDetails(vacationPackage.getDetails());
        vacationPackageDTO.setPrice(vacationPackage.getPrice());
        vacationPackageDTO.setFrom(vacationPackage.getFrom());
        vacationPackageDTO.setTo(vacationPackage.getTo());
        vacationPackageDTO.setMaxNrOfBookings(vacationPackageDTO.getMaxNrOfBookings());

        return vacationPackageDTO;
    }

    public static VacationPackage convertToEntity(VacationPackageDTO vacationPackageDTO) {
        VacationPackage vacationPackage = new VacationPackage();

        vacationPackage.setName(vacationPackageDTO.getName());
        vacationPackage.setDetails(vacationPackageDTO.getDetails());
        vacationPackage.setPrice(vacationPackageDTO.getPrice());
        vacationPackage.setFrom(vacationPackageDTO.getFrom());
        vacationPackage.setTo(vacationPackageDTO.getTo());
        vacationPackage.setMaxNrOfBookings(vacationPackageDTO.getMaxNrOfBookings());

        return vacationPackage;
    }
}
