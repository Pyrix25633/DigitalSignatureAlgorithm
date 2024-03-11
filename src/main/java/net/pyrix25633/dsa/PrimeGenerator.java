package net.pyrix25633.dsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PrimeGenerator {
    private final Random random;
    private final List<Integer> primes;

    public PrimeGenerator(short bits) {
        this.random = new Random();

        int n = (int)Math.pow(2, bits);
        this.primes = new ArrayList<>();
        boolean[] prime = new boolean[n];
        Arrays.fill(prime, true);
        for(int p = 2; (long)p * p < n; p++) {
            if(prime[p]) {
                for(int i = p * 2; i < n; i += p)
                    prime[i] = false;
            }
        }
        for(int i = 2; i < n; i++) {
            if(prime[i])
                primes.add(i);
        }
    }

    public int get() {
        return primes.get(random.nextInt(primes.size()));
    }
}