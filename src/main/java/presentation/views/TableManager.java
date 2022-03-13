package presentation.views;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import service.dto.VacationPackageDTO;

import java.util.List;

public class TableManager {

        @Getter
        private TableView<VacationPackageDTO> table;

        public TableManager(TableView<VacationPackageDTO> table) {
            this.table = table;
            initTable();
        }

        public void initTable() {
            initColumns();
            table.getSelectionModel().setCellSelectionEnabled(false);
            table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            table.setPlaceholder(new Label("No packages loaded yet."));
        }

        public void updateTable(List<VacationPackageDTO> data){
            table.getItems().clear();
            if(data == null || data.isEmpty()) {
                table.setPlaceholder(new Label("No packages found."));
            }else{
                table.getItems().setAll(data);
            }
        }

        private void initColumns() {
            TableColumn<List<String>,String>[] columns = table.getColumns().toArray(new TableColumn[0]);
            columns[0].setCellValueFactory(new PropertyValueFactory<>("name"));
            columns[1].setCellValueFactory(new PropertyValueFactory<>("vacationDestinationName"));
            columns[2].setCellValueFactory(new PropertyValueFactory<>("details"));
            columns[3].setCellValueFactory(new PropertyValueFactory<>("price"));
            columns[4].setCellValueFactory(new PropertyValueFactory<>("from"));
            columns[5].setCellValueFactory(new PropertyValueFactory<>("to"));
            if(table.getColumns().size() > 6) {
                columns[6].setCellValueFactory(new PropertyValueFactory<>("packageStatus"));
            }
        }

}
