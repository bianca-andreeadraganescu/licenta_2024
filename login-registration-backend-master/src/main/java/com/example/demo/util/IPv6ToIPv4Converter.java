package com.example.demo.util;

public class IPv6ToIPv4Converter {

    public static String convertIPv6ToIPv4(String ipv6Address) {
        if (ipv6Address.startsWith("::ffff:")) {
            try {
                String[] parts = ipv6Address.split(":");
                String ipv4Part = parts[parts.length - 1];
                return ipv4Part;
            } catch (Exception e) {
                throw new IllegalArgumentException("Adresa IPv6 nu poate fi convertită în IPv4.");
            }
        } else {
            throw new IllegalArgumentException("Adresa IPv6 nu poate fi convertită în IPv4.");
        }
    }

    private static boolean isIPv4MappedAddress(byte[] bytes) {
        return bytes.length == 16 &&
                bytes[0] == 0 && bytes[1] == 0 && bytes[2] == 0 && bytes[3] == 0 &&
                bytes[4] == 0 && bytes[5] == 0 && bytes[6] == 0 && bytes[7] == 0 &&
                bytes[8] == 0 && bytes[9] == 0 && bytes[10] == (byte) 0xff && bytes[11] == (byte) 0xff;
    }

    private static boolean isIPv4CompatibleAddress(byte[] bytes) {
        return bytes.length == 16 &&
                bytes[0] == 0 && bytes[1] == 0 && bytes[2] == 0 && bytes[3] == 0 &&
                bytes[4] == 0 && bytes[5] == 0 && bytes[6] == 0 && bytes[7] == 0 &&
                bytes[8] == 0 && bytes[9] == 0 && bytes[10] == 0 && bytes[11] == 0;
    }

    private static String convertToIPv4(byte[] bytes) {
        int ipv4Address = (bytes[12] & 0xFF) << 24 |
                (bytes[13] & 0xFF) << 16 |
                (bytes[14] & 0xFF) << 8 |
                (bytes[15] & 0xFF);
        return String.format("%d.%d.%d.%d",
                (ipv4Address >> 24) & 0xFF,
                (ipv4Address >> 16) & 0xFF,
                (ipv4Address >> 8) & 0xFF,
                ipv4Address & 0xFF);
    }

    public static void main(String[] args) {
        try {
            String ipv6Address = "0:0:0:0:0:0:0:1";
            String ipv4Address = convertIPv6ToIPv4(ipv6Address);
            System.out.println("Adresa IPv4 este: " + ipv4Address);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
