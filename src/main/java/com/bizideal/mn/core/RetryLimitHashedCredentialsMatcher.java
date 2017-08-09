package com.bizideal.mn.core;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * @author 作者 liulq:
 * @data 创建时间：2017年3月8日 下午5:14:03
 * @version 1.0 尝试登陆5次后锁定账户
 */
public class RetryLimitHashedCredentialsMatcher extends
		HashedCredentialsMatcher {

	private Cache<String, AtomicInteger> passwordRetryCache;

	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		System.out.println("==========构造了=============");
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		// 集群中可能会导致出现验证多过5次的现象，因为AtomicInteger只能保证单节点并发

		String username = (String) token.getPrincipal();
		// retry count + 1
		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (null == retryCount) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		if (retryCount.incrementAndGet() > 5) {
			// if retry count > 5 throw
			throw new ExcessiveAttemptsException("username: " + username
					+ " tried to login more than 5 times in period");
		}
		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			// clear retry data
			passwordRetryCache.remove(username);
		}
		return matches;
	}
}
