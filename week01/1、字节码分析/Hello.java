public class Hello {
    public static void main(String[] args) {
        int numA = 6;
        int numB = 8;

        for(int i = 0; i < numB; i++){
            if(numA < numB){
                numA++;
            }
        }

        int resultPlus = numA + numB;
        System.out.println("resultPlus = " + resultPlus);

        int resultSubtract = numA - numB;
        System.out.println("resultSubtract = " + resultSubtract);

        int resultMultiply = numA * numB;
        System.out.println("resultMultiply = " + resultMultiply);

        int resultDivide = numA / numB;
        System.out.println("resultDivide = " + resultDivide);
    }
}
