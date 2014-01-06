package org.tnt.pt.service.dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tnt.pt.dmsentity.FSI;
import org.tnt.pt.repository.dms.FsiDao;
import org.tnt.pt.service.ServiceException;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class FsiService {

	private FsiDao fsiDao;

	/**
	 * 申请PT的客户在t_chr_fsf中无法找到记录的时候，就取t_chr_fsi表中当前时间在from_date和to_date之间的创建时间最新的那条记录得燃油附加费FS_RATE 
		t_chr_fsf:account_no字段能够匹配成功的情况下需要用到此表，有2种情况： 
		is_FSI是1，燃油附加费取FS_RATE 
		IS_FSI_DIS是1，燃油附加费取FSI_DIS_RATE*（t_chr_fsi表中的当前生效的燃油附加费FS_RATE）
	 * @param accountNo
	 * @return
	 */
	public Double getFsi(String accountNo) {
		if(accountNo==null) throw new ServiceException("获取燃油附加费时参数错误");
		FSI fsi  = new FSI();
		Double rate = 0.0;
		Integer isFromFsf = fsiDao.isFromFsf(accountNo);
		if(isFromFsf > 0){
			fsi = fsiDao.getFromFSF(accountNo);//"000009302"
			if("1".equals(fsi.getIs_fsi())){
				rate = fsi.getFs_rate()==null?0.0:fsi.getFs_rate();
			}else{
				rate = fsi.getFsi_dis_rate()==null?0.0:fsi.getFsi_dis_rate();
			}
		}else{
			rate = fsiDao.getRateFromFSI()==null?0.0:fsiDao.getRateFromFSI();
		}
		return rate;
	}

	public FsiDao getFsiDao() {
		return fsiDao;
	}

	@Autowired
	public void setFsiDao(FsiDao fsiDao) {
		this.fsiDao = fsiDao;
	}
	
}
