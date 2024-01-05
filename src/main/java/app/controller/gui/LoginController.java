package app.controller.gui;

import app.model.Adopter;
import app.service.AdopterService;
import app.single_point_access.GUIFrameSinglePointAccess;
import app.single_point_access.ServiceSinglePointAccess;
import app.view.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private LoginView loginView;

    private AdopterService adopterService = ServiceSinglePointAccess.getAdopterService();

    public void startLogic() {
        loginView = new LoginView();
        GUIFrameSinglePointAccess.changePanel(loginView.getLoginPanel(), "Login");

        loginView.getLogInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = loginView.getTextFieldName().getText();
                String password = new String(loginView.getPasswordField().getPassword());

                Adopter adopter = adopterService.login(name, password);
                if (adopter != null) {
                    UserDetailsController userDetailsController = new UserDetailsController();
                    userDetailsController.startLogic(adopter);
                } else {
                    GUIFrameSinglePointAccess.showDialogMessage("Invalid username or password");
                }
            }
        });
    }
}
