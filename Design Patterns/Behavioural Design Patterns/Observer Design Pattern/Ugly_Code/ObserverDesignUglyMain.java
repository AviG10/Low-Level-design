package Ugly_Code;

public class ObserverDesignUglyMain {
    public static void main(String[] args){
        Subscriber alice = new Subscriber("Alice");
        Subscriber bob = new Subscriber("Bob");
        Subscriber charlie = new Subscriber("Charlie");

        YoutubeChannel channel = new YoutubeChannel();

        alice.subscribe(channel);
        alice.setSubscribeType("Email");

        bob.subscribe(channel);
        bob.setSubscribeType("SMS");

        charlie.subscribe(channel);

        channel.uploadNewVideo("Java Tutorial Video EP-1");

        alice.unsubscribe(channel);
        bob.unsubscribe(channel);

        channel.uploadNewVideo("Java Tutorial Video EP-2");
    }
}
