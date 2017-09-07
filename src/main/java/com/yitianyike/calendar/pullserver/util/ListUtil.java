package com.yitianyike.calendar.pullserver.util;

import java.util.ArrayList;
import java.util.List;

import com.yitianyike.calendar.pullserver.model.responseCardData.News;

public class ListUtil {
	/**
	 * 将一个list均分成n个list,主要通过偏移量来实现的
	 * 
	 * @param source
	 * @return
	 */
	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
		List<List<T>> result = new ArrayList<List<T>>();
		int remaider = source.size() % n; // (先计算出余数)
		int number = source.size() / n; // 然后是商
		int offset = 0;// 偏移量
		for (int i = 0; i < n; i++) {
			List<T> value = null;
			if (remaider > 0) {
				value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
				remaider--;
				offset++;
			} else {
				value = source.subList(i * number + offset, (i + 1) * number + offset);
			}
			result.add(value);
		}
		return result;
	}

	public static <T> List<List<T>> groupListByNum(List<T> source, int n) {
		List<List<T>> result = new ArrayList<List<T>>();
		int in = 0;
		for (int i = 0; i < source.size() / n; i++) {
			List<T> subList = source.subList(in, in += n);
			result.add(subList);
		}
		int x = source.size() % n;
		if (x != 0) {
			List<T> subList = source.subList(source.size() - x, source.size());
			result.add(subList);
		}
		return result;

	}

	public static void main(String[] args) {
		List<Integer> integers = new ArrayList<Integer>();
//		integers.add(1);
//		integers.add(2);
//		integers.add(3);
//		integers.add(4);
//		integers.add(5);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(6);
//		integers.add(7);
//		integers.add(8);
//		integers.add(9);
//		integers.add(1);
//		integers.add(2);
//		integers.add(3);
//		integers.add(3);
//		integers.add(3);
//		integers.add(3);
//		integers.add(3);
	//	integers.add(4);
		List<List<Integer>> groupListByNum = groupListByNum(integers,9);
		for (List<Integer> list : groupListByNum) {
			System.out.println(list);
		}

	}
}
