import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class App {

    public static CyclicBarrier barreraVerificacion;
    public static CyclicBarrier barreraIteracion;
    public static int iteraciones;
    public static Celda[][] matriz;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Por favor, ingrese el nombre del archivo:");
        String fileName = scanner.nextLine();
        String filePath = "./Data/" + fileName;
        boolean[][] valores_de_matriz = readFile(filePath);
        System.out.println("Por favor, ingrese el número de iteraciones:");
        while (!scanner.hasNextInt()) {
            System.out.println("El valor ingresado no es un entero válido. Por favor, ingrese el número de iteraciones:");
            scanner.next();
        }
        iteraciones = scanner.nextInt(); 
        scanner.close();

        barreraVerificacion = new CyclicBarrier(valores_de_matriz.length*valores_de_matriz.length*2, new Runnable() {
            @Override
            public void run() {
                System.out.println("Barrera Desbloqueada - Verificacion");
                App.printMatriz();
            }
        });

        barreraIteracion = new CyclicBarrier(valores_de_matriz.length*valores_de_matriz.length*2, new Runnable() {
            @Override
            public void run() {
                System.out.println("Barrera Desbloqueada - Iteracion");
                App.decrementarIteraciones();
                App.printMatriz();
            }
        });

        crearMatriz(valores_de_matriz);
    }


    
    // Funciones auxiliares
    public static boolean[][] readFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        int numberOfLines = Integer.parseInt(line);
        boolean[][] values = new boolean[numberOfLines][];

        for (int i = 0; i < numberOfLines; i++) {
            line = br.readLine(); 
            if (line != null) {
                String[] stringValues = line.split(","); 
                boolean[] booleanValues = new boolean[stringValues.length];
                for (int j = 0; j < stringValues.length; j++) {
                    booleanValues[j] = Boolean.parseBoolean(stringValues[j]); 
                }
                values[i] = booleanValues; 
            }
        }
        return values;
    }

    public static void crearMatriz(boolean[][] valores_matriz) {
        int tamanio_matriz = valores_matriz.length;
        matriz = new Celda[tamanio_matriz][tamanio_matriz];
        int id = 1;
        for (int i = 0; i < tamanio_matriz; i++) {
            for (int j = 0; j < tamanio_matriz; j++) {
                Celda celda = new Celda(id, i, j, valores_matriz[i][j]);
                matriz[i][j] = celda;
                id++;
            }
        }
        for (int i = 0; i < tamanio_matriz; i++) {
            for (int j = 0; j < tamanio_matriz; j++) {
                matriz[i][j].asignarVecinos();
            }
        }
        for (int i = 0; i < tamanio_matriz; i++) {
            for (int j = 0; j < tamanio_matriz; j++) {
                matriz[i][j].run();
            }
        }
    }

    public static void printMatriz() {
        matriz = App.getMatriz();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                Celda celda = matriz[i][j];
                System.out.print(celda.getEstado().getEstado() ? "T " : "F ");
            }
            System.out.println();
        }
    }



    // Getters and Setters
    public static int getIteraciones() {
        return iteraciones;
    }

    public static CyclicBarrier getBarreraIteracion() {
        return barreraIteracion;
    }

    public static void setBarreraIteracion(CyclicBarrier barreraIteracion) {
        App.barreraIteracion = barreraIteracion;
    }

    public static void setIteraciones(int iteraciones) {
        App.iteraciones = iteraciones;
    }

    public static void decrementarIteraciones() {
        App.iteraciones--;
    }

    public static Celda[][] getMatriz() {
        return matriz;
    }

    public static void setMatriz(Celda[][] matriz) {
        App.matriz = matriz;
    }

    public static CyclicBarrier getBarreraVerificacion() {
        return barreraVerificacion;
    }

    public static void setBarreraVerificacion(CyclicBarrier barreraVerificacion) {
        App.barreraVerificacion = barreraVerificacion;
    }
}
