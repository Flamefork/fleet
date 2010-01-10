package ru.flamefork.fleet;

public class RawString implements java.lang.Comparable<java.lang.CharSequence>, java.lang.CharSequence {
    private final String s;

    public RawString(String s) {
        this.s = s;
    }

    public String toString() {
        return s.toString();
    }

    public int length() {
        return s.length();
    }

    public char charAt(int i) {
        return s.charAt(i);
    }

    public CharSequence subSequence(int i, int i1) {
        return s.subSequence(i, i1);
    }

    public int compareTo(CharSequence seq) {
        return compareTo(seq.toString());
    }
    
}
