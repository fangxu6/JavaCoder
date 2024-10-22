package com.gougou.weixin;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * className: Test1
 * package: com.gougou.weixin
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2024/1/24 14:59
 */
public class Test {
    public static void main(String[] args) throws SocketException {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        System.out.println(dateFormat.format(date));
        InetAddress a = null;
        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
        if (nifs != null) {
            System.out.println("2222");

            label59:
            while (true) {
                NetworkInterface nif;
                while (true) {
                    if (!nifs.hasMoreElements()) {
                        break label59;
                    }
                    System.out.println("3333");

                    nif = (NetworkInterface) nifs.nextElement();

                    try {
                        if (!nif.isLoopback() && !nif.isVirtual() && nif.isUp()) {
                            break;
                        }
                    } catch (SocketException var5) {
                    }
                }
                System.out.println("4444");

                Enumeration<InetAddress> addresses = nif.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress address = (InetAddress) addresses.nextElement();
                    if (!address.isLoopbackAddress() && !address.isLinkLocalAddress() && !(address instanceof Inet6Address)) {
                        a = address;
                    }
                }
                System.out.println("5555");

            }
        }

        date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println(a.toString());
        System.out.println(a.getHostName());
        date = new Date();
        System.out.println(dateFormat.format(date));
    }

}
