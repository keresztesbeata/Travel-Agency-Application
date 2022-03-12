package service.facade;

import model.UserType;
import service.roles.UserRole;

public class UserServiceFactory {
    public UserRole getUserRole(UserType userType) {
        switch (userType) {
            case REGULAR_USER:
                return new RegularUserServiceFacade();
            case TRAVEL_AGENCY:
                return new TravelAgencyServiceFacade();
            default:
                return null;
        }
    }
}
