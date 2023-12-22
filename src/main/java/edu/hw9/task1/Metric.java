package edu.hw9.task1;

class Metric {
    private final String name;
    private final double[] data;

    Metric(String name, double[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public Statistic compute() {
        double sum = 0;
        double max = -Double.MAX_VALUE;
        double min = Double.MAX_VALUE;

        for (double datum : data) {
            sum += datum;
            max = Math.max(datum, max);
            min = Math.min(datum, min);
        }

        return new Statistic(max, min, sum, sum / data.length);
    }
}
