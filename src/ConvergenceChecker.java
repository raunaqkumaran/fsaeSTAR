import com.klg.jclass.table.TableDataModel;
import star.common.MonitorPlot;
import star.common.StarPlot;

import java.util.Arrays;
import java.util.HashMap;

public class ConvergenceChecker {

    public static final int COLUMN = 1;
    public static final int MOVING_AVERAGE_WINDOW = 500;
    public static final int CONVERGENCE_SCORE_CUTOFF = 15;
    public static final int START_INDEX = 1000;
    public static final int END_INDEX = 1000;
    public HashMap<String, Boolean> convergenceResults;

    public ConvergenceChecker(SimComponents activeSim)
    {
        convergenceResults = new HashMap<>();

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

            if (movingAverageArray == null || START_INDEX > movingAverageArray.length || END_INDEX > movingAverageArray.length)
            {
                convergenceResults.put(plt.getPresentationName(), false);
                continue;
            }

            double[] averageSlice = Arrays.copyOfRange(movingAverageArray, START_INDEX, movingAverageArray.length);
            double[] latestSlice = Arrays.copyOfRange(movingAverageArray, movingAverageArray.length - END_INDEX, movingAverageArray.length);
            double averageStdDev = stdDeviation(averageSlice);
            double max = getMaximum(latestSlice);
            double min = getMinimum(latestSlice);
            double percentageRange = Math.abs(((max - min) / min) * 100);
            double score = cnvgAlgorithm(averageStdDev, percentageRange);

            if (score < CONVERGENCE_SCORE_CUTOFF)
                convergenceResults.put(plt.getPresentationName() + " (" + score + ")", true);
            else
                convergenceResults.put(plt.getPresentationName() + " (" + score + ")", false);

            activeSim.activeSim.print("");
        }
        activeSim.activeSim.println("Complete");
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
