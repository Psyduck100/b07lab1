import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Polynomial {
    public double[] coefficients;
    public int[] exponents;
    public int size;

    public Polynomial(int[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
        this.size = coefficients.length;
        sortPolynomial(); 
    }

    public Polynomial() {
        this.coefficients = {0};
        this.exponents {0};
        this.size = 0;
    }

    public Polynomial(File file) {
        Scanner scanner = new Scanner(file);
        String polynomialString = scanner.nextLine();
        scanner.close();
        
   
        String[] terms = polynomialString.split("(?=[+-])"); // 
        int numTerms = terms.length;
        coefficients = new double[numTerms];
        exponents = new int[numTerms];
        size = 0; 

        for (String term : terms) {
            double coefficient = 1.0;
            int exponent = 0;

            if (term.contains("x")) {
                String[] parts = term.split("x");
                if (parts[0].isEmpty() || parts[0].equals("+")) {
                    coefficient = 1.0;
                } else if (parts[0].equals("-")) {
                    coefficient = -1.0;
                } else {
                    coefficient = Double.parseDouble(parts[0]);
                }

                if (parts.length > 1 && !parts[1].isEmpty()) {
                    exponent = Integer.parseInt(parts[1]);
                } else {
                    exponent = 1; 
                }
            } else {
                coefficient = Double.parseDouble(term);
                exponent = 0;
            }

            coefficients[size] = coefficient;
            exponents[size] = exponent;
            size++;
        }

        double[] tempCoefficients = new double[size];
        int[] tempExponents = new int[size];
        for (int i = 0; i < size; i++) {
            tempCoefficients[i] = coefficients[i];
            tempExponents[i] = exponents[i];
        }
        coefficients = tempCoefficients;
        exponents = tempExponents;
    }
    
    // Sort poly before merging
    public void sortPolynomial() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (exponents[j] > exponents[j + 1]) {
                    // Swap coeff
                    double tempCoef = coefficients[j];
                    coefficients[j] = coefficients[j + 1];
                    coefficients[j + 1] = tempCoef;

                    // Swap exp
                    int tempExpo = exponents[j];
                    exponents[j] = exponents[j + 1];
                    exponents[j + 1] = tempExpo;
                }
            }
        }
    }

    // Merge two poly
    public Polynomial add(Polynomial other) {
        int i = 0, j = 0, k = 0;
        double[] mergedCoefficients = new double[this.size + other.size];
        int[] mergedExponents = new int[this.size + other.size];

        while (i < this.size && j < other.size) {
            if (this.exponents[i] == other.exponents[j]) {
                // Equal exp add em
                
                if (this.coefficients[i] + other.coefficients[j] != 0){ // Check for not 0 
                    mergedExponents[k] = this.exponents[i];
                    mergedCoefficients[k] = this.coefficients[i] + other.coefficients[j]; 
                    k++;
                }
                
                i++;
                j++;
            } else if (this.exponents[i] < other.exponents[j]) {
                // If this exp smaller
                mergedExponents[k] = this.exponents[i];
                mergedCoefficients[k] = this.coefficients[i];
                i++;
                k++;
            } else {
                // If other exp smaller
                mergedExponents[k] = other.exponents[j];
                mergedCoefficients[k] = other.coefficients[j];
                j++;
                k++;
            }
        }


        // Other leftovers         
        while (i < this.size) {
            mergedExponents[k] = this.exponents[i];
            mergedCoefficients[k] = this.coefficients[i];
            i++;
            k++;
        }

        // Other leftovers 
        while (j < other.size) {
            mergedExponents[k] = other.exponents[j];
            mergedCoefficients[k] = other.coefficients[j];
            j++;
            k++;
        }

        // Resize final arr
        double[] finalCoefficients = new double[k];
        int[] finalExponents = new int[k];
        for (int l = 0; l < k; l++) {
            finalCoefficients[l] = mergedCoefficients[l];
            finalExponents[l] = mergedExponents[l];
        }

        return new Polynomial(finalCoefficients, finalExponents);
    }

    public Polynomial multiply(Polynomial other) {
        int maxExponent = this.exponents[this.size - 1] + other.exponents[other.size - 1];
        double[] resultCoefficients = new double[maxExponent + 1];

        // Mult the poly
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < other.size; j++) {
                int newExponent = this.exponents[i] + other.exponents[j];
                double newCoefficient = this.coefficients[i] * other.coefficients[j];
                resultCoefficients[newExponent] += newCoefficient; //store to index based on exp
            }
        }

        // count non zero coeff
        int nonZeroCount = 0;
        for (double coeff : resultCoefficients) {
            if (coeff != 0) {
                nonZeroCount++;
            }
        }

        // new right sized arr
        double[] finalCoefficients = new double[nonZeroCount];
        int[] finalExponents = new int[nonZeroCount];
        int index = 0;

        //copy over values
        for (int exp = 0; exp <= maxExponent; exp++) {
            if (resultCoefficients[exp] != 0) {
                finalCoefficients[index] = resultCoefficients[exp];
                finalExponents[index] = exp;
                index++;
            }
        }

        return new Polynomial(finalCoefficients, finalExponents);
}


    public void saveToFile(String filename) {
        PrintWriter writer = new PrintWriter(filename);  

        for (int i = 0; i < size; i++) {
            if (i > 0) { 
                if (coefficients[i] >= 0) {
                    writer.print("+");  
                }
            }

            writer.print(coefficients[i]);

            if (exponents[i] != 0) {
                writer.print("x");  
                if (exponents[i] != 1) {
                    writer.print(exponents[i]);  
                }
            }
        }
        writer.close(); 
    }

    public double evaluate(double x) {
        double result = 0.0;

        for (int i = 0; i < size; i++) {
            double termValue = coefficients[i] * Math.pow(x, exponents[i]);
            result += termValue; 
        }

        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

}
