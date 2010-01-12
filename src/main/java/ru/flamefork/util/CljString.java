package ru.flamefork.util;

import clojure.lang.IPersistentMap;
import clojure.lang.Obj;

/**
 * Clojure wrapper for String.
 * It supports Clojure metadata.
 * <p>
 * Note that
 * <code>CljString instanceof String == false</code>
 * since String class is final.
 * <p>
 * So CljString is only CharSequence.
 */
public class CljString extends Obj implements Comparable<CharSequence>, CharSequence {
    private final String s;

    public CljString(CharSequence s) {
        this.s = s.toString();
    }

    public CljString(IPersistentMap meta, String s) {
        super(meta);
        this.s = s;
    }

    public String toString() {
        return s.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof CharSequence) {
            return s.equals(obj.toString());
        }
        return super.equals(obj);
    }

    /*
    java.lang.CharSequence implementation
    */
    
    public int length() {
        return s.length();
    }

    public char charAt(int i) {
        return s.charAt(i);
    }

    public CharSequence subSequence(int i, int i1) {
        return s.subSequence(i, i1);
    }

    /*
    java.lang.Comparable<java.lang.CharSequence> implementation
    */
    
    public int compareTo(CharSequence seq) {
        return s.compareTo(seq.toString());
    }

    /*
    clojure.lang.IObj implementation
    */
    
    public Obj withMeta(IPersistentMap meta) {
        return new CljString(meta, s);
    }
}
