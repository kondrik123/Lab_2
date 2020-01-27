package arithmetic_coding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class ArithmeticCoding {
    public final static int OPTIMUM_QUANTITY = 16;
    protected final static int MAIN_PART_SIZE = Double.BYTES + Integer.BYTES;
    protected final static int SECONDARY_PART_SIZE = Byte.BYTES +  Integer.BYTES;

    public enum Mode {
        ENCRYPTION,
        DECRYPTION
    }

    protected double code;
    protected int quantity;
    protected HashMap<Byte, Integer> frequencyMap = new HashMap<>();
    protected HashMap<Byte, Segment> segmentMap = new HashMap<>();

    public abstract byte[] code(@NotNull final byte[] inputData);

    protected void reset() {
        code = 0;
        quantity = 0;
        if (!frequencyMap.isEmpty()) { frequencyMap.clear(); }
        if (!segmentMap.isEmpty()) { segmentMap.clear(); }
    }
}
