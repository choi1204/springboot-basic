package org.programs.kdt.Controller;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Customer.CustomerCreateRequest;
import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Customer.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String findCustomerList(Model model) {
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customerList", customerList);

        return "customerList";
    }

    @GetMapping("/{customerId}")
    public String findCustomer(@PathVariable UUID customerId, Model model) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        customerOptional.ifPresent(customer -> model.addAttribute("customer", customer));

        return "customer";
    }

    @GetMapping("/create")
    public String createCustomerView(Model model) {
        return "createCustomer";
    }

    @PostMapping("/create")
    public String createVoucher(CustomerCreateRequest customerCreateRequest) {
        Customer customer = customerCreateRequest.toCustomer();
        customerService.save(customer);
        return "redirect:/customer";
    }

    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable UUID customerId) {
        customerService.delete(customerId);
        return "redirect:/customer";
    }

}
