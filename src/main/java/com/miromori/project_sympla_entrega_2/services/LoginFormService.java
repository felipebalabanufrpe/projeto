package com.miromori.project_sympla_entrega_2.services;

import com.miromori.project_sympla_entrega_2.ProjectSymplaEntrega2Application;
import com.miromori.project_sympla_entrega_2.controllers.UserController;

public class LoginFormService {
    private UserController userController = ProjectSymplaEntrega2Application.getSpringContext().getBean(UserController.class);

    public void signUp(){

    }
}
