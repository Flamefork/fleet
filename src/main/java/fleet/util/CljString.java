package fleet.util;

import clojure.lang.*;

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
public class CljString extends Obj implements Comparable<CharSequence>, CharSequence, Seqable {
    private final String s;

    public CljString(CharSequence s) {
        this.s = s.toString();
    }

    public CljString(IPersistentMap meta, CharSequence s) {
        super(meta);
        this.s = s.toString();
    }

    public String toString() {
        return s;
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

    /*
    clojure.lang.Seqable implementation
    */
    
    public ISeq seq() {
        return RT.seq(s);
    }
}
