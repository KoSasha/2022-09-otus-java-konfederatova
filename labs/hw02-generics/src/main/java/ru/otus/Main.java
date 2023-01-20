package ru.otus;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(2, "Petr", 11);
        Customer customer3 = new Customer(3, "Pavel", 888);

        CustomerService customerService = new CustomerService();
        customerService.add(customer1, "Data1");
        customerService.add(customer2, "Data2");
        customerService.add(customer3, "Data3");

        Map.Entry<Customer, String> middleScore = customerService.getNext(new Customer(10, "Key", 20));
    }
}
