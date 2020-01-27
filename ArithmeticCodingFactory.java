package arithmetic_coding;

import org.jetbrains.annotations.NotNull;

public class ArithmeticCodingFactory {
    public static @NotNull ArithmeticCoding build(@NotNull ArithmeticCoding.Mode mode) {

        if (mode == ArithmeticCoding.Mode.ENCRYPTION) {
            return new Coder();
        }
        return new Decoder();
    }
}
