import com.klg.jclass.table.TableDataModel;
import star.common.MonitorPlot;
import star.common.StarPlot;

import java.util.Arrays;
import java.util.HashMap;

public class ConvergenceChecker {

    public static final int COLUMN = 1;
    public static final int MOVING_AVERAGE_WINDOW = 600;
    public static final int CONVERGENCE_SCORE_CUTOFF = 10;
    public static final int STD_WINDOW = 750;
    public static final int MIN_MAX_WINDOW = 750;
    public HashMap<String, Boolean> convergenceResults;

    public ConvergenceChecker(SimComponents activeSim)
    {
        convergenceResults = new HashMap<>();
        activeSim.activeSim.println("Convergence cutoff set to: " + CONVERGENCE_SCORE_CUTOFF);

        for (StarPlot plt : activeSim.plots)
        {
            if (!(plt instanceof MonitorPlot))
                continue;

            TableDataModel rawData = plt.getTableDataModel();
            int totalValues = rawData.getNumRows();
            double[] iterationHistory = new double[totalValues];

            for (int i = 0; i < totalValues; i++)
                iterationHistory[i] = Double.parseDouble(rawData.getTableDataItem(i, COLUMN).toString());

            double[] movingAverageArray = movingAverage(iterationHistory);

            if (movingAverageArray == null || STD_WINDOW > movingAverageArray.length || MIN_MAX_WINDOW > movingAverageArray.length)
            {
                convergenceResults.put(plt.getPresentationName(), false);
                continue;
            }

            double[] std_slice = Arrays.copyOfRange(movingAverageArray, movingAverageArray.length - STD_WINDOW, movingAverageArray.length);
            double[] min_max_slice = Arrays.copyOfRange(movingAverageArray, movingAverageArray.length - MIN_MAX_WINDOW, movingAverageArray.length);
            double averageStdDev = stdDeviation(std_slice);
            double max = getMaximum(min_max_slice);
            double min = getMinimum(min_max_slice);
            double percentageRange = Math.abs(((max - min) / min) * 100);
            double score = cnvgAlgorithm(averageStdDev, percentageRange);
            if (score < CONVERGENCE_SCORE_CUTOFF)
            {
                convergenceResults.put(plt.getPresentationName() + " (Score: " + score + " StdDev: " + averageStdDev + ")", true);
                activeSim.activeSim.println(plt.getPresentationName() + " CONVERGED " + " (Score: " + score + " StdDev: " + averageStdDev + ")");
            }
            else {
                convergenceResults.put(plt.getPresentationName() + " (Score: " + score + " StdDev: " + averageStdDev + ")", false);
                activeSim.activeSim.println(plt.getPresentationName() + " NOT CONVERGED " + " (Score: " + score + " StdDev: " + averageStdDev + ")");
            }

            activeSim.activeSim.print("");
        }
        activeSim.activeSim.println("Convergence Check Complete");
    }

    public double cnvgAlgorithm(double stDev, double diff) {
        int scaleFactor = 2500;
        return (stDev * diff * scaleFactor);
    }

    public double getMaximum(double[] arr)
    {
        double max = arr[0];

        for (double v : arr)
        {
            if (v > max)
                max = v;
        }

        return max;
    }

    public double getMinimum(double[] arr)
    {
        double min = arr[0];

        for (double v : arr)
        {
            if (v < min)
                min = v;
        }

        return min;
    }

    public double[] movingAverage(double[] arr)
    {
        if (arr.length - MOVING_AVERAGE_WINDOW < 0)
            return null;

        double[] avgArr = new double[(arr.length - MOVING_AVERAGE_WINDOW) + 1];
        int j = 0;
        for (int i = MOVING_AVERAGE_WINDOW; i < arr.length + 1; i++)
        {
            double[] arrSlice = Arrays.copyOfRange(arr, i - MOVING_AVERAGE_WINDOW, i);
            avgArr[j++] = getAverage(arrSlice);
        }

        return avgArr;
    }

    public double getAverage(double[] arr)
    {
        double sum = 0;
        for (double val : arr)
            sum += val;

        return sum / arr.length;
    }

    public double stdDeviation(double[] arr)
    {
        double avg = getAverage(arr);
        double varianceN = 0;

        for (double x : arr)
        {
            varianceN += Math.pow(x - avg, 2);
        }

        double variance = varianceN / arr.length;
        return Math.sqrt(variance);
    }

}
