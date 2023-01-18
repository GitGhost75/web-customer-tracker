package com.luv2code.springdemo.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
import com.luv2code.springdemo.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// inject our customer service
	@Autowired
	private CustomerService customerService;

	public CustomerService getCustomerService() {
		// add this getter as service was null after adding aop
		return customerService;
	}

	// new in Spring 4.3: GetMapping instead of @Requestmapping method=get...
	@GetMapping("/list")
	public String listCustomers(@RequestParam(required = false, name = "sort") String sort, Model theModel) {

		// get customers from the service
		List<Customer> theCustomers = getCustomerService().getCustomers();

		// create comparator
		Comparator<Customer> theComparator = Comparator.comparing(Customer::getLastName);

		if (null != sort) {

			int sortColumn = Integer.parseInt(sort);

			if (SortUtils.FIRST_NAME == sortColumn) {
				theComparator = Comparator.comparing(Customer::getFirstName);
			} else if (SortUtils.EMAIL == sortColumn) {
				theComparator = Comparator.comparing(Customer::getEmail);
			} else {
				theComparator = Comparator.comparing(Customer::getLastName);
			}
		}

		// sort customers
		theCustomers = theCustomers.stream().sorted(theComparator).collect(Collectors.toList());

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
		getCustomerService().saveCustomer(customer);

		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	private String showFormForUpdate(@RequestParam(name = "customerId") Integer customerId, Model theModel) {

		// get customer from database
		Customer theCustomer = getCustomerService().getCustomer(customerId);

		// set customer as model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);

		return "customer-form";
	}

	@GetMapping("/delete")
	private String deleteCustomer(@RequestParam(name = "customerId") Integer customerId) {

		// delete the customer
		getCustomerService().deleteCustomer(customerId);

		return "redirect:/customer/list";
	}

	@GetMapping("/search")
	private String searchCustomer(@RequestParam(name = "theSearchName") String searchName, Model theModel) {

		// search customers containing searchName
		// get customers from the service
		List<Customer> theCustomers = getCustomerService().searchCustomers(searchName);

		// add customers to model
		theModel.addAttribute("customers", theCustomers);

		return "list-customers";
	}
}
