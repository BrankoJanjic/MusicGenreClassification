package is.fon.rs.statistics;

import java.util.Arrays;

public class Statistics {
	
	public static double getAverage(double[] data) {
		if (data.length < 1)
			return 0.0;
		double sum = 0.0;
		for (int i = 0; i < data.length; i++)
			sum = sum + data[i];
		return (sum / ((double) data.length));
	}

	public static double getAverage(int[] data) {
		if (data.length < 1)
			return 0.0;
		double sum = 0.0;
		for (int i = 0; i < data.length; i++)
			sum = sum + (double) data[i];
		return (sum / ((double) data.length));
	}

	public static double getStandardDeviation(double[] data) {
		if (data.length < 2)
			return 0.0;
		double average = getAverage(data);
		double sum = 0.0;
		for (int i = 0; i < data.length; i++) {
			double diff = data[i] - average;
			sum = sum + diff * diff;
		}
		return Math.sqrt(sum / ((double) (data.length - 1)));
	}

	public static double getStandardDeviation(int[] data) {
		if (data.length < 2)
			return 0.0;
		double average = getAverage(data);
		double sum = 0.0;
		for (int i = 0; i < data.length; i++) {
			double diff = ((double) data[i]) - average;
			sum = sum + diff * diff;
		}
		return Math.sqrt(sum / ((double) (data.length - 1)));
	}

	public static int getIndexOfLargest(double[] values) {
		int max_index = 0;
		for (int i = 0; i < values.length; i++)
			if (values[i] > values[max_index])
				max_index = i;
		return max_index;
	}

	public static int getIndexOfSmallest(double[] values) {
		int min_index = 0;
		for (int i = 0; i < values.length; i++)
			if (values[i] < values[min_index])
				min_index = i;
		return min_index;
	}

	public static double calculateEuclideanDistance(double[] x, double[] y)
			throws Exception {
		if (x.length != y.length)
			throw new Exception("The two given arrays have different sizes.");

		double total = 0.0;
		for (int dim = 0; dim < x.length; dim++)
			total += Math.pow((x[dim] - y[dim]), 2);
		return Math.sqrt(total);
	}

	/**
	 * Returns a random integer from 0 to max - 1, based on the uniform
	 * distribution.
	 */
	public static int generateRandomNumber(int max) {
		int random_number = (int) (((double) Integer.MAX_VALUE) * Math.random());
		return (random_number % max);
	}

	public static double getArraySum(double[] to_sum) {
		double sum = 0.0;
		for (int i = 0; i < to_sum.length; i++)
			sum += to_sum[i];
		return sum;
	}

	public static int pow(int a, int b) {
		int result = a;
		for (int i = 1; i < b; i++)
			result *= a;
		return result;
	}

	public static double logBaseN(double x, double n) {
		return (Math.log10(x) / Math.log10(n));
	}

	public static int ensureIsPowerOfN(int x, int n) {
		double log_value = logBaseN((double) x, (double) n);
		int log_int = (int) log_value;
		int valid_size = pow(n, log_int);
		if (valid_size != x)
			valid_size = pow(n, log_int + 1);
		return valid_size;
	}

	public static double getMedian(double[] values) {
		double[] b = new double[values.length];
		System.arraycopy(values, 0, b, 0, b.length);
		Arrays.sort(b);

		if (values.length % 2 == 0) {
			return (b[(b.length / 2) - 1] + b[b.length / 2]) / 2.0;
		} else {
			return b[b.length / 2];
		}
	}

	public static double getMaxElement(double[] values) {
		double max = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < values.length; i++) {
			if (Double.isNaN(values[i]))
				return Double.NaN;
			if (values[i] > max)
				max = values[i];
		}
		return max;
	}

	public static double getMinElement(double[] values) {
		double min = Double.POSITIVE_INFINITY;
		for (int i = 0; i < values.length; i++) {
			if (Double.isNaN(values[i]))
				return Double.NaN;
			if (values[i] < min)
				min = values[i];
		}
		return min;
	}

	public static double getVariance(double[] values) {
		if (values.length == 0)
			return Double.NaN;
		double avg = getAverage(values);
		double sum = 0.0;
		for (int i = 0; i < values.length; i++) {
			sum += (values[i] - avg) * (values[i] - avg);
		}
		return sum / values.length;
	}

	public static double getRange(double[] values) {
		if (values.length == 0)
			return Double.NaN;
		return getMaxElement(values) - getMinElement(values);
	}

	public double getSkewness(final double[] values) {

		double skew = Double.NaN;

		if (values.length > 2) {

			// Get the mean and the standard deviation
			double m = getAverage(values);

			double accum = 0.0;
			double accum2 = 0.0;
			for (int i = 0; i < values.length; i++) {
				final double d = values[i] - m;
				accum += d * d;
				accum2 += d;
			}
			final double variance = (accum - (accum2 * accum2 / values.length))
					/ (values.length - 1);

			double accum3 = 0.0;
			for (int i = 0; i < values.length; i++) {
				final double d = values[i] - m;
				accum3 += d * d * d;
			}
			accum3 /= variance * Math.sqrt(variance);

			// Get N
			double n0 = values.length;

			// skewness
			skew = (n0 / ((n0 - 1) * (n0 - 2))) * accum3;
		}
		return skew;
	}

	public double getKurtosis(final double[] values, final int begin,
			final int length) {

		double kurt = Double.NaN;

		if (length > 3) {

			// Compute the mean and standard deviation

			double mean = getAverage(values);
			double stdDev = getStandardDeviation(values);

			// Sum the ^4 of the distance from the mean divided by the
			// standard deviation
			double accum3 = 0.0;
			for (int i = begin; i < begin + length; i++) {
				accum3 += Math.pow(values[i] - mean, 4.0);
			}
			accum3 /= Math.pow(stdDev, 4.0d);

			// Get N
			double n0 = length;

			double coefficientOne = (n0 * (n0 + 1))
					/ ((n0 - 1) * (n0 - 2) * (n0 - 3));
			double termTwo = (3 * Math.pow(n0 - 1, 2.0))
					/ ((n0 - 2) * (n0 - 3));

			// Calculate kurtosis
			kurt = (coefficientOne * accum3) - termTwo;
		}
		return kurt;
	}

}