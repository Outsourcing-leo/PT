package org.tnt.pt.service.ptProcess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Customer;
import org.tnt.pt.repository.CustomerDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class CustomerService {

	private CustomerDao customerDao;

	public List<Customer> getAllCustomer() {
		return (List<Customer>) customerDao.findAll();
	}

	public void update(Customer cus){
		customerDao.update(cus);
	}
	
	public Customer getCustomer(Long id) {
		return customerDao.get(id);
	}
	
	public Integer insert(Customer cus){
		return customerDao.insert(cus);
	}

	@Autowired
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}


	
	
	
	
}
