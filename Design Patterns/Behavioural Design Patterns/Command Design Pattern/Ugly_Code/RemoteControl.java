package Ugly_Code;

public class RemoteControl {
    private final TV tv;

    public RemoteControl(TV tv){
        this.tv = tv;
    }

    public void pressOnButton(){
        tv.turnOn();
    }

    public void pressOffButton(){
        tv.turnOff();
    }

    public void pressChannelButton(int channel){
        tv.changeChannel(channel);
    }

    public void pressVolumeButton(int volume){
        tv.adjustVolume(volume);
    }

    public void pressOnChangeVolumeAndChangeChannel(int channel, int volume){
        tv.turnOn();
        tv.changeChannel(channel);
        tv.adjustVolume(volume);
    }
}
