package com.miromori.project_sympla_entrega_2;

import com.miromori.project_sympla_entrega_2.view.LoginForm;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProjectSymplaEntrega2Application {

	private static ConfigurableApplicationContext springContext;

	public static void main(String[] args) {
		springContext = SpringApplication.run(ProjectSymplaEntrega2Application.class, args);
		Application.launch(LoginForm.class, args);
	}

	public static ConfigurableApplicationContext getSpringContext() {
		return springContext;
	}
}
