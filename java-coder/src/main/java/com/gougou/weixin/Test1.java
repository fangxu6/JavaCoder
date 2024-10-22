package com.gougou.weixin;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * className: Test1
 * package: com.gougou.weixin
 * Description:
 *
 * @author fangxu6@gmail.com
 * @since 2024/1/24 15:26
 */
public class Test1 {
    public static void main(String[] args) {
        try {
            InetAddress id = InetAddress.getLocalHost();
            System.out.println( id.getHostName());
        } catch (UnknownHostException e) {
        }
    }
}
