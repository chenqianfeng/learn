package com.example.streamlearn;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @program streamLearn
 * @ClassName LRUCache
 * @description lru缓存
 * @create 2023-01-30 10:08
 **/
public class LRUCache<K, V> implements Iterable {

    int MAXSIZE = 3;

    LinkedHashMap<K, V> map = new LinkedHashMap<>();

    public void cache(K k, V v ){
        if (map.containsKey(k)){
            map.remove(k);
        }else if (map.size() >= MAXSIZE){
            var it = map.keySet().iterator();
            var first = it.next();
            map.remove(first);
        }
        map.put(k, v);
    }

    @Override
    public Iterator<K> iterator() {
        var iterator = map.entrySet().iterator();
        return new Iterator<K>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public K next() {
                return iterator.next().getKey();
            }
        };
    }

    public static void main(String[] args) {
        var lru = new LRUCache<String, Integer>();
        lru.cache("A", 1);
        lru.cache("B", 2);
        lru.cache("C", 3);
        lru.cache("D", 4);
        System.out.println(
                "leave " + StreamSupport.stream(lru.spliterator(), false)
                        .map(x->x.toString())
                        .collect(Collectors.joining("<-"))
        );
        lru.cache("C", 10);

        System.out.println(
                "leave " + StreamSupport.stream(lru.spliterator(), false)
                        .map(x->x.toString())
                        .collect(Collectors.joining("<-"))
        );
    }
}
