package org.programs.kdt.RestController;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Customer.CustomerCreateRequest;
import org.programs.kdt.Customer.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/customer")
public class ARestCustomerController {

    private final CustomerService customerService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Customer>> findCustomerList() {
        List<Customer> customerList = customerService.findAll();
        return ResponseEntity.ok(customerList);
    }

    @GetMapping(value = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> findCustomer(@PathVariable UUID customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        return customerOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createVoucher(@RequestBody CustomerCreateRequest customerCreateRequest) {
        Customer customer = customerCreateRequest.toCustomer();
        customerService.save(customer);
        return ResponseEntity.created(URI.create("/v1/customer/" + customer.getCustomerId())).build();
    }

    @DeleteMapping(value = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCustomer(@PathVariable UUID customerId) {
        customerService.delete(customerId);
    return ResponseEntity.noContent().build();
    }


}
