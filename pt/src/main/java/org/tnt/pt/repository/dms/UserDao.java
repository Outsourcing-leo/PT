package org.tnt.pt.repository.dms;


import org.tnt.pt.dmsentity.User;
import org.tnt.pt.repository.BaseDao;
import org.tnt.pt.vo.LoginVO;



/**
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * 
 * @author calvin
 */
@MyBatisRepositoryDms
public interface UserDao extends BaseDao<User> {

	User findByLoginName(LoginVO loginVO);
}
