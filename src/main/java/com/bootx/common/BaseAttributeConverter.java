/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: M8u9BpnQ/ug3bAQnjBptV8+z2z7FLFKc
 */
package com.bootx.common;

import com.bootx.util.JsonUtils;
import com.fasterxml.jackson.databind.JavaType;
import jakarta.persistence.AttributeConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseAttributeConverter<T> implements AttributeConverter<Object, String> {

	/**
	 * 类型
	 */
	private JavaType javaType;

	/**
	 * 构造方法
	 */
	public BaseAttributeConverter() {
		ResolvableType resolvableType = ResolvableType.forClass(getClass());
		Type type = resolvableType.as(BaseAttributeConverter.class).getGeneric().getType();
		javaType = JsonUtils.constructType(type);
	}

	/**
	 * 转换属性为数据库值
	 * 
	 * @param attribute
	 *            属性
	 * @return 数据库值
	 */
	@Override
	public String convertToDatabaseColumn(Object attribute) {
		if (attribute == null) {
			return null;
		}

		return JsonUtils.toJson(attribute);
	}

	/**
	 * 转换数据库值为属性
	 * 
	 * @param dbData
	 *            数据库值
	 * @return 属性
	 */
	@Override
	public Object convertToEntityAttribute(String dbData) {
		if (StringUtils.isEmpty(dbData)) {
			if (List.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.emptyList();
			} else if (Set.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.emptySet();
			} else if (Map.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.emptyMap();
			} else {
				return null;
			}
		}

		return JsonUtils.toObject(dbData, javaType);
	}

}