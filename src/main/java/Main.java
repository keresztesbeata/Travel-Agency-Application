import javafx.application.Application;
import model.User;
import model.VacationDestination;
import model.VacationPackage;
import presentation.views.MainView;
import repository.VacationPackageRepository;
import service.exceptions.InvalidInputException;
import service.facade.TravelAgencyServiceFacade;

public class Main {
    public static void main(String[] args) {
        //Application.launch(MainView.class);
        try {

            TravelAgencyServiceFacade travelAgencyServiceFacade = new TravelAgencyServiceFacade();
            travelAgencyServiceFacade.login(new User("admin", "admin"));
            System.out.println(travelAgencyServiceFacade.getCurrentUser());

        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
    }
}
