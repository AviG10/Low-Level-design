package Good_Code;

import Good_Code.CommandImpl.*;
import Good_Code.Core_Classes.RemoteControl;
import Good_Code.Core_Classes.TV;

public class CommandPatternGoodMain {
    public static void main(String[] args) {
        TV tv = new TV();

        Command turnOn = new TurnOnCommand(tv);
        Command turnOff = new TurnOffCommand(tv);
        Command changeChannel = new ChangeChannelCommand(tv,123);
        Command adjustVolume = new AdjustVolumeCommand(tv,20);
        Command pressOnChangeVolumeAndChangeChannel = new pressOnChangeVolumeAndChangeChannelCommand(tv,69,10);

        RemoteControl remoteControl = new RemoteControl();
        remoteControl.setOnCommand(turnOn);
        remoteControl.setOffCommand(turnOff);
        remoteControl.setChangeChannelCommand(changeChannel);
        remoteControl.setChangeVolumeCommand(adjustVolume);
        remoteControl.setPressOnChangeVolumeAndChangeChannel(pressOnChangeVolumeAndChangeChannel);

        remoteControl.pressOnButton();
        remoteControl.pressChannelButton();
        remoteControl.pressVolumeButton();
        remoteControl.pressOnChangeVolumeAndChangeChannel();
        remoteControl.pressOffButton();
    }
}
