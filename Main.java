import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        while (scnr.next().toLowerCase().contains("y")) {
            int n = Integer.parseInt(scnr.next());
            writeMatrix(n, n, false);

            double margin = Math.pow(2, -32);
            Matrix matrix = readMatrix(n, n).subMatrix(0, n, 0, n);
            assert matrix != null;
            System.out.println(matrix);
            long time1 = System.currentTimeMillis();
            ArrayList<Complex> eigen = matrix.QRAlgorithm(margin, 100);
            long time2 = System.currentTimeMillis();
            System.out.println((time2 - time1) + " milliseconds");
            System.out.println("Eigenvalues: " + eigen);
            Complex sum = new Complex(0.0, 0.0);
            Complex prod = new Complex(1.0, 0.0);
            for (Complex eigenvalue : eigen) {
                sum = Complex.add(sum, eigenvalue);
                prod = Complex.multiply(prod, eigenvalue);
            }
            Complex trace = new Complex(0.0, 0.0);
            for (int k = 0; k < n; k++) {
                trace = Complex.add(matrix.get(k, k), trace);
            }
            System.out.println("Trace " + trace + " vs sum " + sum);
            System.out.println("Determinant " + matrix.determinant() + " vs product " + prod);
        }
    }

    public static void writeMatrix(int rows, int columns, boolean print) {
        try {
            Matrix matrix = new Matrix(rows, columns);
            for (int i = 0; i < matrix.getNumRows(); i++) {
                for (int j = 0; j < matrix.getNumColumns(); j++) {
                    matrix.set(i, j, new Complex((double) Math.round(Math.random() * 10.0), (double) Math.round(Math.random() * 10.0)));
                }
            }
            if (print) matrix.print();
            File file = new File("TestMatrix1.txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(matrix.toString());
            fileWriter.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public static Matrix readMatrix(int numRows, int numColumns) {
        try {
            File file = new File("TestMatrix1.txt");
            FileReader fileReader = new FileReader(file);
            StringBuilder numBuilder = new StringBuilder();
            int result = fileReader.read();
            ArrayList<double[]> entries = new ArrayList<>();
            entries.add(new double[]{0.0, 0.0});
            while (result != -1) {
                char ch = (char) result;
                if (ch == '-' || ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9' || ch == '.' || ch == 'i') {
                    numBuilder.append(ch);
                }
                else {
                    if (!numBuilder.isEmpty()) {
                        String num = new String(numBuilder);
                        if (num.endsWith("i")) {
                            num = num.substring(0, num.length() - 1);
                            double value = Double.parseDouble(num);
                            entries.getLast()[1] += value;
                        }
                        else {
                            double value = Double.parseDouble(num);
                            entries.getLast()[0] += value;
                        }
                    }
                    numBuilder.delete(0, numBuilder.length());
                }
                if (result == ',' || result == ']') {
                    entries.add(new double[]{0.0, 0.0});
                }

                result = fileReader.read();
            }


            Matrix matrix = new Matrix(numRows, numColumns);
            for (int i = 0; i < entries.size(); i++) {
                if (i < numRows * numColumns) {
                    matrix.set(i / numColumns, i % numColumns, new Complex(entries.get(i)[0], entries.get(i)[1]));
                }
            }
            return matrix;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    public static boolean verifyIfOrthogonal(Matrix Q, boolean print) {
        boolean verified = true;
        double margin = Math.pow(2.0,-16.0);
        for (int i = 0; i < Q.getNumRows(); i++) {
            Complex magnitude = new Complex(0.0, 0.0);
            for (Complex num : Q.getRow(i)) {
                magnitude = Complex.add(Complex.multiply(num, num), magnitude);
            }
            if (print) System.out.println("Row " + i + " magnitude: " + Complex.sqrt(magnitude));
            if (Complex.subtract(magnitude, new Complex(1.0, 0.0)).magnitude() > margin) {
                if (print) System.out.println("FAIL");
                verified = false;
            }
            for (int k = 0; k < Q.getNumRows(); k++) {
                if (k != i) {
                    Complex dot = new Complex(0.0,0.0);
                    for (int h = 0; h < Q.getNumColumns(); h++) {
                        dot = Complex.add(Complex.multiply(Q.get(i, h), Q.get(k, h)), dot);
                    }
                    if (print) System.out.println("Dot between rows " + i + " and " + k + ": " + dot);
                    if (dot.magnitude() > margin) {
                        if (print) System.out.println("FAIL");
                        verified = false;
                    }
                }
            }
        }
        for (int j = 0; j < Q.getNumColumns(); j++) {
            Complex magnitude = new Complex(0.0, 0.0);
            for (Complex num : Q.getColumn(j)) {
                magnitude = Complex.add(Complex.multiply(num, num), magnitude);
            }
            if (print) System.out.println("Column " + j + " magnitude: " + Complex.sqrt(magnitude));
            if (Complex.subtract(magnitude, new Complex(1.0, 0.0)).magnitude() > margin) {
                if (print) System.out.println("FAIL");
                verified = false;
            }
            for (int k = 0; k < Q.getNumColumns(); k++) {
                if (k != j) {
                    Complex dot = new Complex(0.0,0.0);
                    for (int h = 0; h < Q.getNumRows(); h++) {
                        dot = Complex.add(Complex.multiply(Q.get(h, j), Q.get(h, k)), dot);
                    }
                    if (print) System.out.println("Dot between columns " + j + " and " + k + ": " + dot);
                    if (dot.magnitude() > margin) {
                        if (print) System.out.println("FAIL");
                        verified = false;
                    }
                }
            }
        }
        System.out.println((verified ? "PASS" : "FAIL"));
        return verified;
    }
}
