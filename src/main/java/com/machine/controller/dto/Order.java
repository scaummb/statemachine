package com.machine.controller.dto;

import com.machine.controller.state.OrderStatus;

/**
 * @author moubin.mo
 * @date: 2021/8/25 08:38
 */
public class Order {
	private Integer id;
	private OrderStatus status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
