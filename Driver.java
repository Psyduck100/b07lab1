public class Driver {
    public static void main(String[] args) {
        // Test Addition
        double[] coeffs1 = {2, 3, -1}; // Represents 2x^2 + 3x - 1
        int[] exps1 = {2, 1, 0};
        Polynomial poly1 = new Polynomial(coeffs1, exps1);

        double[] coeffs2 = {1, 4}; // Represents 1x + 4
        int[] exps2 = {1, 0};
        Polynomial poly2 = new Polynomial(coeffs2, exps2);

        // Perform addition
        Polynomial sumResult = poly1.add(poly2);

        // Print the result of addition
        System.out.print("Result of Addition: ");
        for (int i = 0; i < sumResult.size; i++) {
            if (i > 0 && sumResult.coefficients[i] >= 0) {
                System.out.print("+");
            }
            System.out.print(sumResult.coefficients[i]);
            if (sumResult.exponents[i] != 0) {
                System.out.print("x");
                if (sumResult.exponents[i] != 1) {
                    System.out.print(sumResult.exponents[i]);
                }
            }
        }
        System.out.println();

        // Test Multiplication
        double[] coeffs3 = {2, 3}; // Represents 2x + 3
        int[] exps3 = {1, 0};
        Polynomial poly3 = new Polynomial(coeffs3, exps3);

        double[] coeffs4 = {4, 5}; // Represents 4x^2 + 5
        int[] exps4 = {2, 0};
        Polynomial poly4 = new Polynomial(coeffs4, exps4);

        // Perform multiplication
        Polynomial multiplyResult = poly3.multiply(poly4);

        // Print the result of multiplication
        System.out.print("Result of Multiplication: ");
        for (int i = 0; i < multiplyResult.size; i++) {
            if (i > 0 && multiplyResult.coefficients[i] >= 0) {
                System.out.print("+");
            }
            System.out.print(multiplyResult.coefficients[i]);
            if (multiplyResult.exponents[i] != 0) {
                System.out.print("x");
                if (multiplyResult.exponents[i] != 1) {
                    System.out.print(multiplyResult.exponents[i]);
                }
            }
        }
        System.out.println();
    }
}
