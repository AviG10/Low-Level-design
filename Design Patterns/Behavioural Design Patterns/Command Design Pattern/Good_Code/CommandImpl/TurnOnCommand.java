package Good_Code.CommandImpl;

import Good_Code.Command;
import Good_Code.Core_Classes.TV;

public class TurnOnCommand implements Command {
    private final TV tv;

    public TurnOnCommand(TV tv) {
        this.tv = tv;
    }

    @Override
    public void execute() {
        tv.turnOn();
    }
}
