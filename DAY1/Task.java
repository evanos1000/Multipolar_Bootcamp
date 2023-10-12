import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public class Task {
    public static void main(String[] args) {
// Buat List angka
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
// Traditional approach
        List<Integer> evenNumbers = new ArrayList<>();
        for (Integer number : numbers) {
            if (number % 2 == 0) {
                evenNumbers.add(number);
            }
        }
        System.out.println("Bilangan genap (tradisional): " + evenNumbers);
// Menggunakan Java Stream untuk mengambil bilangan genap dan menjadikannya List baru
        List<Integer> evenNumbersStream = numbers.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("Bilangan genap (Java Stream): " +
                evenNumbersStream);
    }
}
