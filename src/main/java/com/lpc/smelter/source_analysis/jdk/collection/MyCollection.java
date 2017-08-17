package com.lpc.smelter.source_analysis.jdk.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author longpengchao
 * @email longpengchao@58ganji.com
 * @create 2017-08-08 11:13
 */
public class MyCollection {

	@Test
	public void test1(){
		Set<String> set = new HashSet<String>();
		System.out.println(set.contains(null));
	}

	@Test
	public void test2(){
		List<String> list = new ArrayList<String>();
		System.out.println(list.get(-1));
	}
}
