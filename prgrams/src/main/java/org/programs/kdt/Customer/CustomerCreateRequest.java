package org.programs.kdt.Customer;


import java.util.UUID;

public record CustomerCreateRequest(String name, String email) {

    public Customer toCustomer() {
        return new Customer(
                UUID.randomUUID(),
                name,
                email
        );
    }
}
