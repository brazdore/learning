package org.example.patterns.behavioral;

import org.javatuples.Pair;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.transition.Transition;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class State {
    public static void main(String[] args) throws Exception {
        LightSwitch lightSwitch = new LightSwitch();
        lightSwitch.off();
        lightSwitch.on();
        lightSwitch.on();
        lightSwitch.off();

        // PhoneStateMachine.main(new String[]{""});
        // SpringStateMachine.main(new String[]{""});

        CombinationLock combinationLock = new CombinationLock(new int[]{1, 2, 3, 4});
        combinationLock.enterDigit(1);
        combinationLock.enterDigit(2);
        combinationLock.enterDigit(3);
        combinationLock.enterDigit(4);
        System.out.println(combinationLock.status);
    }

    // Classic Implementation
    static class StateImpl {
        void on(LightSwitch lightSwitch) {
            System.out.println("Light is already ON");

        }

        void off(LightSwitch lightSwitch) {
            System.out.println("Light is already OFF");
        }
    }

    static class OnState extends StateImpl {
        public OnState() {
            System.out.println("Light turned ON");
        }

        @Override
        void off(LightSwitch lightSwitch) {
            System.out.println("Switching light OFF");
            lightSwitch.setState(new OffState());
        }
    }

    static class OffState extends StateImpl {
        public OffState() {
            System.out.println("Light turned OFF");
        }

        @Override
        void on(LightSwitch lightSwitch) {
            System.out.println("Switching light ON");
            lightSwitch.setState(new OnState());
        }
    }

    static class LightSwitch {
        private StateImpl state; // OnState or OffState

        public LightSwitch() {
            this.state = new OffState();
        }

        void on() {
            state.on(this);
        }

        void off() {
            state.off(this);
        }

        public void setState(StateImpl state) {
            this.state = state;
        }
    }

    // Handmade State Machine

    enum PhoneState {
        OFF_HOOK,
        ON_HOOK,
        CONNECTING,
        CONNECTED,
        ON_HOLD
    }

    enum PhoneTrigger {
        CALL_DIALED,
        HUNG_UP,
        CALL_CONNECTED,
        PLACED_ON_HOLD,
        TAKEN_OFF_HOLD,
        LEFT_MESSAGE,
        STOP_USING_PHONE
    }

    static class PhoneStateMachine {
        private static final Map<PhoneState, List<Pair<PhoneTrigger, PhoneState>>> rules = new HashMap<>();

        static {
            rules.put(PhoneState.OFF_HOOK, List.of(
                    new Pair<>(PhoneTrigger.CALL_DIALED, PhoneState.CONNECTING),
                    new Pair<>(PhoneTrigger.STOP_USING_PHONE, PhoneState.ON_HOOK)
            ));

            rules.put(PhoneState.CONNECTING, List.of(
                    new Pair<>(PhoneTrigger.HUNG_UP, PhoneState.OFF_HOOK),
                    new Pair<>(PhoneTrigger.CALL_CONNECTED, PhoneState.CONNECTED)
            ));

            rules.put(PhoneState.CONNECTED, List.of(
                    new Pair<>(PhoneTrigger.LEFT_MESSAGE, PhoneState.OFF_HOOK),
                    new Pair<>(PhoneTrigger.HUNG_UP, PhoneState.OFF_HOOK),
                    new Pair<>(PhoneTrigger.PLACED_ON_HOLD, PhoneState.ON_HOLD)
            ));

            rules.put(PhoneState.ON_HOLD, List.of(
                    new Pair<>(PhoneTrigger.TAKEN_OFF_HOLD, PhoneState.CONNECTED),
                    new Pair<>(PhoneTrigger.HUNG_UP, PhoneState.OFF_HOOK)
            ));
        }

        private static PhoneState currentState = PhoneState.OFF_HOOK;
        private static final PhoneState exitState = PhoneState.ON_HOOK;

        public static void main(String[] args) {
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            do {
                System.out.println("Phone is currently: " + currentState);
                System.out.println("Select trigger: ");

                for (int i = 0; i < rules.get(currentState).size(); i++) {
                    PhoneTrigger trigger = rules.get(currentState).get(i).getValue0();
                    System.out.println("" + i + ". " + trigger);
                }

                boolean parseOK;
                int choice = 0;
                do {
                    try {
                        System.out.println("Please enter your choice: ");
                        choice = Integer.parseInt(console.readLine());
                        parseOK = choice >= 0 && choice < rules.get(currentState).size();
                    } catch (Exception e) {
                        parseOK = false;
                    }
                } while (!parseOK);

                currentState = rules.get(currentState).get(choice).getValue1();

            } while (currentState != exitState);
            System.out.println("Done!");
        }
    }

    // Spring State Machine

    enum States {
        OFF_HOOK,
        ON_HOOK,
        CONNECTING,
        CONNECTED,
        ON_HOLD
    }

    enum Events {
        CALL_DIALED,
        HUNG_UP,
        CALL_CONNECTED,
        PLACED_ON_HOLD,
        TAKEN_OFF_HOLD,
        LEFT_MESSAGE,
        STOP_USING_PHONE
    }

    static class SpringStateMachine {
        public static StateMachine<States, Events> buildMachine() throws Exception {
            StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

            builder.configureStates()
                    .withStates()
                    .initial(States.OFF_HOOK)
                    .states(Set.of(States.values()));

            builder.configureTransitions()
                    .withExternal()
                    .source(States.OFF_HOOK)
                    .event(Events.CALL_DIALED)
                    .target(States.CONNECTING)
                    .and()
                    .withExternal()
                    .source(States.OFF_HOOK)
                    .event(Events.STOP_USING_PHONE)
                    .target(States.ON_HOOK)
                    .and()
                    .withExternal()
                    .source(States.CONNECTING)
                    .event(Events.HUNG_UP)
                    .target(States.OFF_HOOK)
                    .and()
                    .withExternal()
                    .source(States.CONNECTING)
                    .event(Events.CALL_CONNECTED)
                    .target(States.CONNECTED)
                    .and()
                    .withExternal()
                    .source(States.CONNECTED)
                    .event(Events.LEFT_MESSAGE)
                    .target(States.OFF_HOOK)
                    .and()
                    .withExternal()
                    .source(States.CONNECTED)
                    .event(Events.HUNG_UP)
                    .target(States.OFF_HOOK)
                    .and()
                    .withExternal()
                    .source(States.CONNECTED)
                    .event(Events.PLACED_ON_HOLD)
                    .target(States.ON_HOLD)
                    .and()
                    .withExternal()
                    .source(States.ON_HOLD)
                    .event(Events.TAKEN_OFF_HOLD)
                    .target(States.CONNECTED)
                    .and()
                    .withExternal()
                    .source(States.ON_HOLD)
                    .event(Events.HUNG_UP)
                    .target(States.OFF_HOOK);

            return builder.build();
        }

        public static void main(String[] args) throws Exception {
            StateMachine<States, Events> machine = buildMachine();
            machine.start();

            States exitState = States.ON_HOOK;

            BufferedReader console = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            do {
                org.springframework.statemachine.state.State<States, Events> state = machine.getState();

                System.out.println("The phone is currently " + state.getId());
                System.out.println("Select a trigger: ");

                List<Transition<States, Events>> ts = machine.getTransitions()
                        .stream()
                        .filter(t -> t.getSource() == state)
                        .toList();

                for (int i = 0; i < ts.size(); ++i) {
                    System.out.println("" + i + ". " + ts.get(i).getTrigger().getEvent());
                }

                boolean parseOK;
                int choice = 0;
                do {
                    try {
                        System.out.println("Please enter your choice: ");
                        choice = Integer.parseInt(console.readLine());
                        parseOK = choice >= 0 && choice < ts.size();
                    } catch (Exception e) {
                        parseOK = false;
                    }
                } while (!parseOK);

                // Send event and perform transition
                machine.sendEvent(ts.get(choice).getTrigger().getEvent());

            } while (machine.getState().getId() != exitState);
            System.out.println("And we are done!");
        }
    }

    // Exercise

    static class CombinationLock {
        private final int[] combination;
        public String status;
        private int digitsEntered;
        private boolean failed;

        public CombinationLock(int[] combination) {
            this.combination = combination;
            reset();
        }

        private void reset() {
            status = "LOCKED";
            digitsEntered = 0;
            failed = false;
        }

        public void enterDigit(int digit) {
            if ("LOCKED".equals(status)) {
                status = "";
            }

            if (combination[digitsEntered] != digit) {
                failed = true;
            }

            digitsEntered++;
            status += String.valueOf(digit);

            if (digitsEntered == combination.length) {
                status = failed ? "ERROR" : "OPEN";
            }
        }
    }
}