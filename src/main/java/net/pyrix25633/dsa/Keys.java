package net.pyrix25633.dsa;

import org.jetbrains.annotations.NotNull;

public class Keys {
    public final String owner;
    public final int x;
    public final int y;

    public Keys(String owner, @NotNull DSA dsa) {
        this.owner = owner;
        this.x = dsa.random.nextInt(1, dsa.q);
        this.y = DSA.power(dsa.g, x, dsa.p);
    }

    @Override
    public String toString() {
        return owner + "'s x: " + x +
                "\n" + owner + "'s y: " + y;
    }
}