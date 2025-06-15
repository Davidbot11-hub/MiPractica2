package com.unl.practica2.base.Practica1;

import java.io.*;
import java.util.*;

public class Lista<E> {

    private Node<E> head;
    private Node<E> last;
    private Integer length;

    public Lista() {
        this.head = null;
        this.last = null;
        this.length = 0;
    }

    public Integer getLength() {
        return length;
    }

    public Boolean isEmpty() {
        return head == null || length == 0;
    }

    public Node<E> getNode(Integer pos) throws ArrayIndexOutOfBoundsException {
        if (isEmpty()) throw new ArrayIndexOutOfBoundsException("Lista vacía");
        if (pos < 0 || pos >= length) throw new ArrayIndexOutOfBoundsException("Posición fuera de rango");

        Node<E> current = head;
        for (int i = 0; i < pos; i++) {
            current = current.getNext();
        }
        return current;
    }

    public E get(Integer pos) {
        return getNode(pos).getData();
    }

    private void addFirst(E data) {
        Node<E> newNode = new Node<>(data, head);
        head = newNode;
        if (last == null) last = newNode;
        length++;
    }

    private void addLast(E data) {
        Node<E> newNode = new Node<>(data);
        if (isEmpty()) {
            addFirst(data);
        } else {
            last.setNext(newNode);
            last = newNode;
            length++;
        }
    }

    public void add(E data) {
        addLast(data);
    }

    public String print() {
        if (isEmpty()) return "Lista vacía";

        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null) {
            sb.append(current.getData()).append(" -> ");
            current = current.getNext();
        }
        return sb.toString();
    }

    public Lista<E> obtenerRepetidos() {
        Map<E, Integer> contador = new HashMap<>();
        Node<E> actual = head;
        while (actual != null) {
            E dato = actual.getData();
            contador.put(dato, contador.getOrDefault(dato, 0) + 1);
            actual = actual.getNext();
        }

        Lista<E> repetidos = new Lista<>();
        for (Map.Entry<E, Integer> entry : contador.entrySet()) {
            if (entry.getValue() > 1) {
                repetidos.add(entry.getKey());
            }
        }

        return repetidos;
    }

    public void imprimirResumenDatos(long tiempoNs) {
        Lista<E> repetidos = obtenerRepetidos();
        int total = this.getLength();
        int repetidosCount = repetidos.getLength();
        long tiempoMs = tiempoNs / 1_000_000;

        System.out.println("\nResumen:");
        System.out.println("Total de números leídos: " + total);
        System.out.println("Total de números repetidos (únicos): " + repetidosCount);

        System.out.println();
        System.out.println("| Tiempo de ejecución del programa                     |");
        System.out.println("+------------------------------------------------------+");
        System.out.printf("| Tiempo en nanosegundos:        %15d ns |\n", tiempoNs);
        System.out.printf("| Tiempo en milisegundos:            %10d ms |\n", tiempoMs);
        System.out.println("+------------------------------------------------------+");
        System.out.printf("| Total de números leídos        |  %6d |\n", total);
        System.out.printf("| Cantidad de números repetidos  |  %6d |\n", repetidosCount);
        System.out.println("+------------------------------------------------------+");

        System.out.println("\nLista de elementos repetidos:");
        System.out.println(repetidos.print());
    }

    public static Lista<Integer> cargarDesdeArchivo(String rutaArchivo) {
        Lista<Integer> lista = new Lista<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    try {
                        Integer valor = Integer.parseInt(linea.trim());
                        lista.add(valor);
                    } catch (NumberFormatException ex) {
                        System.out.println("Valor inválido en archivo: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
        return lista;
    }

    public static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setData(T data) {
            this.data = data;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        System.out.println("Cargando datos desde el archivo...");
        Lista<Integer> lista = cargarDesdeArchivo(
            "src/main/java/com/unl/practica2/base/Practica1/data.txt"
        );
        long startTime = System.nanoTime();
        lista.obtenerRepetidos(); 
        long endTime = System.nanoTime();
        long duracionNs = endTime - startTime;
        lista.imprimirResumenDatos(duracionNs);
    }
}
