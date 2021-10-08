package com.machine.controller.service;

import com.machine.controller.config.OrderInterceptor;
import com.machine.controller.config.OrderListener;
import com.machine.controller.dto.Order;
import com.machine.controller.state.OrderStatus;
import com.machine.controller.state.OrderStatusChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author moubin.mo
 * @date: 2021/8/25 08:55
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

	private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

	private int id = 1;
	private Map<Integer, Order> orders = new HashMap();

	@Override
	public String pay(int id) {
		Order order = buildOrder();
		Message message = MessageBuilder
								.withPayload(OrderStatusChangeEvent.PAYED)
								.setHeader("order", order).build();
		return sendEvent(message).toString();
	}

	private Order buildOrder() {
		Order order = new Order();
		order.setStatus(OrderStatus.WAIT_PAYMENT);
		order.setId(id++);
		return order;
	}

	/**
	 * 发送订单状态转换事件
	 *
	 * @param message
	 * @return
	 */
	private synchronized Boolean sendEvent(Message<OrderStatusChangeEvent> message) {
		boolean result = false;
		try {
			orderStateMachine.start();
			result = orderStateMachine.sendEvent(message);
			if (!result) {
				logger.error("[sendEvent] error, result = {}", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			orderStateMachine.stop();
		}
		return result;
	}

	// ------------ 以下方法可以拓展 --------------
//	@Override
//	public Order creat() {
//		Order order = new Order();
//		order.setStatus(OrderStatus.WAIT_PAYMENT);
//		order.setId(id++);
//		orders.put(order.getId(), order);
//		return order;
//	}
//
//	@Override
//	public Order deliver(int id) {
//		Order order = orders.get(id);
//		if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.DELIVERY).setHeader("order", order).build())) {
//
//		}
//		return orders.get(id);
//	}
//
//	@Override
//	public Order receive(int id) {
//		Order order = orders.get(id);
//		if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.RECEIVED).setHeader("order", order).build())) {
//		}
//		return orders.get(id);
//	}
//
//	@Override
//	public Map<Integer, Order> getOrders() {
//		return orders;
//	}


	/** --------------- 下面是 监听器EventListener & 拦截器 & 持久化器Persister 的定义 ------------- **/
	/** --------------- 监听器定义 ---------**/
	@Bean
	public OrderListener registerEventListener() {
		// 这里是Spring的添加状态机的机制，https://docs.spring.io/spring-statemachine/docs/2.4.0/reference/#sm-listeners
		OrderListener listener = new OrderListener();
		orderStateMachine.addStateListener(listener);
		return listener;
	}

	/** ----------- 拦截器定义 & 持久化器定义（为什么持久化器可以定义在拦截器里面？我们可以从spring的参考手册里面得到回答。）**/
	// A:You can isntead use a state machine interceptor to try to save the serialized state into external storage during the state change within a state machine. If this interceptor callback fails, you can halt the state change attempt and, instead of ending in an inconsistent state, you can then handle this error manually. See Using StateMachineInterceptor for how to use interceptors.
	@Bean
	public StateMachineInterceptor<OrderStatus, OrderStatusChangeEvent> registerInterceptor() {
		StateMachineInterceptor<OrderStatus, OrderStatusChangeEvent> interceptor =
				new OrderInterceptor<OrderStatus, OrderStatusChangeEvent, Order>();
		orderStateMachine.getStateMachineAccessor()
				.withRegion().addStateMachineInterceptor(interceptor);
		return interceptor;
	}


}
