package org.example.patterns.behavioral;

public class State {
    public static void main(String[] args) {
        LightSwitch lightSwitch = new LightSwitch();
        lightSwitch.off();
        lightSwitch.on();
        lightSwitch.on();
        lightSwitch.off();
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
}
