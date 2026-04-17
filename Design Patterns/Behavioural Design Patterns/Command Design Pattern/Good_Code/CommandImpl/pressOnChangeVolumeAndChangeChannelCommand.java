package Good_Code.CommandImpl;

import Good_Code.Command;
import Good_Code.Core_Classes.TV;

public class pressOnChangeVolumeAndChangeChannelCommand implements Command {
    private final TV tv;
    private final int channel;
    private final int volume;

    public pressOnChangeVolumeAndChangeChannelCommand(TV tv, int channel, int volume){
        this.tv = tv;
        this.channel = channel;
        this.volume = volume;
    }

    @Override
    public void execute() {
        tv.turnOn();
        tv.adjustVolume(volume);
        tv.changeChannel(channel);
    }

}
