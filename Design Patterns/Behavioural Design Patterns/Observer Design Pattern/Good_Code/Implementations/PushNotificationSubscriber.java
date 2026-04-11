package Good_Code.Implementations;

import Good_Code.Interfaces.Subscriber;

public class PushNotificationSubscriber implements Subscriber {
    private final String name;

    public PushNotificationSubscriber(String name) {
        this.name = name;
    }

    public void update(String video) {
        System.out.println("Sending Push notification to " + name + " about new video " + video);
    }
}
