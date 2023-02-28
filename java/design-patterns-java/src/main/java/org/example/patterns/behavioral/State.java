package org.example.patterns.behavioral;

import org.javatuples.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {
    public static void main(String[] args) {
        LightSwitch lightSwitch = new LightSwitch();
        lightSwitch.off();
        lightSwitch.on();
        lightSwitch.on();
        lightSwitch.off();

        PhoneStateMachine.main(new String[]{""});
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

}
