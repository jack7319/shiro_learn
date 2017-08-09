package com.bizideal.mn.entity;

import java.io.Serializable;

public class RoleResourcesKey implements Serializable {
    private Integer roleid;

    private Integer resourcesid;

    private static final long serialVersionUID = 1L;

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getResourcesid() {
        return resourcesid;
    }

    public void setResourcesid(Integer resourcesid) {
        this.resourcesid = resourcesid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleid=").append(roleid);
        sb.append(", resourcesid=").append(resourcesid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}