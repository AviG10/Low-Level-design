package Ugly_Code;

public class CommandPatternUglyMain {
    public static void main(String[] args){
        TV tv = new TV();
        RemoteControl remoteControl = new RemoteControl(tv);

        remoteControl.pressOnButton();
        remoteControl.pressChannelButton(123);
        remoteControl.pressVolumeButton(20);
        remoteControl.pressOnChangeVolumeAndChangeChannel(69,20);
        remoteControl.pressOffButton();
    }
}
