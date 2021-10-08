package com.machine.controller.config;

import com.machine.controller.state.OrderStatus;
import com.machine.controller.state.OrderStatusChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;

import java.util.EnumSet;

/**
 * Order 状态机配置类
 * @author moubin.mo
 * @date: 2021/8/24 23:34
 */
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class StateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderStatusChangeEvent> {

	private final static Logger logger = LoggerFactory.getLogger(StateMachineConfig.class);

	@Override
	public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
		states.withStates()
				.initial(OrderStatus.WAIT_PAYMENT)
				.states(EnumSet.allOf(OrderStatus.class));
	}

	/**
	 * 配置状态转换事件关系
	 * @param transitions
	 * @throws Exception
	 */
	@Override
	public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
		transitions
				.withExternal()
				.source(OrderStatus.WAIT_PAYMENT)
				.target(OrderStatus.WAIT_DELIVER)
				.event(OrderStatusChangeEvent.PAYED)
				.action(addAction())
				.guard(addGuard())

		/** --------- 下面的事件可以拓展 --------- **/
//				.and()
//				.withExternal()
//				.source(OrderStatus.WAIT_DELIVER)
//				.target(OrderStatus.WAIT_RECEIVE)
//				.event(OrderStatusChangeEvent.DELIVERY)
//				.and()
//				.withExternal()
//				.source(OrderStatus.WAIT_RECEIVE)
//				.target(OrderStatus.FINISH)
//				.event(OrderStatusChangeEvent.RECEIVED)
//				.and()
//				.withExternal()
//				.source(OrderStatus.WAIT_PAYMENT)
//				.target(OrderStatus.WAIT_DELIVER)
//				.event(OrderStatusChangeEvent.TEST)
//				.and()
//				.withInternal()
//				.source(OrderStatus.WAIT_DELIVER)
//				.action(getTimerAction())
//				.timer(1000)
		;
	}

	@Bean
	public Action<OrderStatus, OrderStatusChangeEvent> addAction() {
		// 这里采用匿名类
		return new Action<OrderStatus, OrderStatusChangeEvent>() {
			@Override
			public void execute(StateContext<OrderStatus, OrderStatusChangeEvent> context) {
				logger.info("[action] do some actions in state machine..");
			}
		};
	}

	@Bean
	public Guard<OrderStatus, OrderStatusChangeEvent> addGuard() {
		// 这里采用匿名类
		return new Guard<OrderStatus, OrderStatusChangeEvent>() {
			@Override
			public boolean evaluate(StateContext<OrderStatus, OrderStatusChangeEvent> context) {
				logger.info("[guard] do some guards in state machine..");
				return true;
			}
		};
	}

	/** --------------- 下面是定时器action的定义，归属于内部事件 ------------- **/
//	@Bean
//	public TimerAction getTimerAction() {
//		return new TimerAction();
//	}
//
//	private class TimerAction implements Action<OrderStatus, OrderStatusChangeEvent> {
//		@Override
//		public void execute(StateContext<OrderStatus, OrderStatusChangeEvent> context) {
//			logger.info("[timer - action] do some period actions in state machine..");
//		}
//	}
}
