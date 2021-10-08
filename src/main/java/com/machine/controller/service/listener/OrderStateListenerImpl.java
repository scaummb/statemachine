package com.machine.controller.service.listener;

import com.machine.controller.dto.Order;
import com.machine.controller.state.OrderStatus;
import com.machine.controller.state.OrderStatusChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

/**
 * 状态机的辅助能力Bean, 注入名称为 orderStateListener
 * 功能：处理条件成立/事件发生
 * @author moubin.mo
 * @date: 2021/8/25 08:47
 */
@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListenerImpl {

	private final static Logger logger = LoggerFactory.getLogger(OrderStateListenerImpl.class);

	@OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
	public boolean payTransition(Message<OrderStatusChangeEvent> message) {
		Order order = (Order) message.getHeaders().get("order");
		order.setStatus(OrderStatus.WAIT_DELIVER);
		logger.info("[listener - payTransition] header = {}" + message.getHeaders());
		return true;
	}

//	/**
//	 * 处理 OrderStatusChangeEvent.TEST 事件
//	 * @param message
//	 * @return
//	 */
//	@OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
//	public boolean payTransitionv2(Message<OrderStatusChangeEvent> message) {
//		if (OrderStatusChangeEvent.TEST.equals(message.getPayload())) {
//			Order order = (Order) message.getHeaders().get("order");
//			order.setStatus(OrderStatus.WAIT_DELIVER);
//			logger.info("支付v2 headers=" + message.getHeaders().toString());
//			return true;
//		}
//		return false;
//	}

//	@OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
//	public boolean deliverTransition(Message<OrderStatusChangeEvent> message) {
//		Order order = (Order) message.getHeaders().get("order");
//		order.setStatus(OrderStatus.WAIT_RECEIVE);
//		logger.info("发货 headers=" + message.getHeaders().toString());
//		return true;
//	}

//	@OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
//	public boolean receiveTransition(Message<OrderStatusChangeEvent> message){
//		// 从Message<OrderStatusChangeEvent>捞出条件/事件、状态对象；进行处理
//		Order order = (Order) message.getHeaders().get("order");
//		order.setStatus(OrderStatus.FINISH);
//		logger.info("收货 headers=" + message.getHeaders().toString());
//		return true;
//	}

//	@OnTransition
//	public boolean allTransitionMatch(){
//		logger.info("[listener - all actions match]...");
//		return true;
//	}

}
