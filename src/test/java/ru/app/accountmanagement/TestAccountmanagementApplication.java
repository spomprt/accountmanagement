package ru.app.accountmanagement;

import org.springframework.boot.SpringApplication;

public class TestAccountmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.from(AccountmanagementApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
