import com.klg.jclass.table.TableDataModel;
import org.jfree.data.statistics.Statistics;
import star.common.MonitorPlot;
import star.common.StarMacro;
import star.common.StarPlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class convergenceChecker extends StarMacro {

    public static final int COLUMN = 1;
    public static final int MOVING_AVERAGE_WINDOW = 500;
    public static final int CONVERGENCE_SCORE_CUTOFF = 15;
    public static final int START_INDEX = 1000;

    public void execute()
    {
        simComponents activeSim = new simComponents(getActiveSimulation());
        HashMap<String, Boolean> convergenceResults = new HashMap<>();

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

            if (movingAverageArray == null || START_INDEX > movingAverageArray.length)
            {
                convergenceResults.put(plt.getPresentationName(), false);
                continue;
            }

            double[] averageSlice = Arrays.copyOfRange(movingAverageArray, START_INDEX, movingAverageArray.length);
            double averageStdDev = stdDeviation(averageSlice);



            activeSim.activeSim.print("");
        }
        activeSim.activeSim.println("Complete");
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
        double stdDev = Math.sqrt(variance);
        return stdDev;
    }

}
