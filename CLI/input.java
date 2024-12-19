package CLI;

import java.util.Scanner;

public class input {

    @SuppressWarnings("resource")
    public static void main(String[] args) {

        String nama;
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan Nama : ");
        nama = input.nextLine();
        System.out.println("Hallo, " + nama + "!");

    }
}