package ru.app.accountmanagement;

import org.springframework.boot.SpringApplication;

public class TestAccountmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.from(AccountManagementApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
