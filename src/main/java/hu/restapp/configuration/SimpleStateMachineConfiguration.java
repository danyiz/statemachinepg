package hu.restapp.configuration;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@EnableStateMachine
@Slf4j
public class SimpleStateMachineConfiguration
        extends StateMachineConfigurerAdapter<String, String> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<String, String> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListener() {
                    @Override
                    public void stateChanged(State state, State state1) {
                    log.info("Transitioned {}", state.getId());
                    }

                    @Override
                    public void stateEntered(State state) {

                    }

                    @Override
                    public void stateExited(State state) {

                    }

                    @Override
                    public void eventNotAccepted(Message message) {

                    }

                    @Override
                    public void transition(Transition transition) {

                    }

                    @Override
                    public void transitionStarted(Transition transition) {

                    }

                    @Override
                    public void transitionEnded(Transition transition) {

                    }

                    @Override
                    public void stateMachineStarted(StateMachine stateMachine) {

                    }

                    @Override
                    public void stateMachineStopped(StateMachine stateMachine) {

                    }

                    @Override
                    public void stateMachineError(StateMachine stateMachine, Exception e) {

                    }

                    @Override
                    public void extendedStateChanged(Object o, Object o1) {

                    }

                    @Override
                    public void stateContext(StateContext stateContext) {

                    }
                });
    }

    @Override
    public void configure(StateMachineStateConfigurer<String, String> states) throws Exception {
        states
                .withStates()
                .initial("SI")
                .end("SF")
                .states(new HashSet<>(Arrays.asList("S1", "S2")))
                .stateEntry("S3", entryAction())
                .stateExit("S3", exitAction())
                .state("S4", executeAction(), errorAction())
                .stateDo("S5", executeAction());

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<String, String> transitions) throws Exception {
        transitions
                .withExternal()
                .source("SI")
                .target("S1")
                .event("E1")
                .action(initAction())
                .and()
                .withExternal()
                .source("S1")
                .target("S2")
                .event("E2")
                .and()
                .withExternal()
                .source("S2")
                .target("S3")
                .event("E3")
                .and()
                .withExternal()
                .source("S3")
                .target("S4")
                .event("E4")
                .and()
                .withExternal()
                .source("S4")
                .target("S5")
                .event("E5")
                .and()
                .withExternal()
                .source("S5")
                .target("SF")
                .event("end")
                .guard(simpleGuard());
    }

    @Bean
    public Guard<String, String> simpleGuard() {
        return ctx -> {
            int approvalCount = (int) ctx
                    .getExtendedState()
                    .getVariables()
                    .getOrDefault("approvalCount", 0);
            return approvalCount > 0;
        };
    }

    @Bean
    public Action<String, String> entryAction() {
        return ctx -> log.info("Entry " + ctx
                .getTarget()
                .getId());
    }

    @Bean
    public Action<String, String> doAction() {
        return ctx -> log.info("Do " + ctx
                .getTarget()
                .getId());
    }

    @Bean
    public Action<String, String> executeAction() {
        return ctx -> {
            log.info("Execute " + ctx
                    .getTarget()
                    .getId());
            int approvals = (int) ctx
                    .getExtendedState()
                    .getVariables()
                    .getOrDefault("approvalCount", 0);
            approvals++;
            ctx
                    .getExtendedState()
                    .getVariables()
                    .put("approvalCount", approvals);
        };
    }

    @Bean
    public Action<String, String> exitAction() {
        return ctx -> log.info("Exit " + ctx
                .getSource()
                .getId() + " -> " + ctx
                .getTarget()
                .getId());
    }

    @Bean
    public Action<String, String> errorAction() {
        return ctx -> log.info("Error " + ctx
                .getSource()
                .getId() + ctx.getException());
    }

    @Bean
    public Action<String, String> initAction() {
        return ctx -> log.info(ctx
                .getTarget()
                .getId());
    }
}

