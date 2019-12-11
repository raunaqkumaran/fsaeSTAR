import java.io.*;
import java.util.*;

public class base20_compile_reports {

    public static void main(String[] args) {

        try {
            File RepFile = new File("Reports.txt");
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(RepFile, false));
            outputWriter.close();
            File liftCoeff = new File("Lift Coefficient.txt");
            File dragCoeff = new File("Drag Coefficient.txt");
            File balance = new File("% Front.txt");
            File radFlow = new File("Radiator Mass Flow.txt");
            File fwLift = new File("FW Lift.txt");
            File fwDrag = new File("FW Drag.txt");
            File rwLift = new File("RW Lift.txt");
            File rwDrag = new File("RW Drag.txt");
            File utLift = new File("UT Lift.txt");
            File utDrag = new File("UT Drag.txt");
            File nsLift = new File("NS Lift.txt");
            File nsDrag = new File("NS Drag.txt");
            File ecLift = new File("EC Lift.txt");
            File ecDrag = new File("EC Drag.txt");
            File swLift = new File("SW Lift.txt");
            File swDrag = new File("SW Drag.txt");

            writerFullCar(liftCoeff, "Cl = ");
            writerFullCar(dragCoeff, "Cd = ");
            writerBalance(balance, "Balance = ");
            writerRadFlow(radFlow, "Rad mass flow = ");
            writerPartRes(fwLift, " Cl = ");
            writerPartRes(fwDrag, " Cd = ");
            writerPartRes(rwLift, " Cl = ");
            writerPartRes(rwDrag, " Cd = ");
            writerPartRes(utLift, " Cl = ");
            writerPartRes(utDrag, " Cd = ");
            writerPartRes(nsLift, " Cl = ");
            writerPartRes(nsDrag, " Cd = ");
            writerPartRes(ecLift, " Cl = ");
            writerPartRes(ecDrag, " Cd = ");
            writerPartRes(swLift, " Cl = ");
            writerPartRes(swDrag, " Cd = ");

        } catch (Exception IOException)

        {
            System.out.println("IOException");
        }
    }
    private static void writerFullCar(File rep, String Val)
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rep));
            String check = "Monitor value: ";
            int offset = check.length();
            String line = reader.readLine();
            while (!line.startsWith(check)) {
                line = reader.readLine();
            }
            String res = line.substring(offset);

            File reports = new File("Reports.txt");
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(reports, true));
            outputWriter.append(Val);
            outputWriter.append(res);
            outputWriter.newLine();
            outputWriter.close();
        }
        catch (Exception IOException)

        {
            System.out.println("IOException");
        }
    }
    private static void writerBalance(File rep, String Val)
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rep));
            String line = reader.readLine();
            String check = "${PitchMomentCoefficientReport}/${LiftCoefficientReport} =  ";
            int offset = check.length();
            while (!line.startsWith(check)) {
                line = reader.readLine();
            }
            String res = line.substring(offset);

            File reports = new File("Reports.txt");
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(reports, true));
            outputWriter.append(Val);
            outputWriter.append(res);
            outputWriter.newLine();
            outputWriter.close();
        }
        catch (Exception IOException)

        {
            System.out.println("IOException");
        }
    }
    private static void writerRadFlow(File rep, String Val)
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rep));
            String check = "                                                                                                                Total:    ";
            int offset = check.length();
            String line = reader.readLine();
            while (!line.startsWith(check)) {
                line = reader.readLine();
            }
            String res = line.substring(offset);

            File reports = new File("Reports.txt");
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(reports, true));
            outputWriter.append(Val);
            outputWriter.append(res);
            outputWriter.newLine();
            outputWriter.close();
        }
        catch (Exception IOException)

        {
            System.out.println("IOException");
        }
    }
    private static void writerPartRes(File rep, String Val)
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(rep));
            String check = "Monitor value: ";
            int offset = check.length();
            String partCheck = "Aero wrap (Non-aero wrap, wings, mounts and bodywork).";
            String partName = "Test";
            int partOffset = partCheck.length();
            int flag = 1;
            int finalIndex;
            String line = reader.readLine();
            while (!line.startsWith(check)) {
                if (line.startsWith(partCheck) && flag == 1)
                {
                    finalIndex = line.substring(partOffset).indexOf(" ");
                    partName = line.substring(partOffset, partOffset + finalIndex);
                    flag = 0;
                }
                line = reader.readLine();
            }
            String res = line.substring(offset);

            File reports = new File("Reports.txt");
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(reports, true));
            outputWriter.append(partName);
            outputWriter.append(Val);
            outputWriter.append(res);
            outputWriter.newLine();
            outputWriter.close();
        }
        catch (Exception IOException)

        {
            System.out.println("IOException");
        }
    }
}
