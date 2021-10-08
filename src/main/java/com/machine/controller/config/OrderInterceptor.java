package com.machine.controller.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.persist.AbstractPersistingStateMachineInterceptor;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

public class OrderInterceptor<OrderStatus, OrderStatusChangeEvent, Order> extends AbstractPersistingStateMachineInterceptor {

	private final static Logger logger = LoggerFactory.getLogger(OrderListener.class);

	@Override
	public void preStateChange(State state, Message message, Transition transition, StateMachine stateMachine) {
		logger.info("[interceptor - preStateChange] do something in state machine");
		super.preStateChange(state, message, transition, stateMachine);
	}

	@Override
	public void write(StateMachineContext context, Object contextObj) throws Exception {
		logger.info("[interceptor & persist - write] do some persist in state machine, object = {}", contextObj);
	}

	@Override
	public StateMachineContext read(Object contextObj) throws Exception {
		logger.info("[interceptor & - read] do some persist in state machine");
		return null;
	}
}