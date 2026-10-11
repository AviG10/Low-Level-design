package Code;

import java.util.*;

public class LRUCacheDemo {
    public static void main(String[] args) {
        System.out.println("======= LRU Cache Demo Start ======");

        LRUCache<String, Integer> lruCache = new LRUCache<>(3);
        lruCache.put("a", 1);
        lruCache.put("b", 2);
        lruCache.put("c", 3); // c->b->a

        Integer valueA = lruCache.get("a"); // a->c->b
        System.out.println("valueA: " + valueA);

        lruCache.put("d", 4); // d->a->c
        System.out.println("valueB: " + lruCache.get("b") ); // null

        System.out.println("valueA: " + lruCache.get("a") ); // a->d->c
        System.out.println("valueC: " + lruCache.get("c") ); // c->a->d
        System.out.println("valueD: " + lruCache.get("d") ); // d->c->a

        lruCache.put("a",5); // update the existing value and recency order to a->b->c

        lruCache.put("e",6); // e->a->b

        System.out.println("valueA: " + lruCache.get("a") ); // a->e->b

        System.out.println("======= LRU Cache Demo End ======");
    }
}
