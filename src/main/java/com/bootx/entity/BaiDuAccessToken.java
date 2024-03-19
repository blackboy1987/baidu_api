
package com.bootx.entity;

import com.bootx.util.DateUtils;
import jakarta.persistence.Entity;

import java.util.Date;

/**
 * @author black
 */
@Entity
public class BaiDuAccessToken extends BaseEntity<Long> {

	private String accessToken;

	private String refreshToken;

	private Long expiresIn;

	private String sessionSecret;

	private String sessionKey;

	private String scope;

	private Date expiresDate;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getSessionSecret() {
		return sessionSecret;
	}

	public void setSessionSecret(String sessionSecret) {
		this.sessionSecret = sessionSecret;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Date getExpiresDate() {
		return expiresDate;
	}

	public void setExpiresDate(Date expiresDate) {
		this.expiresDate = DateUtils.getNextSecond(expiresIn);
	}
}