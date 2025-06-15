package com.unl.practica2.base.Controller;

import java.io.BufferedReader;
import java.io.FileReader;

import com.unl.practica2.base.Controller.data_struct.list.LinkedList;


public class PrimeraParte {
     private LinkedList<Integer> lista;

    public void cargar() {
        // TODO
        lista = new LinkedList<>();
        try {
            BufferedReader fb = new BufferedReader(new FileReader("src/main/java/com/unl/practica2/base/Practica1/data.txt"));
            String line = fb.readLine();
            while (line != null) {
                lista.add(Integer.parseInt(line));
                line = fb.readLine();
            }
            fb.close();
            // System.out.println(fb.readLine());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    private void quickSort(Integer arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(Integer arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }
        int swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    public void q_order() {
        cargar();
        if (!lista.isEmpty()) {
            Integer arr[] = lista.toArray();
            long startTime = System.currentTimeMillis();
            quickSort(arr, 0, arr.length - 1);
           // quickSort(arr, 0, (arr.length - 1));
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("se ha demorado quicksort " + endTime );
            lista.toList(arr);
        }        
    }

    public void s_order() {
        cargar();
        if (!lista.isEmpty()) {
            Integer arr[] = lista.toArray();
            long startTime = System.currentTimeMillis();
            shell_sort(arr);
           // quickSort(arr, 0, (arr.length - 1));
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("se ha demorado shell " + endTime );
            lista.toList(arr);
        }        
    }

    public void shell_sort(Integer arrayToSort[]) {
        int n = arrayToSort.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = arrayToSort[i];
                int j = i;
                while (j >= gap && arrayToSort[j - gap] > key) {
                    arrayToSort[j] = arrayToSort[j - gap];
                    j -= gap;
                }
                arrayToSort[j] = key;
            }
        }
    }

    public static void main(String[] args) {
    PrimeraParte prueba = new PrimeraParte();
    long[] tiemposQuick = new long[3];
    long[] tiemposShell = new long[3];

    final String RED = "\u001B[31m";
    final String GREEN = "\u001B[32m";
    final String BLUE = "\u001B[34m";
    final String RESET = "\u001B[0m";

    System.out.println(BLUE + "\n========= Medición de Algoritmos =========\n" + RESET);

    for (int i = 0; i < 3; i++) {
        System.out.println("→ Iteración " + (i + 1));
        
        // Medir QuickSort
        long inicioQuick = System.currentTimeMillis();
        prueba.q_order();
        long finQuick = System.currentTimeMillis();
        tiemposQuick[i] = finQuick - inicioQuick;

        // Medir ShellSort
        long inicioShell = System.currentTimeMillis();
        prueba.s_order();
        long finShell = System.currentTimeMillis();
        tiemposShell[i] = finShell - inicioShell;

        System.out.println(); 
    }

    long totalQuick = 0;
    long totalShell = 0;
    for (int i = 0; i < 3; i++) {
        totalQuick += tiemposQuick[i];
        totalShell += tiemposShell[i];
    }

    System.out.println(GREEN + "✔ Tiempo total QuickSort: " + totalQuick + " ms" + RESET);
    System.out.println(RED + "✔ Tiempo total ShellSort: " + totalShell + " ms" + RESET);
    System.out.println("\n+----------------+--------------+--------------+");
    System.out.println("|  Iteración     |  QuickSort   |  ShellSort   |");
    System.out.println("+----------------+--------------+--------------+");
    for (int i = 0; i < 3; i++) {
        System.out.printf("|      %d         |     %4d ms   |     %4d ms   |\n", i + 1, tiemposQuick[i], tiemposShell[i]);
    }
    System.out.println("+----------------+--------------+--------------+");
    System.out.println(BLUE + "\nResultado generado correctamente." + RESET);
}
}
