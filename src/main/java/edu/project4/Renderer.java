package edu.project4;

import edu.project4.domain.FractalImage;
import java.util.List;

public interface Renderer {
    FractalImage render(List<Transformation> variations,
                        int samples, int iterationsPerSample, int symmetry,
                        int width, int height);
}
