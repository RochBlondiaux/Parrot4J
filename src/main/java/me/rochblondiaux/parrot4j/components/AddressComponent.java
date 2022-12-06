package me.rochblondiaux.parrot4j.components;

import com.google.common.collect.Maps;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

@NoArgsConstructor
public class AddressComponent {
    private final Map<String, InetAddress> knownInetAddresses = Maps.newHashMap();


    public InetAddress getInetAddress(String hostName) {
        try {
            if (!knownInetAddresses.containsKey(hostName)) {
                knownInetAddresses.put(hostName, InetAddress.getByName(hostName));
            }
            return knownInetAddresses.get(hostName);
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean isReachable(String hostName, int timeout) {
        try {
            return getInetAddress(hostName).isReachable(timeout);
        } catch (IOException e) {
            return false;
        }
    }
}