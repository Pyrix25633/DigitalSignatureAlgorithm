package net.pyrix25633.dsa;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DSA {
    public final Random random;

    private final short L;
    private final short N;
    public final int q;
    public final int p;
    private final int h;
    public final int g;

    public DSA() {
        this.random = new Random();

        this.L = 28;
        this.N = 24;
        this.q = new PrimeGenerator(N).get();
        this.p = generateP();
        GH gh = generateGH();
        this.h = gh.h;
        this.g = gh.g;
    }

    private int generateP() {
        PrimeGenerator generator = new PrimeGenerator(L);
        int p;
        do {
            p = generator.get();
        } while((p-1) % q != 0);
        return p;
    }

    private int generateH() {
        return random.nextInt(2, p-1);
    }

    @Contract(" -> new")
    private @NotNull GH generateGH() {
        int g;
        int h;
        do {
            h = generateH();
            g = power(h, (p-1) / q, p);
        } while(g <= 1);
        return new GH(g, h);
    }

    public int H(int m) {
        int x = (int)Math.pow(2, N) - 1;
        long d = 0;
        for(int i = 0; i < 12; i++)
            d = ((d << 1) ^ ((long)(m + 5) * 3) - 2 + i) % x;
        return (int)((d+x) % x);
    }

    public Signature sign(int m, @NotNull Keys keys) {
        int k;
        int r;
        int s;
        do {
            k = random.nextInt(1, q);
            r = power(g, k, p) % q;
            s = multiply(inverse(k, q), (H(m) + multiply(keys.x, r, q)), q);
        } while(r <= 0 || s <= 0);
        return new Signature(keys.owner, r, s);
    }

    public boolean verify(int m, @NotNull Signature signature, @NotNull Keys keys) {
        if(signature.r <= 0 || signature.r >= q) return false;
        if(signature.s <= 0 || signature.s >= q) return false;
        int w = inverse(signature.s, q);
        int u1 = multiply(H(m), w, q);
        int u2 = multiply(signature.r, w, q);
        int v = multiply(power(g, u1, p), power(keys.y, u2, p), p) % q;
        return v == signature.r;
    }

    @Override
    public String toString() {
        return "L: " + L +
                "\nN: " + N +
                "\nq: " + q +
                "\np: " + p +
                "\nh: " + h +
                "\ng: " + g;
    }

    public static int multiply(int n, int t, int m) {
        n = n % m;
        t = t % m;
        return (int)(((long)n*t) % m);
    }

    public static int power(int b, int e, int m) {
        b = b % m;
        e = e % (m-1);
        int r = 1;
        for(int i = 0; i < e; i++)
            r = multiply(r, b, m);
        return (r+m) % m;
    }

    public static int inverse(int n, int m) {
        for(int i = 1; i < m; i++) {
            if(multiply(i, n, m) == 1)
                return i;
        }
        throw new IllegalStateException("No inverse of " + n + " mod " + m);
    }

    @SuppressWarnings("ClassCanBeRecord")
    private static class GH {
        public final int g;
        public final int h;

        public GH(int g, int h) {
            this.g = g;
            this.h = h;
        }
    }
}