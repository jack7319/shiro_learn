package com.bizideal.mn.service;

import java.util.List;

import com.bizideal.mn.entity.Resources;
import com.bizideal.mn.entity.Roles;

/**
 * @author liulq:
 * @data 2017年3月6日 下午10:17:03
 * @version 1.0
 */
public interface IResourceService {

	public List<Resources> selectByRoles(List<Roles> roles);
}
