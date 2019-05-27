package org.qvit.hybrid.utils;

import java.util.List;

import org.springframework.beans.FatalBeanException;

public class BeanUtils {

	public static void copyProperties(Object source, Object target, String... ignoreProperties) {
		if (source == null) {
			return;
		}
		org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
	}

	public static <T, K> void copyListProperties(List<T> source, List<K> target, Class<K> targetClazz, String... ignoreProperties) {
		for (T t : source) {
			try {
				K k = targetClazz.newInstance();
				copyProperties(t, k, ignoreProperties);
				target.add(k);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				throw new FatalBeanException(e.getMessage(), e);
			}
		}
	}

}
