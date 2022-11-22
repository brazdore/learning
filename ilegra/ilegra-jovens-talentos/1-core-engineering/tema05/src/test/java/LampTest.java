import org.example.entities.HalogenLamp;
import org.example.entities.IncandescentLamp;
import org.example.entities.Switch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class LampTest {
    IncandescentLamp incandescentLamp = new IncandescentLamp();
    HalogenLamp halogenLamp = new HalogenLamp();
    Switch switchON = new Switch();
    Switch switchOFF = new Switch();

    public LampTest() {
    }

    @BeforeEach
    void init() {
        this.incandescentLamp = new IncandescentLamp();
        this.halogenLamp = new HalogenLamp();
        this.switchON = new Switch(this.halogenLamp, true);
        this.switchOFF = new Switch(this.incandescentLamp, false);
    }

    @org.junit.jupiter.api.Test
    public void mustTurnRoomLightsON() {
        Assertions.assertFalse(this.switchOFF.isSwitchON());
        this.switchOFF.switchState();
        Assertions.assertTrue(this.switchOFF.isSwitchON());
    }

    @org.junit.jupiter.api.Test
    public void mustTurnRoomLightsOFF() {
        Assertions.assertTrue(this.switchON.isSwitchON());
        this.switchON.switchState();
        Assertions.assertFalse(this.switchON.isSwitchON());
    }

    @org.junit.jupiter.api.Test
    public void mustTurnLamplightLightsON() {
        Assertions.assertFalse(this.incandescentLamp.isLampOn());
        this.switchOFF.switchState();
        Assertions.assertTrue(this.incandescentLamp.isLampOn());
    }

    @org.junit.jupiter.api.Test
    public void mustTurnLamplightLightsOFF() {
        Assertions.assertTrue(this.halogenLamp.isLampOn());
        this.switchON.switchState();
        Assertions.assertFalse(this.halogenLamp.isLampOn());
    }

    @org.junit.jupiter.api.Test
    public void changingLampStillON() {
        Assertions.assertTrue(this.halogenLamp.isLampOn());
        this.switchON.changeLamp(this.incandescentLamp);
        Assertions.assertTrue(this.incandescentLamp.isLampOn());
    }

    @org.junit.jupiter.api.Test
    public void switchingLampTurnsOFF() {
        IncandescentLamp newLamp = new IncandescentLamp();
        Assertions.assertFalse(newLamp.isLampOn());
        Assertions.assertTrue(this.halogenLamp.isLampOn());
        this.switchON.changeLamp(newLamp);
        Assertions.assertTrue(newLamp.isLampOn());
        Assertions.assertFalse(this.halogenLamp.isLampOn());
    }
}