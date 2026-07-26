package com.mycompany.prueba;
import java.util.Scanner;
import java.util.Random;
import java.util.Locale;

/**
 *
 * @author HP
 */
public class Prueba {

    static Scanner teclado = new Scanner(System.in);
    static Random aleatorio = new Random();

    static int nivel;
    static int puntaje;
    static int totalPreguntas;
    static boolean continuar;
    static int salaAnterior = 0;

    static double respuestaCorrecta;

    // Historial
    static boolean historialCorrecto[] = new boolean[50];
    static int historialTema[] = new int[50];

    // Matriz [tema][correctas][incorrectas]
    static int matriz[][] = new int[6][3];

    // Control de preguntas repetidas
    static boolean preguntasUsadas[] = new boolean[6];

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        int opcion;

        do {
            System.out.println("==================================================");
            System.out.println("                  ESCAPE MATH");
            System.out.println("       Desafío Matemático de Nivelación");
            System.out.println("==================================================");
            System.out.println();
            System.out.println("Tu misión es completar las 5 salas matemáticas.");
            System.out.println("Cada sala contiene diferentes desafíos.");
            System.out.println();
            System.out.println("? 3 intentos por reto.");
            System.out.println("? Mientras menos intentos uses, más puntos obtendrás.");
            System.out.println("? Si fallas un reto, el juego termina.");
            System.out.println();
            System.out.println("============== MENÚ ==============");
            System.out.println("1. Iniciar partida");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = teclado.nextInt();

            switch (opcion) {
                case 1 -> iniciarJuego();
                case 2 -> {
                    System.out.println();
                    System.out.println("Gracias por jugar Escape Math.");
                }
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 2);
    }

    public static void iniciarJuego() {
        nivel = 1;
        puntaje = 0;
        totalPreguntas = 0;
        continuar = true;
        salaAnterior = 0;

        for (int i = 1; i <= 5; i++) {
            preguntasUsadas[i] = false;
        }

        while (continuar && nivel <= 25) {
            mostrarSala(nivel);
            resolverNivel();
        }

        if (nivel > 25) {
            System.out.println();
            System.out.println("==================================================");
            System.out.println("               ¡FELICIDADES!");
            System.out.println("      Has escapado del laboratorio.");
            System.out.println("      Completaste las 5 salas.");
            System.out.println("==================================================");
        }

        mostrarEstadisticas();
    }

    public static void mostrarSala(int nivel) {
        int sala = ((nivel - 1) / 5) + 1;

        if (sala != salaAnterior) {
            for (int i = 1; i <= 5; i++) {
                preguntasUsadas[i] = false;
            }
            salaAnterior = sala;
        }

        System.out.println();

        switch (sala) {
            case 1 -> {
                System.out.println("==================================================");
                System.out.println("                  SALA 1");
                System.out.println("           LÓGICA PROPOSICIONAL");
                System.out.println("==================================================");
            }
            case 2 -> {
                System.out.println("==================================================");
                System.out.println("                  SALA 2");
                System.out.println("           SISTEMAS NUMÉRICOS");
                System.out.println("==================================================");
            }
            case 3 -> {
                System.out.println("==================================================");
                System.out.println("                  SALA 3");
                System.out.println("             ÁLGEBRA BÁSICA");
                System.out.println("==================================================");
            }
            case 4 -> {
                System.out.println("==================================================");
                System.out.println("                  SALA 4");
                System.out.println("        EXPRESIONES ÁLGEBRAICAS");
                System.out.println("==================================================");
            }
            case 5 -> {
                System.out.println("==================================================");
                System.out.println("                  SALA 5");
                System.out.println("                FRACCIONES");
                System.out.println("==================================================");
            }
        }

        int reto = ((nivel - 1) % 5) + 1;
        System.out.println("Reto " + reto + " de 5");
        System.out.println();
        mostrarBarraProgreso();
    }

    public static void mostrarBarraProgreso() {
        int salaActual = ((nivel - 1) / 5) + 1;

        System.out.println();
        System.out.println("PROGRESO DE ESCAPE:");
        System.out.print("[");

        for (int i = 1; i <= 5; i++) {
            if (i <= salaActual) {
                System.out.print("#");
            } else {
                System.out.print("-");
            }
        }

        System.out.println("] Sala " + salaActual + " de 5");
    }

    public static void resolverNivel() {
        int intentos = 3;
        boolean exito = false;

        generarPregunta();

        while (intentos > 0 && !exito) {
            System.out.print("Tu respuesta: ");
            double respuestaUsuario = teclado.nextDouble();

            if (Math.abs(respuestaUsuario - respuestaCorrecta) < 0.01) {
                switch (intentos) {
                    case 3 -> puntaje += 10;
                    case 2 -> puntaje += 7;
                    default -> puntaje += 5;
                }

                System.out.println("¡Correcto!");
                exito = true;
                int sala = ((nivel - 1) / 5) + 1;

                matriz[sala][0]++;
                historialCorrecto[totalPreguntas] = true;
                historialTema[totalPreguntas] = sala;
            } else {
                int sala = ((nivel - 1) / 5) + 1;

                matriz[sala][1]++;
                historialCorrecto[totalPreguntas] = false;
                historialTema[totalPreguntas] = sala;
                intentos--;

                if (intentos > 0) {
                    System.out.println("Respuesta incorrecta.");
                    System.out.println("Intentos restantes: " + intentos);
                }
            }
        }

        totalPreguntas++;

        if (exito) {
            nivel++;
            System.out.println("AVANZANDO AL NIVEL: " + nivel);
        } else {
            continuar = false;
            System.out.println();
            System.out.println("========== GAME OVER ==========");
            System.out.println("No lograste superar el desafío.");
            System.out.println("Puntaje: " + puntaje);
        }
    }

    public static void generarPregunta() {
        int sala = ((nivel - 1) / 5) + 1;

        switch (sala) {
            case 1 ->  {
                int pregunta;
                do {
                    pregunta = aleatorio.nextInt(5) + 1;
                } while (preguntasUsadas[pregunta]);

                preguntasUsadas[pregunta] = true;
                System.out.println("Número de pregunta: " + pregunta);

                switch (pregunta) {
                    case 1 -> {
                        System.out.println("p = Verdadero, q = Falso");
                        System.out.println("p AND q");
                        System.out.println("1 = Verdadero | 0 = Falso");
                        respuestaCorrecta = 0;
                }
                    case 2 -> {
                        System.out.println("p = Verdadero, q = Falso");
                        System.out.println("p OR q");
                        System.out.println("1 = Verdadero | 0 = Falso");
                        respuestaCorrecta = 1;
                }
                    case 3 -> {
                        System.out.println("p = Falso");
                        System.out.println("NOT p");
                        System.out.println("1 = Verdadero | 0 = Falso");
                        respuestaCorrecta = 1;
                }
                    case 4 -> {
                        System.out.println("p = Verdadero, q = Verdadero");
                        System.out.println("p implica q");
                        System.out.println("1 = Verdadero | 0 = Falso");
                        respuestaCorrecta = 1;
                }
                    case 5 -> {
                        System.out.println("p = Falso, q = Falso");
                        System.out.println("p OR q");
                        System.out.println("1 = Verdadero | 0 = Falso");
                        respuestaCorrecta = 0;
                }
                }
            }
            case 2 ->  {
                int pregunta;
                do {
                    pregunta = aleatorio.nextInt(5) + 1;
                } while (preguntasUsadas[pregunta]);

                preguntasUsadas[pregunta] = true;

                switch (pregunta) {
                    case 1 -> {
                        System.out.println("¿8 pertenece a los números naturales?");
                        System.out.println("1 = Sí | 0 = No");
                        respuestaCorrecta = 1;
                }
                    case 2 -> {
                        System.out.println("¿-15 pertenece a los enteros?");
                        System.out.println("1 = Sí | 0 = No");
                        respuestaCorrecta = 1;
                }
                    case 3 -> {
                        System.out.println("¿√2 es un número racional?");
                        System.out.println("1 = Sí | 0 = No");
                        respuestaCorrecta = 0;
                }
                    case 4 -> {
                        System.out.println("¿3.14 es un número irracional?");
                        System.out.println("1 = Sí | 0 = No");
                        respuestaCorrecta = 0;
                }
                    case 5 -> {
                        System.out.println("¿0 pertenece a los naturales?");
                        System.out.println("1 = Sí | 0 = No");
                        respuestaCorrecta = 1;
                }
                }
            }
            case 3 ->  {
                int pregunta;
                do {
                    pregunta = aleatorio.nextInt(5) + 1;
                } while (preguntasUsadas[pregunta]);

                preguntasUsadas[pregunta] = true;

                switch (pregunta) {
                    case 1 -> {
                        System.out.println("Resuelve: 3x + 6 = 21");
                        respuestaCorrecta = 5;
                }
                    case 2 -> {
                        System.out.println("Resuelve: 2x - 8 = 10");
                        respuestaCorrecta = 9;
                }
                    case 3 -> {
                        System.out.println("Resuelve: 5x = 45");
                        respuestaCorrecta = 9;
                }
                    case 4 -> {
                        System.out.println("Resuelve: 4x + 4 = 20");
                        respuestaCorrecta = 4;
                }
                    case 5 -> {
                        System.out.println("Resuelve: 6x - 12 = 24");
                        respuestaCorrecta = 6;
                }
                }
            }
            case 4 ->  {
                int pregunta;
                do {
                    pregunta = aleatorio.nextInt(5) + 1;
                } while (preguntasUsadas[pregunta]);

                preguntasUsadas[pregunta] = true;

                switch (pregunta) {
                    case 1 -> {
                        System.out.println("Simplifica: 3x + 4x - 2x");
                        respuestaCorrecta = 5;
                }
                    case 2 -> {
                        System.out.println("Simplifica: 6a - 2a + a");
                        respuestaCorrecta = 5;
                }
                    case 3 -> {
                        System.out.println("Simplifica: 8y + 3y - 5y");
                        respuestaCorrecta = 6;
                }
                    case 4 -> {
                        System.out.println("Simplifica: 10m - 4m + 2m");
                        respuestaCorrecta = 8;
                }
                    case 5 -> {
                        System.out.println("Simplifica: 9p - 3p + 2p");
                        respuestaCorrecta = 8;
                }
                }
            }
            case 5 ->  {
                int pregunta;
                do {
                    pregunta = aleatorio.nextInt(5) + 1;
                } while (preguntasUsadas[pregunta]);

                preguntasUsadas[pregunta] = true;

                switch (pregunta) {
                    case 1 -> {
                        System.out.println("Resuelve: 1/2 + 1/4");
                        System.out.println("Escribe el resultado decimal");
                        respuestaCorrecta = 0.75;
                }
                    case 2 -> {
                        System.out.println("Resuelve: 3/4 - 1/2");
                        System.out.println("Escribe el resultado decimal");
                        respuestaCorrecta = 0.25;
                }
                    case 3 -> {
                        System.out.println("Resuelve: 2/3 + 1/3");
                        System.out.println("Escribe el resultado decimal");
                        respuestaCorrecta = 1;
                }
                    case 4 -> {
                        System.out.println("Resuelve: 5/6 - 2/6");
                        System.out.println("Escribe el resultado decimal");
                        respuestaCorrecta = 0.5;
                }
                    case 5 -> {
                        System.out.println("Resuelve: 2/5 × 3/4");
                        System.out.println("Escribe el resultado decimal");
                        respuestaCorrecta = 0.3;
                }
                }
            }
        }
    }

    public static void mostrarEstadisticas() {
        System.out.println();
        System.out.println("==============================");
        System.out.println("      ESTADÍSTICAS FINALES");
        System.out.println("==============================");
        System.out.println("Puntaje obtenido: " + puntaje);
        System.out.println("Retos respondidos: " + totalPreguntas);
        System.out.println();

        for (int i = 1; i <= 5; i++) {
            int correctas = matriz[i][0];
            int incorrectas = matriz[i][1];
            int total = correctas + incorrectas;

            double porcentaje = 0;
            if (total > 0) {
                porcentaje = (correctas * 100.0) / total;
            }

            System.out.println("------------------------------");
            System.out.println("Sala " + i);
            System.out.println("Correctas: " + correctas);
            System.out.println("Incorrectas: " + incorrectas);
            System.out.printf("Porcentaje: %.2f%%\n", porcentaje);
        }

        System.out.println();
        System.out.println("==============================");
        System.out.println("       HISTORIAL DE RESPUESTAS");
        System.out.println("==============================");

        for (int i = 0; totalPreguntas >= i; i++) {
            System.out.print("Pregunta " + (i + 1));
            System.out.print(" | Sala: " + historialTema[i]);

            if (historialCorrecto[i]) {
                System.out.println(" | Correcta");
            } else {
                System.out.println(" | Incorrecta");
            }
        }
    }
}