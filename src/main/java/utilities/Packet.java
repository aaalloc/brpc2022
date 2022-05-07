package utilities;

import java.util.AbstractMap;

/**
 * Represent a Tuple of two element send a by a Client through a socket.
 * @param <K> Left element
 * @param <V> Right element
 */
public class Packet<K,V extends Boolean> extends AbstractMap.SimpleEntry<K, V> {
    public Packet(K key, V value) {
        super(key, value);
    }

    public K getData(){
        return this.getKey();
    }

    public V getClientState(){
        return this.getValue();
    }
}
