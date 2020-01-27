package arithmetic_coding;

import org.jetbrains.annotations.NotNull;

class  Segment {
    double left = 0.0;
    double right = 0.0;

    Segment() {}

    Segment(double l, double r) { left = l; right = r; }

    Segment(@NotNull final Segment segment) {
        left = segment.left;
        right = segment.right;
    }

    public Segment copy() { return new Segment(left, right); }

    double middleValue() { return (right + left) / 2; }

    void set(double l, double r) { left = l; right = r; }
}
