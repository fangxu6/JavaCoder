package jnsw.copy.util;

public enum Algorithm {
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    AES("AES"),
    RSA("RSA");

    private final String key;

    private Algorithm(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
