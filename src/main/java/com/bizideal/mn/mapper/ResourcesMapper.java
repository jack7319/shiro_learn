package com.bizideal.mn.mapper;

import java.util.List;

import com.bizideal.mn.entity.Resources;
import com.bizideal.mn.entity.Roles;

import tk.mybatis.mapper.common.Mapper;

public interface ResourcesMapper extends Mapper<Resources> {

	public List<Resources> selectByRoles(List<Roles> roles);
}