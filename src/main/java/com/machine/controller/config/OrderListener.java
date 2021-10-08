package com.machine.controller.config;

import com.machine.controller.state.OrderStatus;
import com.machine.controller.state.OrderStatusChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

/**
 * 状态机事件监听器 OrderListener
 */
public class OrderListener
        extends StateMachineListenerAdapter<OrderStatus, OrderStatusChangeEvent> {

    private final static Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @Override
    public void stateChanged(State<OrderStatus, OrderStatusChangeEvent> from, State<OrderStatus, OrderStatusChangeEvent> to) {
        logger.info("[listener - stateChanged] from = {}", from);
        logger.info("[listener - stateChanged] to = {}", to);
    }

    @Override
    public void transition(Transition<OrderStatus, OrderStatusChangeEvent> transition) {
        logger.info("[listener - transition] trigger = {}, source = {}, target = {}, guard = {}, actions = {}",
                transition.getTrigger(), transition.getSource(), transition.getTarget(),
                transition.getGuard(), transition.getActions());
    }
}