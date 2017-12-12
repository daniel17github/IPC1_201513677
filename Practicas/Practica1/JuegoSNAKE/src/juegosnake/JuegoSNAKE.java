package juegosnake;

import java.util.Scanner;

public class JuegoSNAKE {

    private static int temp;
    private static vectorHistorial historial[] = new vectorHistorial[5];
    private static short pointX, pointY;
    private static short cabeza1X, cabeza1Y;
    private static short aumentoSnake, punteoFruta, cuentaFruta;
    private static vectorSF[] SnakeX;
    private static vectorSF[] SnakeY;
    private static vectorSF[][] Snake;
    public static Scanner pantalla = new Scanner(System.in);

    public static void main(String[] args) {

        menuPrincipal();
    }

    private static void menuPrincipal() {
        String menuStr01;
        System.out.println("-------------------------------");
        System.out.println("         MENU PRINCIPAL        ");
        System.out.println("-------------------------------");
        System.out.println("Elegi una opcion y presiona ENTER");
        System.out.println("-------------------------------");
        System.out.println("      [1] - INICIO");
        System.out.println("      [2] - REGRESAR AL JUEGO");
        System.out.println("      [3] - HISTORIAL");
        System.out.println("      [4] - SALIR");
        System.out.println("-------------------------------");

        menuStr01 = pantalla.nextLine();
        seleccionMenu(menuStr01);
    }

    private static void menuInicio() {

        String inicioStr01;
        // String inicioStr001;
        short inicioSht01, inicioSht02, inicioSht03;
        int i = historial.length;
        Scanner inicioScan = new Scanner(System.in);

        System.out.println("────────────── Inicio ──────────────");
        System.out.println("Ingresa tu nombre");
        inicioStr01 = inicioScan.nextLine();
        //   System.out.println("Ingresa tu Fecha de Nacimiento en letras");
        // inicioStr001 = inicioScan.nextLine();
        System.out.println("────────────────X────────────────");
        System.out.println("Ingresa el ancho del tablero (70)");
        inicioSht01 = inicioScan.nextShort();
        System.out.println("────────────────Y────────────────");
        System.out.println("Ingresa el alto del tablero (35)");
        inicioSht02 = inicioScan.nextShort();
        System.out.println("────────────────S────────────────");
        System.out.println("Ingresa el tamaño inicial de SNAKE (3 - 9)");
        inicioSht03 = inicioScan.nextShort();

        cuentaFruta = (short) 0;

        while (i != 0) {
            temp = Math.abs(i - historial.length);
            if (historial[temp] == null) {
                historial[temp] = new vectorHistorial(inicioSht01, inicioSht02, inicioSht03, inicioStr01, cuentaFruta);
                break;
            } else {
                i--;
            }
        }

        inicializarPantalla();
    }

    private static void menuHistorial() {

        int c = historial.length;
        System.out.println("---------------- HISTORIAL ----------------");
        System.out.println("|#| USUARIO || X || Y || SNAKE || PUNTEO ||");
        while (c != 0) {
            if (historial[historial.length - c] != null) {
                System.out.print("|" + ((historial.length - c) + 1) + "| ");
                System.out.println(historial[historial.length - c].getUsuarioName() + " || " + historial[historial.length - c].getUsuarioX()
                        + " || " + historial[historial.length - c].getUsuarioY() + " || " + historial[historial.length - c].getUsuarioSnake() + " || "
                        + historial[historial.length - c].getUsuarioScore() + " ||");
                c--;
            } else {
                break;
            }
        }
        System.out.println("\n");
        menuPrincipal();
    }

    private static void inicializarPantalla() {

        short largo;
        String d;
        aumentoSnake = 0;
        largo = (short) (historial[temp].getUsuarioX() * historial[temp].getUsuarioY() * 2 / 3); //importante para entender el try-catch de moverSnake()

        setSnake(new vectorSF[historial[temp].getUsuarioX()][historial[temp].getUsuarioY()]);
        setSnakeX(new vectorSF[largo]);//prepara la matriz en dónde alojaran los puntos en x del SNAKE
        setSnakeY(new vectorSF[largo]);//prepara la matriz en dónde alojaran los puntos en y del SNAKE  
        cabeza1X = (short) (historial[temp].getUsuarioX() / 2);//prepara el punto inicial de la cabeza del SNAKE (x)
        cabeza1Y = 1;

        vectorSF.inicializarVector(historial[temp].getUsuarioX(), historial[temp].getUsuarioY(), Snake);
        functionSnake.inicializarSnake(historial[temp].getUsuarioSnake(), SnakeX, SnakeY, cabeza1X, Snake);

        functionFruta.aparecerFruta(Snake);
        functionFruta.centro(historial[temp].getUsuarioX(), historial[temp].getUsuarioY());//ingresa los valores para calcular el punteo en la clase funcionFruta

        punteoFruta = (short) functionFruta.calcularFruta();//recupera el valor del punteo
        functionFruta.bitacoraFruta(punteoFruta);//llena la Bitacora en el indice 0 con el punteo recién creado

        System.out.println(historial[temp].getUsuarioScore() + " || " + historial[temp].getUsuarioName());// Imprime el punteo actual junto con el nombre del usuario
        vectorSF.imprimirVector(historial[temp].getUsuarioX(), historial[temp].getUsuarioY(), Snake);//imprime la matriz
        functionFruta.imprimirBitacora();

        d = pantalla.nextLine();
        mover(d);
    }

    private static void Pantalla() {
        String d;
        System.out.println(historial[temp].getUsuarioScore() + " || " + historial[temp].getUsuarioName());// Imprime el punteo actual junto con el nombre del usuario
        vectorSF.imprimirVector(historial[temp].getUsuarioX(), historial[temp].getUsuarioY(), Snake);//imprime la matriz ya actualizada
        functionFruta.imprimirBitacora();
        d = pantalla.nextLine();
        mover(d);
    }

    private static void mover(String d) {
        short tamanoSnake;
        tamanoSnake = (short) (historial[temp].getUsuarioSnake() + aumentoSnake);
        switch (d) {
            case "W"://arriba
            case "w":
                pointX = 0;
                pointY = -1;
                break;
            case "A":
            case "a"://izquierda
                pointX = -1;
                pointY = 0;
                break;
            case "S"://abajo
            case "s":
                pointX = 0;
                pointY = 1;
                break;
            case "D"://derecha
            case "d":
                pointX = 1;
                pointY = 0;
                break;
            case "M":
            case "m":
                regresarSnake((short) 2);
                break;
            default:

                Pantalla();
                break;
        }
        cabeza1X = (short) (cabeza1X + pointX);
        cabeza1Y = (short) (cabeza1Y + pointY);

        functionSnake.moverSnake(cabeza1X, cabeza1Y, tamanoSnake, SnakeX, SnakeY, Snake);
        Pantalla();
    }

    private static void seleccionMenu(String opcion) {
        switch (opcion) {
            case "1":
                menuInicio();
                break;
            case "2":
                System.out.println(historial[temp].getUsuarioScore() + " || " + historial[temp].getUsuarioName());// Imprime el punteo actual junto con el nombre del usuario
                vectorSF.imprimirVector(historial[temp].getUsuarioX(), historial[temp].getUsuarioY(), Snake);//imprime la matriz
                functionFruta.imprimirBitacora();
                break;
            case "3":
                menuHistorial();
                break;
            case "4":
                System.out.println("¡¡¡¡Gracias por Jugar!!!!");
                System.exit(0);
                break;

            default:

                System.err.println("Elige una opcion del Menu");
                menuPrincipal();
                break;
        }
    }

    public static void crecerSnake() {
        short cuentaFruta0;
        cuentaFruta0 = punteoFruta;//guarda el valor anterior (el que ya se comío Snake)
        functionFruta.aparecerFruta(Snake);//deja la marca de la fruta en la matriz
        punteoFruta = (short) functionFruta.calcularFruta();//calcula el nuevo punteo según la coordenadas en las que apareció
        cuentaFruta = (short) (cuentaFruta + cuentaFruta0);//aumenta el valor del punteo con el valor que se comió
        historial[temp].setUsuarioScore(cuentaFruta);//Guarda el punteo en HISTORIAL
        aumentoSnake++;//aumenta el tamaño de Snake
        functionFruta.bitacoraFruta(punteoFruta);
    }

    public static void regresarSnake(short opcion) {
        switch (opcion) {
            case 0:

                System.out.println("¡Oh! parece que tu SNAKE quiso salir del marco\n");
                System.out.println("|#| USUARIO || PUNTEO ||");
                System.out.println("|" + (temp + 1) + "| " + historial[temp].getUsuarioName() + " || "
                        + historial[temp].getUsuarioScore() + " ||");
                System.out.println("\n\nRegresarás automáticamente al MENU PRINCIPAL");

                menuPrincipal();
                break;
            case 1:

                System.out.println("¡Ups! parece que tu SNAKE se ha mordido ha si misma\n");
                System.out.println("|#| USUARIO || PUNTEO ||");
                System.out.println("|" + (temp + 1) + "| " + historial[temp].getUsuarioName() + " || "
                        + historial[temp].getUsuarioScore() + " ||");
                System.out.println("\n\nRegresarás automáticamente al MENU PRINCIPAL");

                menuPrincipal();
                break;
            case 2:

                System.out.println("Regresarás al MENU PRINCIPAL en unos instantes\n\nMientras tanto observa tu punteo\n");
                System.out.println("|#| USUARIO || PUNTEO ||");
                System.out.println("|" + (temp + 1) + "| " + historial[temp].getUsuarioName() + " || "
                        + historial[temp].getUsuarioScore() + " ||");

                menuPrincipal();
                break;
            case 3:

                System.out.println("¡Felicitaciones!, tu SNAKE ha crecido tanto como puede\n");
                System.out.println("¡Disfruta de tu record!\n");
                System.out.println("|#| USUARIO || PUNTEO ||");
                System.out.println("|" + (temp + 1) + "| " + historial[temp].getUsuarioName() + " || "
                        + historial[temp].getUsuarioScore() + " ||");
                System.out.println("\n\nRegresarás automáticamente al MENU PRINCIPAL");

                menuPrincipal();
                break;
        }
    }

    public static vectorSF[][] getSnake() {
        return Snake;
    }

    public static void setSnake(vectorSF[][] Snake) {
        JuegoSNAKE.Snake = Snake;
    }

    public static vectorSF[] getSnakeX() {
        return SnakeX;
    }

    public static void setSnakeX(vectorSF[] SnakeX) {
        JuegoSNAKE.SnakeX = SnakeX;
    }

    public static vectorSF[] getSnakeY() {
        return SnakeY;
    }

    public static void setSnakeY(vectorSF[] SnakeY) {
        JuegoSNAKE.SnakeY = SnakeY;
    }
}
