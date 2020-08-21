package com.cxd.tool.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/6/27 15:13
 */
public class ArrayUtils {

	/**
	 * 根据数组获取集合
	 *
	 * @param arrys
	 * @return
	 */
	public static List<Integer> getIntArray(Integer... arrys) {
		List<Integer> list = new ArrayList<>();
		Collections.addAll(list, arrys);
		return list;
	}

	/**
	 * 根据数组获取集合
	 *
	 * @param arrys
	 * @return
	 */
	public static List<Byte> getByteArray(Integer... arrys) {
		List<Byte> list = new ArrayList<>();
		for (Integer arry : arrys) {
			list.add(Byte.valueOf(String.valueOf(arry)));
		}
		return list;
	}

	public static List<String> getArray(String... arrys) {
		List<String> list = new ArrayList<>();
		Collections.addAll(list, arrys);
		return list;
	}

	/**
	 * 截取数组
	 * @param ids
	 * @param cut
	 * @return
	 */
	public static List<Integer> arrayCat(List<Integer> ids, int cut) {
		return ids.subList(0, ids.size() / cut);
	}

	public static void main(String[] args) {
		List<Integer> ids = getIntArray(1, 2, 3, 4, 5, 6, 7, 8, 9);
		System.out.println(arrayCat(ids, 2));
	}


}
