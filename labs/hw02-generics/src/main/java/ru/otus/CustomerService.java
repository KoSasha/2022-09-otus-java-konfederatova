package ru.otus;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> customers;

    public CustomerService() {
        this.customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
    public Map.Entry<Customer, String> getSmallest() {
        return Optional.ofNullable(customers.firstEntry()).map(this::createEntryFromExist).orElse(null);
    }

    //  возвращает наименьшую пару “ключ-значение”, которая больше ключа customer. Если такого ключа нет, возвращает null
    public Map.Entry<Customer, String> getNext(Customer customer) {
        return Optional.ofNullable(customers.higherEntry(customer)).map(this::createEntryFromExist).orElse(null);
    }

    public void add(Customer customer, String data) {
        this.customers.put(customer, data);
    }

    private Map.Entry<Customer, String> createEntryFromExist(Map.Entry<Customer, String> customerStringEntry) {
        return new AbstractMap.SimpleEntry<>(
                new Customer(customerStringEntry.getKey().getId(), customerStringEntry.getKey().getName(), customerStringEntry.getKey().getScores()), customerStringEntry.getValue());
    }
}
