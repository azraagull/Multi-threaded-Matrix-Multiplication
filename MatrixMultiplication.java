import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Matris satırı çarpanı sınıfı
class MatrixRowMultiplier implements Runnable {
    private final int[][] A; // İlk matris
    private final int[][] B; // İkinci matris
    private final int row; // Çarpılacak satır
    private final int[][] C; // Sonuç matrisi

    // Constructor
    public MatrixRowMultiplier(int[][] A, int[][] B, int row, int[][] C) {
        this.A = A;
        this.B = B;
        this.row = row;
        this.C = C;
    }

    @Override
    public void run() {
        // Matris çarpım işlemi
        for (int j = 0; j < B[0].length; j++) {
            for (int i = 0; i < A[0].length; i++) {
                C[row][j] += A[row][i] * B[i][j];
            }
        }
    }
}

public class MatrixMultiplication {
    public static void main(String[] args) {
        // Argüman kontrolü
        if (args.length != 4) {
            System.out.println("Usage: java MatrixMultiplication <row1> <col1> <fileA> <fileB>");
            return;
        }

        int row1 = Integer.parseInt(args[0]); // İlk matrisin satır sayısı
        int col1 = Integer.parseInt(args[1]); // İlk matrisin sütun sayısı
        String fileA = args[2]; // İlk matris dosyası
        String fileB = args[3]; // İkinci matris dosyası

        int[][] A;
        int[][] B;

        try {
            // Matris dosyalarının okunması
            A = readMatrixFromFile(fileA, row1, col1);
            B = readMatrixFromFile(fileB, col1, row1);
        } catch (IOException e) {
            System.err.println("Error reading matrix files: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid matrix data format. " + e.getMessage());
            return;
        }

        // Matris boyutlarının kontrolü
        if (A[0].length != B.length) {
            System.err.println("Error: Matrices cannot be multiplied due to incompatible dimensions.");
            System.err.println("Matrix A columns: " + A[0].length + ", Matrix B rows: " + B.length);
            return;
        }

        // Matris A'nın yazdırılması
        System.out.println("Matrix A:");
        printMatrix(A);

        // Matris B'nin yazdırılması
        System.out.println("\nMatrix B:");
        printMatrix(B);

        int[][] resultMatrix = new int[row1][B[0].length]; // Sonuç matrisi
        Thread[] threads = new Thread[row1]; // İş parçacıkları
        long[] threadTimes = new long[row1]; // İş parçacığı süreleri

        // İş parçacıklarının oluşturulması ve başlatılması
        for (int i = 0; i < row1; i++) {
            final int row = i;
            threads[i] = new Thread(() -> {
                long startTime = System.nanoTime();
                new MatrixRowMultiplier(A, B, row, resultMatrix).run();
                long endTime = System.nanoTime();
                threadTimes[row] = endTime - startTime;
            });
            threads[i].start();
        }

        // İş parçacıklarının tamamlanmasının beklenmesi
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Error: Thread interrupted. " + e.getMessage());
            }
        }

        // Sonuç matrisinin yazdırılması
        System.out.println("\nResult Matrix:");
        printMatrix(resultMatrix);

        // İş parçacığı çalışma sürelerinin yazdırılması
        System.out.println("\nThread Execution Times (in nanoseconds):");
        for (int i = 0; i < threadTimes.length; i++) {
            System.out.println("Thread " + (i + 1) + ": " + threadTimes[i]);
        }
    }

    // Matrisin dosyadan okunması
    private static int[][] readMatrixFromFile(String filename, int rows, int cols) throws IOException {
        int[][] matrix = new int[rows][cols];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for (int i = 0; i < rows; i++) {
                String line = br.readLine();
                if (line == null) {
                    throw new IOException("Error: Not enough rows in file " + filename);
                }
                String[] values = line.split(" ");
                if (values.length != cols) {
                    throw new IOException("Error: Row " + (i + 1) + " in file " + filename + " does not have " + cols + " columns.");
                }
                for (int j = 0; j < cols; j++) {
                    matrix[i][j] = Integer.parseInt(values[j]);
                }
            }
        }
        return matrix;
    }

    // Matrisin yazdırılması
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
