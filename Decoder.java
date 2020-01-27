package arithmetic_coding;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

public class Decoder extends ArithmeticCoding {
    /*
    public final static int MINIMAL_INPUT_DATA_SIZE = MAIN_PART_SIZE + SECONDARY_PART_SIZE;

    private boolean checkInputData() {
        if (inputData.length < MINIMAL_INPUT_DATA_SIZE ||
                Math.floorMod(inputData.length - MAIN_PART_SIZE, SECONDARY_PART_SIZE) != 0) {
            return false;
        }
        return true;
    }
    */

    protected Decoder() {}

    private void parse(byte[] inputData) {
        ByteBuffer stream = ByteBuffer.wrap(inputData);

        code = stream.getDouble();
        quantity = stream.getInt();

        for (int i, cnt = 0; cnt < quantity; cnt += i) {
            frequencyMap.put(stream.get(), i = stream.getInt());
        }
    }

    private void fillSegmentMap() {
        Segment segment = new Segment();

        for (Byte elem : frequencyMap.keySet()) {
            segment.right += ((double)frequencyMap.get(elem)) / quantity;
            segmentMap.put(elem, segment.copy());

            segment.left = segment.right;
        }
    }

    private byte[] decrypt() {
        byte[] outputData = new byte[quantity];

        for (int i = 0; i < quantity; ++i) {
            for (Byte elem : segmentMap.keySet()) {
                Segment segment = segmentMap.get(elem);

                if (code >= segment.left && code < segment.right) {
                    outputData[i] = elem;
                    code = (code - segment.left) / (segment.right - segment.left);
                    break;
                }
            }
        }

        return outputData;
    }

    @Override
    public byte[] code(@NotNull final byte[] inputData) {
        reset();

        parse(inputData);
        if (quantity > 0) {
            fillSegmentMap();
            return decrypt();
        }
        return null;
    }
}
