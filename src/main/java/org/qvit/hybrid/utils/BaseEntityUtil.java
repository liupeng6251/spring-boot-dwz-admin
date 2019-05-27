package org.qvit.hybrid.utils;

import java.util.Date;

import org.qvit.hybrid.sys.entity.BaseEntity;

public class BaseEntityUtil {

	public static final String[] BASEENTITY_PROPERTIES = new String[] { "id", "version", "createDate", "updateDate" };

	public static void bindNewBaseEntity(BaseEntity baseEntity) {
		baseEntity.setCreateDate(new Date());
		baseEntity.setUpdateDate(new Date());
		baseEntity.setVersion(1L);
		baseEntity.setId(null);
	}

	public static void bindUpdateBaseEntity(BaseEntity baseEntity) {
		baseEntity.setUpdateDate(new Date());
		baseEntity.setVersion(baseEntity.getVersion() + 1);
	}

}
