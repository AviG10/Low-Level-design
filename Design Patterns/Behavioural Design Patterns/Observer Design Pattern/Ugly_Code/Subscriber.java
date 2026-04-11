package Ugly_Code;

public class Subscriber {
    private final String name;
    private String subscribeType = "PushNotification";

    public Subscriber(String name) {
        this.name = name;
    }

    public void setSubscribeType(String subscribeType){
        this.subscribeType = subscribeType;
    }

    public String getSubscribeType(){
        return subscribeType;
    }

    public String getName(){
        return name;
    }

    public void subscribe(YoutubeChannel channel){
        channel.addSubscriber(this);
    }

    public void unsubscribe(YoutubeChannel channel){
        channel.removeSubscriber(this);
    }
}
