import java.util.ArrayList;
import java.util.Random;

public class Polynomial {
    private ArrayList<Complex> polynomial = new ArrayList<>();
    private ArrayList<Complex> roots = new ArrayList<>();
    private int loopCount = 0;
    Polynomial() {
        for (int i = 0; i < polynomial.size(); i = i + 1) {
            polynomial.remove(i);
        }
    }
    Polynomial (ArrayList<Complex> input) {
        clear();
        for (int i = 0; i < input.size(); i = i + 1) {
            set(i, input.get(i));
        }
    }
    public void set(ArrayList<Complex> input) {
        clear();
        for (int i = 0; i < input.size(); i = i + 1) {
            set(i, input.get(i));
        }
    }
    public void set (int degree, Complex coefficient) {
        if (polynomial.size() - 1 < degree) {
            for (int i = polynomial.size(); i < degree; i = i + 1) {
                polynomial.add(new Complex(0.0, 0.0));
            }
            polynomial.add(coefficient);
        }
        else {
            polynomial.set(degree, coefficient);
        }

    }
    public int size() {
        return(polynomial.size());
    }
    public Complex get(int degree) {
        return(polynomial.get(degree));
    }


    public void clear() {
        int polynomialSize = polynomial.size();
        for (int i = 0; i < polynomialSize; i = i + 1) {
            polynomial.remove(0);
        }
    }
    public String toString() {
        String result = "";
        for (int i = polynomial.size() - 1; i >= 0; i = i - 1) {
            if (!polynomial.get(i).isEmpty() && i == 1) {
                result += polynomial.get(i).print() + "x";
            }
            else if (!polynomial.get(i).isEmpty() && i == 0) {
                result += polynomial.get(i).print();
            }
            else if (!polynomial.get(i).isEmpty()) {
                result += polynomial.get(i).print() + "x^" + i;
            }
            if (i >= 1 && !polynomial.get(i - 1).isEmpty()) result += " + ";
        }
        return(result);
    }
    public void print() {
        System.out.println();
        for (int i = polynomial.size() - 1; i >= 0; i = i - 1) {
            if (!polynomial.get(i).isEmpty() && i == 1) {
                System.out.print(polynomial.get(i).print() + "x");
            }
            else if (!polynomial.get(i).isEmpty() && i == 0) {
                System.out.print(polynomial.get(i).print());
            }
            else if (!polynomial.get(i).isEmpty()) {
                System.out.print(polynomial.get(i).print() + "x^" + i);
            }
            if (i >= 1 && !polynomial.get(i - 1).isEmpty()) System.out.print(" + ");
        }
    }

    public Complex calculate(Complex input) {
        Complex result = new Complex(0.0, 0.0);
        for (int i = 0; i < polynomial.size(); i = i + 1) {
            Complex term = new Complex(1.0, 0.0);
            if (i != 0) for (int j = 0; j < i; j = j + 1) {
                term = Complex.multiply(term, input);
            }
            term = Complex.multiply(term, polynomial.get(i));
            result = Complex.add(result, term);
        }
        return (result);
    }

    public void transformDerivative() {

        for (int i = 0; i < polynomial.size(); i = i + 1) {
            polynomial.set(i, Complex.multiply(new Complex(Double.valueOf(i), 0.0), polynomial.get(i)));
        }
        ArrayList<Complex> temp = new ArrayList<Complex>();
        for (int i = 1; i < polynomial.size(); i = i + 1) {
            temp.add(polynomial.get(i));
        }
        polynomial.clear();
        for (int i = 0; i < temp.size(); i = i + 1) {
            polynomial.add(temp.get(i));
        }

    }

    private static int findRealDegree(Polynomial input) {
        int result = input.size() - 1;
        for (int i = input.size() - 1; i >= 0; i = i - 1) {
            if (input.get(i).getReal() != 0.0) {
                result = i;
                break;
            }
        }
        return(result);
    }
    private static int findImaginaryDegree(Polynomial input) {
        int result = input.size() - 1;
        for (int i = input.size() - 1; i >= 0; i = i - 1) {
            if (input.get(i).getImaginary() != 0.0) {
                result = i;
                break;
            }
        }
        return(result);
    }
    public static Double calculateRealBound(Polynomial input) {
        Double sum = 0.0;
        int highestDegree = findRealDegree(input);
        for (int i = 0; i < highestDegree - 1; i = i + 1) {
            sum = Math.abs(input.get(i).getReal()) / input.get(highestDegree).getReal() + sum;
        }
        if (sum >= 1.0) {
            return(sum);
        }
        else {
            return(1.0);
        }
    }
    public static Double calculateImaginaryBound(Polynomial input) {
        Double sum = 0.0;
        int highestDegree = findImaginaryDegree(input);
        for (int i = 0; i < highestDegree - 1; i = i + 1) {
            sum = Math.abs(input.get(i).getImaginary()) / input.get(highestDegree).getImaginary() + sum;
        }
        if (sum >= 1.0) {
            return(sum);
        }
        else {
            return(1.0);
        }
    }

    private ArrayList<Complex> points = new ArrayList<>();
    private ArrayList<Complex> newPoints = new ArrayList<>();

    private void updateAberth(int point) {
        Polynomial derivative = new Polynomial(); //copy the polynomial into a new variable
        for (int i = 0; i < polynomial.size(); i = i + 1) {
            derivative.set(i, polynomial.get(i));
        }
        derivative.transformDerivative(); //get the derivative of that polynomial
        Complex result = new Complex(0.0, 0.0);
        for (int i = 1; i <= polynomial.size() - 1; i = i + 1) {
            double testValue = points.get(i).getReal();
            if (i != point && testValue == testValue) {
                Complex temp = new Complex(0.0, 0.0);
                temp = Complex.subtract(points.get(point), points.get(i));
                temp = Complex.divide(new Complex(1.0, 0.0), temp);
                result = Complex.add(result, temp);
                temp = null;
            }
        } //get the sum for the aberth update method
        result = Complex.subtract(Complex.divide(derivative.calculate(points.get(point)), calculate(points.get(point))), result);
        result = Complex.divide(new Complex(1.0, 0.0), result);
        result = Complex.subtract(points.get(point), result); //complete the rest of the update function
        if (Math.getExponent(result.getReal()) <= -16) result = new Complex(0.0, result.getImaginary());
        if (Math.getExponent(result.getImaginary()) <= -16) result = new Complex(result.getReal(), 0.0);
        double testValue = result.getReal();
        if (testValue == testValue) newPoints.set(point, result);
    }
    private void setNew() {
        int length = newPoints.size();
        int length2 = points.size();
        for (int i = 0; i < length2; i = i + 1) {
            points.remove(0);
        }
        for (int i = 0; i < length; i = i + 1) {
            points.add(newPoints.get(i));
        }
    }
    public void printRoots() {
        System.out.println("\n" + loopCount + " steps with " + roots.size() + " roots found.");
        for (int i = 0; i < roots.size(); i = i + 1) {
            if (i != 0) System.out.print(" & ");
            System.out.print(roots.get(i));
        }
    }
    public Complex getRoots(int index) {
        return(roots.get(index));
    }
    private void saveRoots(double margin, int loopCount) {
        int length = roots.size();
        this.loopCount = loopCount;
        for (int i = 0; i < length; i = i + 1) {
            roots.remove(0);
        }
        for (int i = 0; i < points.size(); i = i + 1) {
            Complex result = calculate(points.get(i));
            double magnitude = result.getReal() * result.getReal() + result.getImaginary() * result.getImaginary();
            if (magnitude < margin * margin) roots.add(points.get(i));
        }
    }
    public void aberthRoot(double margin) {
        int degree1 = findRealDegree(this);
        int degree2 = findImaginaryDegree(this);
        if (degree1 >= degree2) aberthRoot(margin, degree1);
        else aberthRoot(margin, degree2);
    }
    public void aberthRoot(double margin, int expectedRoots) {
        double temp1 = calculateRealBound(this);
        double temp2 = calculateImaginaryBound(this);
        int numPoints = expectedRoots + 1;
        if (numPoints < 5) numPoints = 5;
        aberthRoot(numPoints, new Complex(0.0,0.0), Math.sqrt(temp1 * temp1 + temp2 * temp2), margin, expectedRoots, 100);
    }
    public void aberthRoot(int num, Complex center, double maxDist, double margin, int expectedRoots, int maxLoops) {
        generateInitialGuesses(num, center, maxDist);
        int rootCount = 0;
        int loopCount = 0;
        for (int i = 0; i < maxLoops && (rootCount < expectedRoots || expectedRoots <= 0); i = i + 1) {
            rootCount = updateAllAberth(margin);
            loopCount++;
        }
        saveRoots(margin, loopCount);

    }
    private int updateAllAberth(double margin) {
        int endAberth = 0;
        for (int i = 0; i < points.size(); i = i + 1) {
            updateAberth(i);
        }
        setNew();
        for (int i = 0; i < newPoints.size(); i = i + 1) {
            Complex result = calculate(points.get(i));
            if (result.getReal() * result.getReal() + result.getImaginary() * result.getImaginary() <= margin * margin) {
                endAberth++;
            }
        }
        return(endAberth);
    }
    private void generateInitialGuesses (int num, Complex center, double maxDist) {
        int length = points.size();
        for (int i = 0; i < length; i = i + 1) {
            points.remove(0);
            newPoints.remove(0);
        }
        Random rand = new Random();
        for (int i = 0; i < num; i = i + 1) {
            points.add(new Complex(rand.nextDouble(center.getReal() - maxDist, center.getReal() + maxDist), rand.nextDouble(center.getImaginary() - maxDist, center.getImaginary() + maxDist)));
            newPoints.add(new Complex(0.0,0.0));
        }

    }

}
