package com.bizideal.mn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizideal.mn.entity.Resources;
import com.bizideal.mn.entity.Roles;
import com.bizideal.mn.mapper.ResourcesMapper;
import com.bizideal.mn.service.IResourceService;

/**
 * @author liulq:
 * @data 2017年3月6日 下午10:17:35
 * @version 1.0
 */
@Service
public class ResourceServiceImpl implements IResourceService {

	@Autowired
	private ResourcesMapper resourcesMapper;

	@Override
	public List<Resources> selectByRoles(List<Roles> roles) {
		return resourcesMapper.selectByRoles(roles);
	}

}
