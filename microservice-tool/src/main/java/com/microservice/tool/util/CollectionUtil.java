package com.microservice.tool.util;

import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/8/23 12:27
 */
public class CollectionUtil extends org.springframework.util.CollectionUtils {

	/**
	 * 是否为空
	 *
	 * @param collections
	 * @return
	 */
	public static boolean isEmpty(@Nullable Collection<?>... collections) {
		boolean result = false;
		for (Collection<?> collection : collections) {
			if (isEmpty(collection)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		List<String> list1 = null;
		List<Integer> list2 = new ArrayList<>();
		list2.add(2);
		System.out.println(isEmpty(list1, list2));

	}
}
