package com.bizideal.mn.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Roles implements Serializable {
	/**
	 * 自增id
	 */
	private Integer id;

	/**
	 * 角色名称
	 */
	private String name;

	private List<Resources> resources = new ArrayList<Resources>();

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public List<Resources> getResources() {
		return resources;
	}

	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", name=").append(name);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}