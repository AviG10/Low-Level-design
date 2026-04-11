package Good_Code.Implementations;

import Good_Code.Interfaces.Subscriber;

public class EmailNotificationSubscriber implements Subscriber {
    private final String name;

    public EmailNotificationSubscriber(String name) {
        this.name = name;
    }

    public void update(String video) {
        System.out.println("Sending Email to " + name + " about new video " + video);
    }
}
