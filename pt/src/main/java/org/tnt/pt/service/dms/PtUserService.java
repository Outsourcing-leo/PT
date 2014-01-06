package org.tnt.pt.service.dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.dmsentity.User;
import org.tnt.pt.repository.dms.UserDao;
import org.tnt.pt.vo.LoginVO;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class PtUserService {

	private UserDao userDao;

	public User getUser(LoginVO loginVO) {
		return userDao.findByLoginName(loginVO);
	}
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
