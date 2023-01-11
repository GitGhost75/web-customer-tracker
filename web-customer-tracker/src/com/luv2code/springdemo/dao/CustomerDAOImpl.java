package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {

		// get current session
		Session currentSession = sessionFactory.getCurrentSession();

		// create query ... sort by last name
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);

		// get result list
		List<Customer> customers = theQuery.getResultList();

		// return result list
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {

		// get current session
		Session currentSession = sessionFactory.getCurrentSession();

		// save/update the customer ... finally ;-)
		currentSession.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(Integer customerId) {

		// get current session
		Session currentSession = sessionFactory.getCurrentSession();

		// get customer by id
		Customer theCustomer = currentSession.get(Customer.class, customerId);

		return theCustomer;
	}

}
