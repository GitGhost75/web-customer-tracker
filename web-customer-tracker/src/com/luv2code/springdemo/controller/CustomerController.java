package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// inject our customer service
	@Autowired
	private CustomerService customerService;

	// new in Spring 4.3: GetMapping instead of @Requestmapping method=get...
	@GetMapping("/list")
	public String listCustomers(Model theModel) {

		// get customers from the service
		List<Customer> theCustomers = customerService.getCustomers();

		// add customers to model
		theModel.addAttribute("customers", theCustomers);

		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	private String showFormForAdd(Model theModel) {

		Customer theCustomer = new Customer();

		theModel.addAttribute("customer", theCustomer);

		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	private String saveCustomer(@ModelAttribute("customer") Customer customer) {

		// update customer using service
		customerService.saveCustomer(customer);

		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	private String showFormForUpdate(@RequestParam(name = "customerId") Integer customerId, Model theModel) {

		// get customer from database
		Customer theCustomer = customerService.getCustomer(customerId);

		// set customer as model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);

		return "customer-form";
	}

	@GetMapping("/delete")
	private String deleteCustomer(@RequestParam(name = "customerId") Integer customerId) {

		// delete the customer
		customerService.deleteCustomer(customerId);

		return "redirect:/customer/list";
	}
}
