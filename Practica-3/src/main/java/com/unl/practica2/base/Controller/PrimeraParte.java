package com.unl.practica2.base.Controller;

import java.io.BufferedReader;
import java.io.FileReader;

import com.unl.practica2.base.Controller.data_struct.list.LinkedList;


public class PrimeraParte {
     private LinkedList<Integer> lista;

    public void cargar() {
        lista = new LinkedList<>();
        try {
            BufferedReader fb = new BufferedReader(new FileReader("src/main/java/com/unl/practica2/base/Practica1/data.txt"));
            String line = fb.readLine();
            while (line != null) {
                lista.add(Integer.parseInt(line));
                line = fb.readLine();
            }
            fb.close();
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

    final String RED = "\u001B[31m";
    final String GREEN = "\u001B[32m";
    final String BLUE = "\u001B[34m";
    final String RESET = "\u001B[0m";

    System.out.println(BLUE + "\n========= Medición de Algoritmos =========\n" + RESET);

    // Medir QuickSort
    long inicioQuick = System.currentTimeMillis();
    prueba.q_order();
    long finQuick = System.currentTimeMillis();
    long tiempoQuick = finQuick - inicioQuick;

    // Medir ShellSort
    long inicioShell = System.currentTimeMillis();
    prueba.s_order();
    long finShell = System.currentTimeMillis();
    long tiempoShell = finShell - inicioShell;

    System.out.println(GREEN + "✔ Tiempo QuickSort: " + tiempoQuick + " ms" + RESET);
    System.out.println(RED + "✔ Tiempo ShellSort: " + tiempoShell + " ms" + RESET);
    System.out.println("\n+----------------+--------------+--------------+");
    System.out.println("|  Algoritmo     |   Duración   |");
    System.out.println("+----------------+--------------+");
    System.out.printf("|  QuickSort     |   %6d ms   |\n", tiempoQuick);
    System.out.printf("|  ShellSort     |   %6d ms   |\n", tiempoShell);
    System.out.println("+----------------+--------------+");
    System.out.println(BLUE + "\nResultado generado correctamente." + RESET);
}
}