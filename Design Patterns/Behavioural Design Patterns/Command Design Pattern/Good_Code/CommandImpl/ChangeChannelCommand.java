package Good_Code.CommandImpl;

import Good_Code.Command;
import Good_Code.Core_Classes.TV;

public class ChangeChannelCommand implements Command {
    private final TV tv;
    private final int channel;

    public ChangeChannelCommand(TV tv, int channel) {
        this.tv = tv;
        this.channel = channel;
    }

    @Override
    public void execute(){
        tv.changeChannel(channel);
    }
}
