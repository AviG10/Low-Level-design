package Good_Code.Implementations;

import Good_Code.Interfaces.Subscriber;
import Good_Code.Interfaces.YoutubeChannel;

import java.util.ArrayList;
import java.util.List;

public class YoutubeChannelImpl implements YoutubeChannel {
    private final List<Subscriber> subscribers = new ArrayList<>();
    private String video;

    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers() {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(video);
        }
    }

    public void uploadNewVideo(String video) {
        this.video = video;
        notifySubscribers();
    }
}
