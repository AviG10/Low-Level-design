package Ugly_Code;

import java.util.ArrayList;
import java.util.List;

public class YoutubeChannel {
    private final List<Subscriber> subscribers =  new ArrayList<>();
    private String video;

    // Method to add a new subscriber
    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    // Method to remove a subscriber
    public void removeSubscriber(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    // Method to upload a new video
    public void uploadNewVideo(String video){
        this.video = video;
        notifySubscribers();
    }

    // Notify all subscribers
    public void notifySubscribers(){
        for(Subscriber subscriber : subscribers){
            switch (subscriber.getSubscribeType()) {
                //Violating Open/Closed Principle
                case "PushNotification" ->
                        System.out.println("Sending Push notification to " + subscriber.getName() + " about new video " + video);
                case "Email" ->
                        System.out.println("Sending Email to " + subscriber.getName() + " about new video " + video);
                case "SMS" ->
                        System.out.println("Sending SMS to " + subscriber.getName() + " about new video " + video);
                default -> System.out.println("Invalid subscribe.");
            }

        }
    }

}
