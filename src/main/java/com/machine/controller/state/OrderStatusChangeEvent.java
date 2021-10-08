package com.machine.controller.state;

/**
 * @author moubin.mo
 * @date: 2021/8/24 23:33
 */

/**
 * 订单状态改变事件
 */
public enum OrderStatusChangeEvent {
	// 支付，发货，确认收货
	PAYED, DELIVERY, RECEIVED;
}
