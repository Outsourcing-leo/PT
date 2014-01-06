package org.tnt.pt.dmsentity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.tnt.pt.entity.IdEntity;


public class User extends IdEntity {

	private String userName;
	
	private String is_default;
	
	private String realName;
	
	private String password;
	
	private String role_name;
	
	private String code;
	
	private String outtypeName;
	
	public User() {
	}

	public User(Long id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOuttypeName() {
		return outtypeName;
	}

	public void setOuttypeName(String outtypeName) {
		this.outtypeName = outtypeName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}