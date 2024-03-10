package net.pyrix25633.dsa;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Main {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.startTask("Digital Signature Algorithm");

        timer.startTask("DSA");
        DSA dsa = new DSA();
        timer.endTask();

        timer.startTask("Alice's Keys");
        Keys aliceKeys = new Keys("Alice", dsa);
        timer.endTask();
        timer.startTask("Bob's Keys");
        Keys bobKeys = new Keys("Bob", dsa);
        timer.endTask();

        int aliceMessage = dsa.random.nextInt(0, Integer.MAX_VALUE);
        timer.startTask("Alice's Signature");
        Signature aliceSignature = dsa.sign(aliceMessage, aliceKeys);
        timer.endTask();
        int bobMessage = dsa.random.nextInt(0, Integer.MAX_VALUE);
        timer.startTask("Alice's Signature");
        Signature bobSignature = dsa.sign(bobMessage, bobKeys);
        timer.endTask();
        timer.startTask("Trudy's Fake Signature");
        Signature trudySignature = new Signature("Trudy",
                dsa.random.nextInt(1, dsa.q), dsa.random.nextInt(1, dsa.q));
        timer.endTask();
        int trudyMessage = dsa.random.nextInt(0, Integer.MAX_VALUE);

        timer.startTask("Alice's Signature Verification");
        boolean aliceSignatureVerified = dsa.verify(aliceMessage, aliceSignature, aliceKeys);
        boolean aliceSignatureTrudyMessageVerified = dsa.verify(trudyMessage, aliceSignature, aliceKeys);
        timer.endTask();
        timer.startTask("Bob's Signature Verification");
        boolean bobSignatureVerified = dsa.verify(bobMessage, bobSignature, bobKeys);
        boolean bobSignatureTrudyMessageVerified = dsa.verify(trudyMessage, bobSignature, bobKeys);
        timer.endTask();
        timer.startTask("Trudy's Fake Signature Verification");
        boolean trudySignatureAliceMessageVerified = dsa.verify(aliceMessage, trudySignature, aliceKeys);
        boolean trudySignatureBobMessageVerified = dsa.verify(bobMessage, trudySignature, bobKeys);
        timer.endTask();

        timer.endTask();

        System.out.println(dsa);
        System.out.println(aliceKeys);
        System.out.println(bobKeys);
        System.out.println("Alice's Message: " + aliceMessage);
        System.out.println(aliceSignature);
        System.out.println("Bob's Message: " + bobMessage);
        System.out.println(bobSignature);
        System.out.println("Trudy's Message: " + trudyMessage);
        System.out.println(trudySignature);
        System.out.print(test(aliceSignatureVerified, true));
        System.out.println(" Alice's Signature Verified: " + aliceSignatureVerified);
        System.out.print(test(aliceSignatureTrudyMessageVerified, false));
        System.out.println(" Alice's Signature Verified for Trudy's Message: " + aliceSignatureTrudyMessageVerified);
        System.out.print(test(bobSignatureVerified, true));
        System.out.println(" Bob's Signature Verified: " + bobSignatureVerified);
        System.out.print(test(bobSignatureTrudyMessageVerified, false));
        System.out.println(" Bob's Signature Verified for Trudy's Message: " + bobSignatureTrudyMessageVerified);
        System.out.print(test(trudySignatureAliceMessageVerified, false));
        System.out.println(" Trudy's Fake Signature Verified for Alice's Message: " + trudySignatureAliceMessageVerified);
        System.out.print(test(trudySignatureBobMessageVerified, false));
        System.out.println(" Trudy's Fake Signature Verified for Bob's Message: " + trudySignatureBobMessageVerified);
    }

    @Contract(pure = true)
    private static @NotNull String test(boolean value, boolean expected) {
        return (value == expected) ? "✔" : "❌";
    }
}