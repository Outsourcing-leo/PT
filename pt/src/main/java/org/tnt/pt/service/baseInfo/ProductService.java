package org.tnt.pt.service.baseInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.entity.Product;
import org.tnt.pt.repository.ProductDao;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class ProductService {

	private ProductDao productDao;

	public List<Product> getAllProduct() {
		return (List<Product>) productDao.findAll();
	}

	
	public Product getProduct(Long id) {
		return productDao.get(id);
	}
	
	
	@Autowired
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	
	

	
}
