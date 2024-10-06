public class ValidateExpression {
    //Clasa de validator pentru un sir de String.
    public static void Validate(String args[], int argc) {
        //Numarul de parametri trebuie sa fie impar.
        //Nu pot exista operatori unul dupa celalalt.
        //Numerele complexe sa fie de forma +/- a +/- b * i
        if (argc % 2 == 0)
            throw new RuntimeException("Invalid syntax.");
        for (int i = 0; i < argc; i++) {
            if (i % 2 == 1) {
                if (!(args[i].equals("-") || args[i].equals("+") ||  args[i].equals("*") || args[i].equals("/"))) {
                    throw new RuntimeException("Invalid operator.");
                }
            }
            if (i % 2 == 0) {
                if (args[i].charAt(0) != '-' && args[i].charAt(0) != '+')
                    throw new RuntimeException("Invalid sign for complex number: " + args[i]);
                String complexNumber = args[i].trim().substring(1);         //ignoram semnul partii reale
                String splitNumber[];
                if (complexNumber.contains("-")) {
                    splitNumber = complexNumber.split("\\-");
                } else if (complexNumber.contains("+")) {
                    splitNumber = complexNumber.split("\\+");
                } else {throw new RuntimeException("Invalid syntax for complex number: " + args[i]);}

                if (splitNumber.length != 2)
                    throw new RuntimeException("Invalid syntax for complex number: " + args[i]);

                if (!splitNumber[1].endsWith("* i") && !splitNumber[1].endsWith("*i"))
                    throw new RuntimeException("Invalid syntax for complex number: " + args[i]);
            }
        }
    }
}