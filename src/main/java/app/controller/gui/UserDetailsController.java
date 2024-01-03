package app.controller.gui;

import app.model.Adopter;
import app.service.UserService;
import app.single_point_access.GUIFrameSinglePointAccess;
import app.single_point_access.ServiceSinglePointAccess;
import app.view.UserDetailsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDetailsController {

    private UserDetailsView userDetailsView;

    private UserService userService = ServiceSinglePointAccess.getUserService();


    public void startLogic(Adopter adopter) {
        userDetailsView = new UserDetailsView();
        GUIFrameSinglePointAccess.changePanel(userDetailsView.getMainPanel(), "Welcome " + adopter.getName());

        userDetailsView.getNameValue().setText(adopter.getName());
        //userDetailsView.getSalaryValue().setText(adopter.getSalary().toString());
        userDetailsView.getStreetValue().setText(adopter.getAddress().getStreet());
        userDetailsView.getCityValue().setText(adopter.getAddress().getCity());

        userDetailsView.getLogOutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginController loginController = new LoginController();
                loginController.startLogic();
            }
        });
    }
}
