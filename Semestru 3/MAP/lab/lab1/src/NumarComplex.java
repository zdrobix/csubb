public class NumarComplex {

    private double re; //partea reala
    private double im; //partea imaginara

    public NumarComplex(double re_, double im_) {
        //Constructorul clasei
        this.re = re_;
        this.im = im_;
    }

    public double getRe() {return this.re;}
    public double getIm() {return this.im;}

    public NumarComplex add (NumarComplex other) {
        return new NumarComplex(this.re + other.re, this.im + other.im);
    }

    public NumarComplex subtract (NumarComplex other) {
        return new NumarComplex(this.re - other.re, this.im - other.im);
    }

    public NumarComplex multiply (NumarComplex other) {
        return new NumarComplex(this.re * other.re - this.im * other.im, this.re * other.im + this.im * other.re);
    }

    public NumarComplex divide (NumarComplex other) {
        return new NumarComplex((this.re * other.re + this.im * other.im)/(other.re*other.re + other.im*other.im),
                (this.im * other.re - this.re*other.im)/(other.re*other.re + other.im*other.im));
    }

    public NumarComplex conjugate() {
        return new NumarComplex(this.re, -this.im);
    }

    public static NumarComplex fromString(String string) {
        //Primeste un string ca parametru si returneaza un numar complex.
        boolean isNegativeReal = string.charAt(0) == '-';
        boolean isNegativeIm = false;
        string = string.trim().substring(1);
        String splitNumber[] = new String[2];
        double real, imaginary;
        if (string.contains("-")) {
            splitNumber = string.split("\\-");
            isNegativeIm = true;
        } else if (string.contains("+")) {
            splitNumber = string.split("\\+");
        }
        if (splitNumber[0].trim().endsWith(" +"))
            real = Double.parseDouble(splitNumber[0].trim().substring(0, splitNumber[0].trim().length() - 2));
        else real = Double.parseDouble(splitNumber[0].trim());
        imaginary = Double.parseDouble(splitNumber[1].trim().substring(0, splitNumber[1].trim().length() - 3));

        if (isNegativeIm) {
            imaginary = -imaginary;
        }
        if (isNegativeReal) {
            real = -real;
        }
        return new NumarComplex(real, imaginary);
    }

    public String toString() {
        if ( this.re < 0 )
            return this.re + " + " + this.im + " * i ";
        return "+ " + this.re + " + " + this.im + " * i ";
    }
}