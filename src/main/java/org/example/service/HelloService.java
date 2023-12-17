package org.example.service;

public class HelloService {
    private final String name;
    private final String address;

    public HelloService(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String sayHello() {
        return "Hello, " + name + " from " + address;
    }
}
