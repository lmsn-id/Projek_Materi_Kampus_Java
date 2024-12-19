package CLI;

public class array {

    public static void main(String[] args) {

        int[] numbers = { 10, 20, 30, 40, 50 };

        System.out.println("Elemen-elemen dalam array:");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Index " + i + ": " + numbers[i]);
        }

        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        System.out.println("Jumlah semua elemen dalam array: " + sum);

        int max = numbers[0];
        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
        }
        System.out.println("Elemen terbesar dalam array: " + max);

        int indexToChange = 2;
        numbers[indexToChange] = 99;
        System.out.println(
                "Setelah perubahan, elemen pada indeks ke-" + indexToChange + " menjadi: " + numbers[indexToChange]);
    }
}
