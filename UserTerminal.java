import java.util.ArrayList;
import java.util.Scanner;

public class UserTerminal {
    static Scanner scnr = new Scanner(System.in);

    static ArrayList<Polynomial> testP = new ArrayList<>();
    static Polynomial polynomial;
    static ArrayList<Matrix> testM = new ArrayList<>();
    static Matrix matrix;

    public static void main(String[] args) {
        initiliazeTestOptions();
        boolean programRunning = true;
        while (programRunning) {
            System.out.print("\nPolynomial (p) or matrix (m) or quit (q)? ");
            String choice = scnr.next().toLowerCase();
            if (choice.equals("p")) {
                chooseFromPolynomialOptions();
                System.out.print("Roots (r), calculate (c), or derive (d): ");
                choice = scnr.next().toLowerCase();
                switch (choice) {
                    case "r" -> {
                        polynomial.aberthRoot(Math.pow(2, -32));
                        polynomial.printRoots();
                    }
                    case "c" -> {
                        System.out.print("At value: ");
                        System.out.println("Result: " + polynomial.calculate(Complex.parseComplex(scnr.next())));
                    }
                    case "d" -> {
                        polynomial.transformDerivative();
                        System.out.println(polynomial);
                    }
                }
            }
            else if (choice.equals("m")) {
                double margin = Math.pow(2, -32);
                chooseFromMatrixOptions();
                System.out.print("Eigenvalues (e), rref (r), determinant (d), inverse (i), similar Hessenberg (h): ");
                choice = scnr.next().toLowerCase();
                switch (choice) {
                    case "e" -> {
                        ArrayList<Complex> eigen = matrix.QRAlgorithm(margin, 100);
                        System.out.println("Eigenvalues:\n" + eigen);
                    }
                    case "r" -> {
                        Matrix rref = matrix.clone();
                        rref.rref(margin);
                        System.out.println("RREF:\n" + rref);
                    }
                    case "d" -> {
                        System.out.println("Determinant: " + matrix.determinant());
                    }
                    case "i" -> {
                        System.out.println("Inverse Matrix:\n" + matrix.inverseMatrix());
                    }
                    case "h" -> {
                        if (matrix.getNumRows() == matrix.getNumColumns()) {
                            System.out.println("Similar upper Hessenberg form matrix:\n" + matrix.hessenbergForm());
                        }
                        else {
                            System.out.println("Must be a square matrix.");
                        }
                    }
                }
            }
            else if (choice.equals("q")) {
                programRunning = false;
            }
            else {
                System.out.println("Invalid answer. Try again.");
            }
        }
    }

    public static void initiliazeTestOptions() {
        testP.clear();
        Polynomial p = new Polynomial();
        p.set(4, new Complex(1.0, 0.0));
        p.set(0, new Complex(-1.0, 0.0));
        testP.add(p);
        p = new Polynomial();
        p.set(3, new Complex(1.0 / 3.0, 0.0));
        p.set(2, new Complex(1.0 / 2.0, 0.0));
        p.set(1, new Complex(1.0, 0.0));
        p.set(0, new Complex(0.0, 0.0));
        testP.add(p);
        p = new Polynomial();
        p.set(6, new Complex(5.0, 0.0));
        p.set(5, new Complex(3.0, 0.0));
        p.set(2, new Complex(2.0, -1.0));
        p.set(1, new Complex(1.0, 0.0));
        p.set(0, new Complex(-1.0, 0.0));
        testP.add(p);
        p = new Polynomial();
        p.set(4, new Complex(2.0, 0.0));
        p.set(3, new Complex(7.0, 0.0));
        p.set(2, new Complex(11.0, 0.0));
        p.set(1, new Complex(28.0, 0.0));
        p.set(0, new Complex(12.0, 0.0));
        testP.add(p);

        testM.clear();
        Matrix m = new Matrix(3, 4);
        m.set(0,0, new Complex(-3.0, 0.0));
        m.set(0,1, new Complex(1.0, 0.0));
        m.set(0,2, new Complex(-9.0, 0.0));
        m.set(0,3, new Complex(12.0, 0.0));
        m.set(1,0, new Complex(13.0, 0.0));
        m.set(1,1, new Complex(6.0, 0.0));
        m.set(1,2, new Complex(-17.0, 0.0));
        m.set(1,3, new Complex(8.0, 0.0));
        m.set(2,0, new Complex(-2.4, 0.0));
        m.set(2,1, new Complex(2.0, 0.0));
        m.set(2,2, new Complex(1.0, 0.0));
        m.set(2,3, new Complex(-2.0, 0.0));
        testM.add(m);

        m = new Matrix(5, 5);
        m.set(0,0, new Complex(-3.5, 0.0));
        m.set(0,1, new Complex(1.0, 0.0));
        m.set(0,2, new Complex(-1.0, 0.0));
        m.set(0,3, new Complex(1.0, 0.0));
        m.set(0,4, new Complex(-2.0, 1.0));
        m.set(1,0, new Complex(16.0, 0.0));
        m.set(1,1, new Complex(3.0, 0.0));
        m.set(1,2, new Complex(7.0, 0.0));
        m.set(1,3, new Complex(3.0, 2.0));
        m.set(1,4, new Complex(-2.0, 0.0));
        m.set(2,0, new Complex(-2.4, 0.0));
        m.set(2,1, new Complex(2.0, 0.0));
        m.set(2,2, new Complex(1.0, 0.0));
        m.set(2,3, new Complex(9.0, 0.0));
        m.set(2,4, new Complex(-9.0, 0.0));
        m.set(3,0, new Complex(-2.4, 0.0));
        m.set(3,1, new Complex(2.0, 0.0));
        m.set(3,2, new Complex(2.3, 0.0));
        m.set(3,3, new Complex(-1.0, 0.0));
        m.set(3,4, new Complex(3.0, -2.5));
        m.set(4,0, new Complex(-0.4, 0.0));
        m.set(4,1, new Complex(0.1, 1.0));
        m.set(4,2, new Complex(1.0, 0.0));
        m.set(4,3, new Complex(1.0, 0.0));
        m.set(4,4, new Complex(-2.0, 0.0));
        testM.add(m);
        m = new Matrix(2,2);
        m.set(0,0, new Complex(0.0, Math.sqrt(2.0)));
        m.set(0,1, new Complex(Math.sqrt(3.0), 0.0));
        m.set(1,0, new Complex(Math.sqrt(3.0), 0.0));
        m.set(1,1, new Complex(0.0, -Math.sqrt(2.0)));
        testM.add(m);
    }

    public static void chooseFromPolynomialOptions() {
        int i = 0;
        for (Polynomial p : testP) {
            System.out.println("(" + i + "):  " + p);
            i++;
        }
        System.out.print("Select one by number (invalid answer to create a new one): ");
        String choice = scnr.next();
        try {
            polynomial = testP.get(Integer.parseInt(choice));
        }
        catch (Exception e) {
            polynomial = new Polynomial();
            System.out.print("Degree of polynomial: ");
            int degree = getIntResponse();
            for (i = degree; i >= 0; i--) {
                System.out.print("Coefficient of degree " + i + ": ");
                String response = scnr.next();
                polynomial.set(i, Complex.parseComplex(response));
                System.out.println();
            }
        }
        System.out.println("Polynomial " + polynomial + "\nis selected.");
    }
    public static void chooseFromMatrixOptions() {
        int i = 0;
        for (Matrix m : testM) {
            System.out.println("(" + i + "):\n" + m);
            i++;
        }
        System.out.print("Select one by number (invalid answer to randomize a new one): ");
        String choice = scnr.next();
        try {
            matrix = testM.get(Integer.parseInt(choice));
        }
        catch (Exception e) {
            System.out.print("What number of rows (0-20 for best results)? ");
            int numRows = getIntResponse();
            System.out.print("What number of columns (0-20 for best results)? ");
            int numColumns = getIntResponse();
            matrix = new Matrix(numRows, numColumns);
            for (i = 0; i < numRows; i++) {
                for (int j = 0; j < numColumns; j++) {
                    matrix.set(i, j, new Complex(Math.round(Math.random() * 20.0 - 10.0), Math.round(Math.random() * 20.0 - 10.0)));
                }
            }
        }
        System.out.println("Matrix\n" + matrix + "is selected.");
    }
    public static int getIntResponse() {
        while (true) {
            try {
                return Integer.parseInt(scnr.next());
            } catch (Exception e) {
                System.out.println("Invalid response. Try again.");
            }
        }
    }
}
