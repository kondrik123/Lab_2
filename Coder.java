package arithmetic_coding;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class Coder extends ArithmeticCoding {
    protected Coder() {}

    private void parse(byte[] inputData) {
        final int one = 1;

        for (byte elem : inputData) {
            if (frequencyMap.containsKey(elem)) {
                frequencyMap.put(elem, frequencyMap.get(elem) + one);
            } else {
                frequencyMap.put(elem, one);
            }
        }
        quantity = inputData.length;
    }

    private void fillSegmentMap() {
        Segment segment = new Segment();

        for (Byte elem : frequencyMap.keySet()) {
            segment.right += ((double)frequencyMap.get(elem)) / quantity;
            segmentMap.put(elem, segment.copy());

            segment.left = segment.right;
        }
    }

    private byte[] fillArray() {
        byte[] outputData = new byte[MAIN_PART_SIZE + SECONDARY_PART_SIZE * frequencyMap.keySet().size()];
        ByteBuffer buffer = ByteBuffer.wrap(outputData);

        buffer.putDouble(code);
        buffer.putInt(quantity);

        for (byte elem : frequencyMap.keySet()) {
            buffer.put(elem);
            buffer.putInt(frequencyMap.get(elem));
        }

        return outputData;
    }

    private byte[] encrypt(byte[] inputData) {
        double newLeft, newRight;
        Segment segment = new Segment(0.0, 1.0);

        for (byte elem : inputData) {
            newLeft = segment.left + (segment.right - segment.left) * segmentMap.get(elem).left;
            newRight = segment.left + (segment.right - segment.left) * segmentMap.get(elem).right;

            segment.set(newLeft, newRight);
        }
        code = segment.middleValue();

        return fillArray();
    }

    @Override
    public byte[] code(@NotNull final byte[] inputData) {
        reset();

        parse(inputData);
        if (quantity > 0) {
            fillSegmentMap();
            return encrypt(inputData);
        }
        return null;
    }
}
