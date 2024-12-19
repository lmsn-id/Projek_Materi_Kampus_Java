package CLI;

import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class percabangan {

    public static void main(String[] args) {

        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        Locale localeID = Locale.forLanguageTag("id-ID");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeID);

        String nama, duamatkul, bonus = "", Jurusan = "", kls = "", Jurusan2 = "", kls2 = "";
        int Pilihan, PilihanKedua, bayar, kembali, biaya = 0, biayaKelas2 = 0, totalBiaya;

        System.out.print("\sSelamat Form Pendaftaran SOTN\n");
        System.out.print("\s=============================\n");
        System.out.print("\sMasukkan Nama : ");
        nama = input.nextLine();
        System.out.print("\n \sSilahkan Pilih Jurusan Dan Kelas\n");
        System.out.print("\s================================================================\n");
        System.out.print("\s1. Jurusan Ekonomi - Kelas Pagi           - Biaya Rp. 1.750.000 \n");
        System.out.print("\s2. Jurusan Ekonomi - Kelas Malam          - Biaya Rp. 2.000.000 \n");
        System.out.print("\s3. Jurusan Sastra Indonesia - Kelas Pagi  - Biaya Rp. 1.900.000 \n");
        System.out.print("\s4. Jurusan Sastra Indonesia - Kelas Malam - Biaya Rp. 2.300.000 \n");
        System.out.print("\s================================================================\n");
        System.out.print("\n \sMasukkan Pilihan Anda (1-4): ");
        Pilihan = input.nextInt();
        input.nextLine();

        if (Pilihan == 1) {
            Jurusan = "Ekonomi";
            kls = "Pagi";
            biaya = 1750000;
        } else if (Pilihan == 2) {
            Jurusan = "Ekonomi";
            kls = "Malam";
            biaya = 2000000;
        } else if (Pilihan == 3) {
            Jurusan = "Sastra Indonesia";
            kls = "Pagi";
            biaya = 1900000;
        } else if (Pilihan == 4) {
            Jurusan = "Sastra Indonesia";
            kls = "Malam";
            biaya = 2300000;
        } else {
            System.out.print("\sPilihan Tidak Ditemukan\n");
            return;
        }

        System.out.print("\sApakah Anda Ingin Memilih 2 Kelas [Y/N]: ");
        duamatkul = input.nextLine();

        if (duamatkul.equalsIgnoreCase("Y")) {

            if (Jurusan.equals("Ekonomi")) {
                System.out.print("\n \sPilih Kelas Kedua (Hanya Jurusan Sastra Indonesia)\n");
                System.out.print("\s3. Jurusan Sastra Indonesia - Kelas Pagi  - Biaya Rp. 1.900.000 \n");
                System.out.print("\s4. Jurusan Sastra Indonesia - Kelas Malam - Biaya Rp. 2.300.000 \n");
                System.out.print("\n \sMasukkan Pilihan Kelas Kedua (3-4): ");
                PilihanKedua = input.nextInt();
                input.nextLine();

                if (PilihanKedua == 3) {
                    Jurusan2 = "Sastra Indonesia";
                    kls2 = "Pagi";
                    biayaKelas2 = 1900000;
                } else if (PilihanKedua == 4) {
                    Jurusan2 = "Sastra Indonesia";
                    kls2 = "Malam";
                    biayaKelas2 = 2300000;
                } else {
                    System.out.print("\sPilihan Kelas Kedua Tidak Ditemukan\n");
                    return;
                }

            } else if (Jurusan.equals("Sastra Indonesia")) {
                System.out.print("\n \sPilih Kelas Kedua (Hanya Jurusan Ekonomi)\n");
                System.out.print("\s1. Jurusan Ekonomi - Kelas Pagi           - Biaya Rp. 1.750.000 \n");
                System.out.print("\s2. Jurusan Ekonomi - Kelas Malam          - Biaya Rp. 2.000.000 \n");
                System.out.print("\n \sMasukkan Pilihan Kelas Kedua (1-2): ");
                PilihanKedua = input.nextInt();
                input.nextLine();

                if (PilihanKedua == 1) {
                    Jurusan2 = "Ekonomi";
                    kls2 = "Pagi";
                    biayaKelas2 = 1750000;
                } else if (PilihanKedua == 2) {
                    Jurusan2 = "Ekonomi";
                    kls2 = "Malam";
                    biayaKelas2 = 2000000;
                } else {
                    System.out.print("\sPilihan Kelas Kedua Tidak Ditemukan\n");
                    return;
                }
            }

            bonus = "Payung";
        } else if (duamatkul.equalsIgnoreCase("N")) {
            bonus = "Tidak ada bonus";
        } else {
            System.out.print("\sPilihan Tidak Ditemukan\n");
            return;
        }

        totalBiaya = biaya + biayaKelas2;

        System.out.print("\n \sData Pendaftaran\n");
        System.out.print("\s================================================\n");
        System.out.print("\sNama              : " + nama + "\n");
        System.out.print("\sJurusan 1         : " + Jurusan + "\n");
        System.out.print("\sKelas 1           : " + kls + "\n");
        System.out.print("\sBiaya             : " + currencyFormat.format(biaya) + "\n");

        if (!kls2.equals("")) {
            System.out.print("\sJurusan 2         : " + Jurusan2 + "\n");
            System.out.print("\sKelas 2           : " + kls2 + "\n");
            System.out.print("\sBiaya Kelas 2     : " + currencyFormat.format(biayaKelas2) + "\n");
        }

        System.out.print("\sTotal Biaya       : " + currencyFormat.format(totalBiaya) + "\n");
        System.out.print("\sBayar             : ");
        bayar = input.nextInt();
        kembali = bayar - totalBiaya;
        System.out.print("\sKembalian         : " + currencyFormat.format(kembali) + "\n");
        System.out.print("\sBonus             : " + bonus + "\n");
        System.out.print("\s================================================\n");
    }
}
