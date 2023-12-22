package edu.project4;

import edu.project4.domain.Point;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AffineTransformationTest {
    @Test
    void affineTransformationIsAppliedCorrectly() {
        AffineTransformation transformation = new AffineTransformation(1, 2, 3, 4, 5, 6, null);
        Point point = new Point(7, 8);

        Point transformed = transformation.apply(point);

        assertThat(transformed.x()).isCloseTo(1 * 7 + 2 * 8 + 3, Offset.offset(1e-6));
        assertThat(transformed.y()).isCloseTo(4 * 7 + 5 * 8 + 6, Offset.offset(1e-6));
    }
}
