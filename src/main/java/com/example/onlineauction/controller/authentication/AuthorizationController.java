package com.example.onlineauction.controller.authentication;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.example.onlineauction.*;
import com.example.onlineauction.constants.Role;
import com.example.onlineauction.constants.Status;
import com.example.onlineauction.dao.UserDAO;
import com.example.onlineauction.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static com.example.onlineauction.util.AlertUtil.showAlert;

public class AuthorizationController {

    @FXML
    private Button authorizationButtonUser;

    @FXML
    private Button exitButtonAuthorization;

    @FXML
    private Button goToRegistrationButton;

    @FXML
    private TextField loginUserFieldAuth;

    @FXML
    private PasswordField passwordUserFieldAuth;

    public static int userId;

    public static String login;
    public static String password;


    @FXML
    void Authorization(ActionEvent event) throws Exception {
        login = loginUserFieldAuth.getText();
        password = passwordUserFieldAuth.getText();

        // Проверка заполнения полей логина и пароля
        if (login.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Пожалуйста, заполните все поля.");
            return;
        }

        Connection connection = DatabaseConnector.ConnectDb();
        UserDAO userDAO = new UserDAO(connection);

        try {
            // Проверка аутентификации пользователя
            Role userRole = userDAO.getUserRole(login, password);
            if (userRole != null) {
                // Получение пользователя по логину
                User user = userDAO.getUserByLogin(login);
                if (user != null) {
                    // Проверка статуса пользователя
                    if (user.getStatus() == Status.BLOCK) {
                        // Аккаунт заблокирован
                        showAlert(Alert.AlertType.ERROR, "Ошибка!", "Ваш аккаунт заблокирован! Свяжитесь с администратором" );
                        return;
                    }

                    // Аутентификация успешна
                    String fxmlPath;
                    String title;
                    switch (userRole) {
                        case BUYER:
                            fxmlPath = "/com/example/onlineauction/buyer/buyer-view.fxml";
                            title = "Окно покупателя";
                            break;
                        case SELLER:
                            fxmlPath = "/com/example/onlineauction/seller/seller-view.fxml";
                            title = "Окно продавца";
                            break;
                        case ADMIN:
                            fxmlPath = "/com/example/onlineauction/administrator/administrator-view.fxml";
                            title = "Окно администратора";
                            break;
                        default:
                            return;
                    }
                    userId = user.getId();
                    WindowsManager.openWindow(fxmlPath, title);
                    Stage stageClose = (Stage) goToRegistrationButton.getScene().getWindow();
                    stageClose.close();
                }
            } else {
                // Неверные учетные данные
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Неверный логин или пароль.");
            }
        } catch (SQLException e) {
            // Обработка ошибки
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Ошибка при выполнении запроса к базе данных.");
            e.printStackTrace();
        }
    }

    @FXML
    void ExitAuthorization(ActionEvent event) throws IOException {
        System.exit(1);
    }

    @FXML
    void GoToRegistraton(ActionEvent event) throws IOException {
        Stage stageCLose = (Stage) goToRegistrationButton.getScene().getWindow();
        stageCLose.close();
        WindowsManager.openWindow("/com/example/onlineauction/AllUsers/registration-view.fxml", "Окно регистрации");
    }

    @FXML
    void initialize() {

    }

}
