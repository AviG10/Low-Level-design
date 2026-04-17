package Good_Code.Core_Classes;

import Good_Code.Command;

public class RemoteControl {
    private Command onCommand;
    private Command offCommand;
    private Command changeChannelCommand;
    private Command changeVolumeCommand;
    private Command pressOnChangeVolumeAndChangeChannel;

    public void setOnCommand(Command command){
        this.onCommand = command;
    }

    public void setOffCommand(Command command){
        this.offCommand = command;
    }

    public void setChangeChannelCommand(Command command){
        this.changeChannelCommand = command;
    }

    public void setChangeVolumeCommand(Command command){
        this.changeVolumeCommand = command;
    }

    public void setPressOnChangeVolumeAndChangeChannel(Command command){
        this.pressOnChangeVolumeAndChangeChannel = command;
    }

    public void pressOnButton(){
        onCommand.execute();
    }

    public void pressOffButton(){
        offCommand.execute();
    }

    public void pressChannelButton(){
        changeChannelCommand.execute();
    }

    public void pressVolumeButton(){
        changeVolumeCommand.execute();
    }

    public void pressOnChangeVolumeAndChangeChannel(){
        pressOnChangeVolumeAndChangeChannel.execute();
    }
}
