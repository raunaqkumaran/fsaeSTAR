import star.base.neo.DoubleVector;
import star.base.report.Report;
import star.common.StarMacro;

import java.io.*;
import java.lang.Math;
import java.nio.charset.StandardCharsets;


public class exportReports extends StarMacro {

    public static final String LIFT_COEFFICIENT_MONITOR_PLOT_TXT = "Lift Coefficient Monitor Plot.txt";
    public static final String DRAG_COEFFICIENT_MONITOR_PLOT_TXT = "Drag Coefficient Monitor Plot.txt";
    public static final String FW_LIFT_COEFFICIENT_MONITOR_PLOT_TXT = "FW Lift Coefficient Monitor Plot.txt";
    public static final String RW_LIFT_COEFFICIENT_MONITOR_PLOT_TXT = "RW Lift Coefficient Monitor Plot.txt";
    public static final String SW_LIFT_COEFFICIENT_MONITOR_PLOT_TXT = "SW Lift Coefficient Monitor Plot.txt";
    public static final int MOVING_AVERAGE_WINDOW = 500;
    public static final int CONVERGENCE_SCORE_CUTOFF = 15;

    public void execute() {
        try {
            execute0();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void execute0() throws IOException {
        simComponents activeSim = new simComponents(getActiveSimulation());
        String path;

        // Set file path for reports
        path = activeSim.dir + activeSim.separator + activeSim.simName + activeSim.separator + "Reports";
        File repFolder = new File(resolvePath(path));

        // Create folder if it doesn't exist

        if (!repFolder.exists())
            repFolder.mkdirs();

        for (Report rep : activeSim.reports) {
            rep.printReport(resolvePath(path + activeSim.separator + rep.getPresentationName().replaceAll("[\\/]", "") + ".txt"), false);
        }

        //For some reason a reports table needs to sample a point. Here's a random point.
        activeSim.point.getPointCoordinate().setCoordinate(activeSim.activeSim.getUnitsManager().getObject("in"),activeSim.activeSim.getUnitsManager().getObject("in"),activeSim.activeSim.getUnitsManager().getObject("in"), new DoubleVector(new double[] {1, 1, 1}));
        activeSim.point.getInputParts().setObjects(activeSim.groundPlane);

        //Need to execute and extract the repTable before it can be exported to a reports.csv file. Also need to make sure it's set to sample the volume mesh.
        activeSim.repTable.setRepresentation(activeSim.finiteVol);
        activeSim.repTable.extract();
        activeSim.repTable.export(path + activeSim.separator + "Reports.csv");

        //postProc.java still handles plot exports. This calls the exportPlots function from there.
        postProc esObj = new postProc();
        esObj.exportPlots(activeSim);

        // Testing for convergence

        boolean convergesCl = convergence(path + activeSim.separator + LIFT_COEFFICIENT_MONITOR_PLOT_TXT);
        boolean convergesCd = convergence(path + activeSim.separator + DRAG_COEFFICIENT_MONITOR_PLOT_TXT);
        boolean convergesFW = convergence(path + activeSim.separator + FW_LIFT_COEFFICIENT_MONITOR_PLOT_TXT);
        boolean convergesRW = convergence(path + activeSim.separator + RW_LIFT_COEFFICIENT_MONITOR_PLOT_TXT);
        boolean convergesSW = convergence(path + activeSim.separator + SW_LIFT_COEFFICIENT_MONITOR_PLOT_TXT);

        printConvergence(convergesCl, convergesCd, convergesFW, convergesRW, convergesSW, path, activeSim);
    }

    public boolean convergence(String str) {

        /*
         * ---GENERAL PROGRAM DESCRIPTION---
         *
         * The goal of this program is to quantify convergence of CFD plot data. Ideally, the FSAE aerodynamics team
         * would like a program that can read data from a CFD simulation plot and determine if the values converge. Such
         * a program would let the team know how confident they can be in simulation accuracy. This program achieves
         * this goal by analyzing a moving average of the plot data.
         *
         * It reads numerical data from a .txt file of CFD results, storing them in an array for analysis. It then
         * calculates a moving average of the data, storing these values in another array. The standard deviation,
         * variance, and minimum/maximum values of this moving average data are found and used to calculate a score,
         * which determines the convergence of the data (lower score = better convergence). Importantly, the program
         * only uses the final 1000 iterations of the data to determine convergence, as earlier iterations often have
         * large variations, but still converge after many more iterations. Also worth noting is that if the moving
         * average min/max values are higher than a certain value (discounting the first 1000 iterations), the program
         * will display non-convergence, even if the final 1000 iterations appear to converge (as in test case 2).
         */

        int column = 1;
        int startIndex = 1000;
        int endIndex = 1000;

        double[][] zeroArray = clearArray();
        double[][] arrayTranspose = fillArray(zeroArray, str);
        assert arrayTranspose != null;
        double[][] array = transposeMatrix(arrayTranspose);

        double[] avgArr = getMovingAvg(array[column]);
        double variance = getVariance(array[column], startIndex);
        //double stDev = getStanDev(array[column], startIndex);
        double avgStDev = getStanDev(avgArr, arrLength(avgArr) - endIndex);
        //double diff = getMinMaxDiff(array[column], startIndex);
        double avgDiff1 = getMinMaxDiff(avgArr, arrLength(avgArr) - endIndex);
        double avgDiff2 = getMinMaxDiff(avgArr, startIndex);
        double score = cnvgAlgorithm(avgStDev, avgDiff1);

        return (testConvergence(avgDiff2, score));
    }

    public double[][] clearArray() {
        int numRow = 10_000;
        int numCol = 3;
        double[][] arr = new double[numRow][numCol];

        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                arr[i][j] = 0;
            }
        }

        return arr;
    }

    public double[][] fillArray(double[][] array, String fileName) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));

            String line = reader.readLine();

            int i = 0;
            int j = 0;
            while (line != null) {
                String[] strArray = line.split(",");

                if (!line.trim().isEmpty()) {
                    for (String s : strArray) {
                        if (s.trim().equals("\"Iteration\""))
                            break;
                        if (!s.trim().isEmpty()) {
                            array[i][j++] = Double.parseDouble(s);
                        }
                    }
                    line = reader.readLine();
                    i++;
                    j = 0;
                }
            }
            reader.close();
            return array;
        } catch (IOException ex) {
            System.out.println("Problems..");
        }
        return null;
    }

    public double[][] transposeMatrix(double[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        double[][] transposedMatrix = new double[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }

        return transposedMatrix;
    }

    public double[] getMovingAvg(double[] arr) {
        double[] avgArr = new double[arrLength(arr)];
        int meanLength = MOVING_AVERAGE_WINDOW;

        for (int i = 1; i < avgArr.length + 1; i++) {
            if (i < meanLength) {
                double sum = getSum(arr, 0, i);
                avgArr[i - 1] = sum / i;
            } else {
                double sum2 = getSum(arr, i - meanLength, i);
                avgArr[i - 1] = sum2 / meanLength;
            }
        }
        return avgArr;
    }

    public int arrLength(double[] arr) {
        int length = 0;

        for (double v : arr) {
            if (v != 0)
                length++;
        }

        return length;
    }

    public double getVariance(double[] arr, int start) {

        double mean = getSum(arr, start, arrLength(arr)) / (arrLength(arr) - start);
        double[] numerator = getNumerator(arr, mean, start, arrLength(arr));
        double sum = getSum(numerator, start, arrLength(arr));

        return (sum / arrLength(arr));
    }

    public double getStanDev(double[] arr, int start) {
        return (Math.sqrt(getVariance(arr, start)));
    }

    public double getSum(double[] arr, int start, int end) {
        double sum = 0;

        for (int i = start; i < end; i++)
            sum += arr[i];

        return (sum);
    }

    public double[] getNumerator(double[] arr, double mean, int start, int end) {
        double[] numerator = new double[arrLength(arr)];

        for (int i = start; i < end; i++)
            numerator[i] = Math.pow((arr[i] - mean), 2);

        return (numerator);
    }

    public double getMinMaxDiff(double[] arr, int index) {

        for (int i = 0; i < arrLength(arr); i++)
            arr[i] = Math.abs(arr[i]);

        double arrMin = arr[index];
        for (int i = index; i < arrLength(arr); i++) {
            if (arr[i] < arrMin)
                arrMin = arr[i];
        }

        double arrMax = arr[index];
        for (int j = index; j < arrLength(arr); j++) {
            if (arr[j] > arrMax)
                arrMax = arr[j];
        }

        return (((arrMax - arrMin) / arrMin) * 100);
    }

    public double cnvgAlgorithm(double stDev, double diff) {
        int scaleFactor = 2500;
        return (stDev * diff * scaleFactor);
    }

    public void printResults(double variance, double stDev, double avgStDev, double diff, double avgDiff1, double avgDiff2, double score) {

        //System.out.println("Variance: " + variance);
        //System.out.println("Standard deviation: " + stDev);
        //System.out.println("Moving avg standard deviation 100: " + avgStDev);
        //System.out.println("Min/max difference (%): " + diff);
        //System.out.println("Moving avg min/max difference (%, last 1000 iterations): " + avgDiff1);
        System.out.println("Moving avg min/max difference 500 (%, all iterations after 1000): " + avgDiff2);
        System.out.println("Convergence Score: " + score);

    }

    public boolean testConvergence(double avgDiff, double score) {
        return ((score <= CONVERGENCE_SCORE_CUTOFF) && (avgDiff < CONVERGENCE_SCORE_CUTOFF));
    }

    public void printConvergence(boolean convergesCl, boolean convergesCd, boolean convergesFW, boolean convergesRW, boolean convergesSW, String filePath, simComponents simObj) throws IOException {
        PrintWriter writer = new PrintWriter(filePath + simObj.separator + "Convergence.txt", StandardCharsets.UTF_8);
        if (convergesCl)
            writer.println("Lift Coefficient sim converges.");
        if (!convergesCl)
            writer.println("Lift Coefficient sim does not converge. Run more iterations.");
        if (convergesCd)
            writer.println("Drag Coefficient sim converges.");
        if (!convergesCd)
            writer.println("Drag Coefficient sim does not converge. Run more iterations.");
        if (convergesFW)
            writer.println("FW Lift Coefficient sim converges.");
        if (!convergesFW)
            writer.println("FW Lift Coefficient sim does not converge. Run more iterations.");
        if (convergesRW)
            writer.println("RW Lift Coefficient sim converges.");
        if (!convergesRW)
            writer.println("RW Lift Coefficient sim does not converge. Run more iterations.");
        if (convergesSW)
            writer.println("SW Lift Coefficient sim converges.");
        if (!convergesSW)
            writer.println("SW Lift Coefficient sim does not converge. Run more iterations.");

        writer.close();
    }

}

