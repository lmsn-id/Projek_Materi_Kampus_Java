package CLI;

import java.util.Scanner;

public class FormBMI {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double beratstandar = 18.5, berat, tinggiCm, tinggiM, bmi;

        System.out.print("Masukkan berat badan anda (kg): ");
        berat = input.nextDouble();

        System.out.print("Masukkan tinggi badan anda (cm): ");
        tinggiCm = input.nextDouble();

        tinggiM = tinggiCm / 100;

        bmi = berat / (tinggiM * tinggiM);

        System.out.printf("Nilai BMI anda adalah %.4f%n", bmi);

        if (bmi < beratstandar) {
            System.out.println("Anda termasuk berbadan kurus");
        } else if (bmi >= beratstandar && bmi < 25) {
            System.out.println("Anda termasuk berbadan langsing");
        } else {
            System.out.println("Anda termasuk berbadan gemuk");
        }
        input.close();
    }
}