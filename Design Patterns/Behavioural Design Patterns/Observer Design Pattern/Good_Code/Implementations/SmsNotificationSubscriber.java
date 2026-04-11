package Good_Code.Implementations;

import Good_Code.Interfaces.Subscriber;

public class SmsNotificationSubscriber implements Subscriber {
    private final String name;

    public SmsNotificationSubscriber(String name) {
        this.name = name;
    }

    public void update(String video){
        System.out.println("Sending SMS to " + name + " about new video " + video);
    }
}
