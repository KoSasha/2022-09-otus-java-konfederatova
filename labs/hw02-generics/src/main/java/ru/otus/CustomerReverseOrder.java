package ru.otus;

import java.util.Deque;
import java.util.LinkedList;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    private final Deque<Customer> customers;

    public CustomerReverseOrder() {
        this.customers = new LinkedList<>();
    }

    public void add(Customer customer) {
        this.customers.addLast(customer);
    }

    public Customer take() {
        return customers.pollLast();
    }

}
