public class Numbers {

    public static void main(String[] args) {
        byte b = 100; //-128 to 127
        System.out.println("This is byte :" + b);

        short s = 5000; //-32768 to 32767
        System.out.println("This is short :" + s);

        int i = 100000; //-2147483648 to 2147483647
        System.out.println("This is integer :" + i);

        long l = 1000000000L; //-9223372036854775808 to 9223372036854775807
        System.out.println("This is Long integer :" + l);

        float f = 5.75f; //9.99 or 3.14515
        System.out.println("This is float number :" + f);

        double d = 8.5e3d;
        System.out.println("This is a double number :" + d);
    }
}
