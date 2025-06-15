package com.unl.practica2.base.Practica1;

import java.io.*;

public class Arreglo {
    private int[] numeros;
    private int[] numIguales;

    public static int[] leerNumerosDesdeArchivo(String rutaArchivo) {
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                contador += partes.length;
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return new int[0];
        }

        int[] numeros = new int[contador];
        int indice = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                for (String parte : partes) {
                    if (!parte.isEmpty()) {
                        numeros[indice++] = Integer.parseInt(parte);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al procesar el archivo: " + e.getMessage());
            return new int[0];
        }
        return numeros;
    }

    public static int[] encontrarNumerosRepetidos(int[] numeros) {
        int n = numeros.length;
        int[] repetidosTemp = new int[n];
        int countRepetidos = 0;

        for (int i = 0; i < n; i++) {
            int actual = numeros[i];
            boolean yaEstaAgregado = false;
            for (int k = 0; k < countRepetidos; k++) {
                if (repetidosTemp[k] == actual) {
                    yaEstaAgregado = true;
                    break;
                }
            }
            if (yaEstaAgregado) continue;
            int repeticiones = 0;
            for (int j = 0; j < n; j++) {
                if (numeros[j] == actual) {
                    repeticiones++;
                }
            }
            if (repeticiones > 1) {
                repetidosTemp[countRepetidos++] = actual;
            }
        }

        int[] repetidos = new int[countRepetidos];
        for (int i = 0; i < countRepetidos; i++) {
            repetidos[i] = repetidosTemp[i];
        }

        return repetidos;
    }

    private void mostrarTiempoDeEjecucion(long inicio, long fin) {
        long tiempoTotal = fin - inicio;
        long tiempoMilisegundos = tiempoTotal / 1_000_000;

        System.out.printf("\n| %-52s |\n", "Tiempo de ejecución del programa");
        System.out.println("+------------------------------------------------------+");
        System.out.printf("| %-42s %10s |\n", "Tiempo en nanosegundos:", tiempoTotal + " ns");
        System.out.printf("| %-42s %10s |\n", "Tiempo en milisegundos:", tiempoMilisegundos + " ms");
        System.out.println("+------------------------------------------------------+");
    }

    public void imprimir() {
        System.out.println("+------------------------------------------------------+");
        System.out.printf("| %-38s | %6d |\n", "Total de números leídos", this.numeros.length);
        System.out.printf("| %-38s | %6d |\n", "Cantidad de números repetidos únicos", this.numIguales.length);
        System.out.println("+------------------------------------------------------+");
    }

    public static void imprimirResumen(int[] numeros, int[] repetidos) {
        System.out.println("\nResumen:");
        System.out.println("Total de números leídos: " + numeros.length);
        System.out.println("Total de números repetidos (únicos): " + repetidos.length);
    }

    public static void main(String[] args) {
        long inicio = System.nanoTime();
        int[] numeros = leerNumerosDesdeArchivo("src/main/java/com/unl/practica2/base/Practica1/data.txt");
        System.out.print("Números leídos: ");
        for (int n : numeros) {
            System.out.print(n + " ");
        }
        int[] repetidos = encontrarNumerosRepetidos(numeros);
        System.out.print("\nNúmeros repetidos: ");
        for (int r : repetidos) {
            System.out.print(r + " ");
        }
        imprimirResumen(numeros, repetidos);
        long fin = System.nanoTime();
        Arreglo instancia = new Arreglo();
        instancia.numeros = numeros;
        instancia.numIguales = repetidos;
        instancia.mostrarTiempoDeEjecucion(inicio, fin);
        instancia.imprimir();
    }
}
