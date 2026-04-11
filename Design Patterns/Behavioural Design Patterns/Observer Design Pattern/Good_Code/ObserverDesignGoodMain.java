package Good_Code;

import Good_Code.Implementations.EmailNotificationSubscriber;
import Good_Code.Implementations.PushNotificationSubscriber;
import Good_Code.Implementations.SmsNotificationSubscriber;
import Good_Code.Implementations.YoutubeChannelImpl;

public class ObserverDesignGoodMain {
    public static void main(String[] args) {
        PushNotificationSubscriber alice = new PushNotificationSubscriber("Alice");
        EmailNotificationSubscriber bob = new EmailNotificationSubscriber("Bob");
        SmsNotificationSubscriber charlie = new SmsNotificationSubscriber("Charlie");

        YoutubeChannelImpl channel = new YoutubeChannelImpl();
        channel.addSubscriber(alice);
        channel.addSubscriber(bob);
        channel.addSubscriber(charlie);

        channel.uploadNewVideo("Java Tutorial EP-1");

        channel.removeSubscriber(alice);

        channel.uploadNewVideo("Java Tutorial EP-2");
    }
}
