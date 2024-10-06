public class NumarComplex {

    private double re;
    private double im;

    public NumarComplex(double re_, double im_) {
        this.re = re_;
        this.im = im_;
    }

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
        return new NumarComplex(this.re / other.re, this.im / other.im);
        //not correct please revise
    }

    public NumarComplex conjugate() {
        return new NumarComplex(this.re, -this.im);
    }

    public static NumarComplex fromString(String string) {
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
        real = Double.parseDouble(splitNumber[0].trim());
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