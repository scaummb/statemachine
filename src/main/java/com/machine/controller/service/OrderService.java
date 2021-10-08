package com.machine.controller.service;

/**
 * @author moubin.mo
 * @date: 2021/8/26 21:26
 */

public interface OrderService {
	String pay(int id);

	//--------- 下面方法可以拓展 ---------
//	Order creat();
//	Order deliver(int id);
//	Order receive(int id);
//	Map<Integer, Order> getOrders();
}
