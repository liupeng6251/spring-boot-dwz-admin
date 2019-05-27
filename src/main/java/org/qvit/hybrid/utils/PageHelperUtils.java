package org.qvit.hybrid.utils;

import com.github.pagehelper.PageHelper;

public class PageHelperUtils {

	public static void startPage(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);

	}
}
