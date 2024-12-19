package CLI;

import java.util.Scanner;

public class looping {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int angkaBawah, angkaAtas;

        System.out.print("Masukkan angka batas bawah: ");
        angkaBawah = scanner.nextInt();

        System.out.print("Masukkan angka batas atas: ");
        angkaAtas = scanner.nextInt();

        System.out.println("Bilangan prima antara " + angkaBawah + " dan " + angkaAtas + " adalah:");
        System.out.println("---------------------------------------------");
        int i = angkaBawah;
        do {
            boolean prima = true;

            if (i < 2) {
                prima = false;
            } else {
                for (int j = 2; j <= Math.sqrt(i); j++) {
                    if (i % j == 0) {
                        prima = false;
                        break;
                    }
                }
            }
            if (prima) {
                System.out.print(i + ", ");
            }

            i++;
        } while (i <= angkaAtas);

        scanner.close();
    }
}