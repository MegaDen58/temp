package com.example.onlineauction.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.example.onlineauction.*;
import com.example.onlineauction.constants.Role;
import com.example.onlineauction.controller.authentication.AuthorizationController;
import com.example.onlineauction.controller.authentication.RegistrationController;
import com.example.onlineauction.dao.CategoryDAO;
import com.example.onlineauction.dao.LotDAO;
import com.example.onlineauction.dao.UserDAO;
import com.example.onlineauction.model.Category;
import com.example.onlineauction.model.Lot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static com.example.onlineauction.util.AlertUtil.showAlert;

public class ManagementProductsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButtonManageLots;

    private static final Logger LOGGER = Logger.getLogger(ManagementProductsController.class.getName());

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private DatePicker dateFinish;

    @FXML
    private DatePicker datePublication;

    @FXML
    private TextArea descriptionLotsArea;

    @FXML
    private TextField nameLotsField;

    @FXML
    private Button saveButtonManageLots;

    @FXML
    private TextField startPriceField;

    @FXML
    private TextField stepPriceField;

    @FXML
    private TextField conditionField;

    private CategoryDAO categoryDAO;
    private UserDAO userDAO;
    private int userId;
    private LotDAO lotDAO;
    public static int sellerId;

    public ManagementProductsController() throws SQLException {
    }

    @FXML
    void BackManageLots(ActionEvent event) {
        Stage currentStage = (Stage) backButtonManageLots.getScene().getWindow();
        currentStage.close();
    }
    UserDAO userDAO1 = new UserDAO(DriverManager.getConnection("jdbc:mysql://localhost:3306/auction", "root", "12345"));
    @FXML
    void SaveManageLots(ActionEvent event) throws SQLException {
        String name = nameLotsField.getText();
        String description = descriptionLotsArea.getText();
        LocalDate publicationDate = datePublication.getValue();
        LocalDate finishDate = dateFinish.getValue();
        double startPrice = Double.parseDouble(startPriceField.getText());
        double stepPrice = Double.parseDouble(stepPriceField.getText());
        String condition = conditionField.getText();

        Category selectedCategory = categoryComboBox.getValue();
        int categoryId = selectedCategory.getId();

        int sellerId = 0;
        if (RegistrationController.isRegistred){
            sellerId = RegistrationController.registeredUserId;
        }
        else if(userDAO.getUserRole(AuthorizationController.login, AuthorizationController.password) == Role.SELLER) {
            String login = AuthorizationController.login;
            sellerId = userDAO.getIdByLogin(login);
        }

        int buyerId = 49;

        // Создание объекта лота
        Lot lot = new Lot(name, description, startPrice, startPrice, stepPrice, publicationDate.toString(), finishDate.toString(), condition);
        lot.setSellerId(sellerId);
        lot.setCurrentBuyerId(buyerId); // Установка фиктивного покупателя
        lot.setCategoryId(categoryId);

        // Сохраняем лот в базе данных
        try {
            lotDAO.create(lot);
            showAlert(Alert.AlertType.INFORMATION, "Успеншно!", "Лот успешно добавлен!");
            Stage stageClose = (Stage) saveButtonManageLots.getScene().getWindow();
            stageClose.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка ошибки сохранения лота
        }
    }

    @FXML
    void initialize() {
        try {
            Connection connection = DatabaseConnector.ConnectDb(); // Получаем подключение к базе данных
            categoryDAO = new CategoryDAO(connection);
            lotDAO = new LotDAO(connection);
            // Создаем экземпляр UserDAO
            userDAO = new UserDAO(connection);
            // Заполняем ComboBox категориями из базы данных
            ObservableList<Category> categories = FXCollections.observableArrayList(categoryDAO.getAllCategories());
            categoryComboBox.setItems(categories);
        } catch (Exception e) {
            e.printStackTrace();
            // Обработка ошибки подключения к базе данных
        }
    }

}
