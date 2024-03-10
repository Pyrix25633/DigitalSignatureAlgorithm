package net.pyrix25633.dsa;

@SuppressWarnings("ClassCanBeRecord")
public class Signature {
    public final String signer;
    public final int r;
    public final int s;

    public Signature(String signer, int r, int s) {
        this.signer = signer;
        this.r = r;
        this.s = s;
    }

    @Override
    public String toString() {
        return signer + "'s Signature (r: " + r + ", s: " + s + ")";
    }
}