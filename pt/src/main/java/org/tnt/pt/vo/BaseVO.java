package org.tnt.pt.vo;

import org.tnt.pt.util.Navigate;

public class BaseVO {

	/**
	 * 分页导航
	 */
	private Navigate navigate = new Navigate();
	
	public Navigate getNavigate() {
		return navigate;
	}

	public void setNavigate(Navigate navigate) {
		this.navigate = navigate;
	}
}
