package ru.otus;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

public class CustomerService {

    private NavigableMap<Customer, String> customers;

    public CustomerService() {
        this.customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        return Map.Entry.copyOf(customers.firstEntry());
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
//        return null; // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return Optional.ofNullable(customers.higherEntry(customer)).map(Map.Entry::copyOf).orElse(null);
        //  возвращает наименьшую пару “ключ-значение”, которая больше ключа customer. Если такого ключа нет, возвращает null
//        return null;
    }

    public void add(Customer customer, String data) {
        this.customers.put(customer, data);
    }
}
