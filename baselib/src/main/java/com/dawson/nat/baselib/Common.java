package com.dawson.nat.baselib;


import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * @author dawson
 */
public class Common {
    public static boolean isEmpty(final String str) {
        return str == null || "".equals(str);
    }

    public static String creatUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String[] getIPAndMac() {
        try {
            java.util.Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                List<InterfaceAddress> addrs = ni.getInterfaceAddresses();
                for (InterfaceAddress addr : addrs) {
                    InetAddress ip = addr.getAddress();
                    System.out.println(ip);
                    if (ip instanceof Inet4Address) {
                        NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                        byte[] mac = network.getHardwareAddress();
                        if (mac == null) {
                            continue;
                        }
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        return new String[]{
                                ip.getHostAddress(), sb.toString()
                        };
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
