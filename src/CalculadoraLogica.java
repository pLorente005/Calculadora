import java.util.Stack;

public class CalculadoraLogica {
    private Stack<Double> operandos;
    private Stack<String> operadores;
    private boolean reiniciarPantalla;

    public CalculadoraLogica() {
        reiniciar();
    }

    public void setReiniciarPantalla(boolean reiniciarPantalla) {
        this.reiniciarPantalla = reiniciarPantalla;
    }

    public void reiniciar() {
        operandos = new Stack<>();
        operadores = new Stack<>();
        reiniciarPantalla = false;
    }

    public String borrarUltimoCaracter(String texto) {
        if (texto.length() > 0) {
            texto = texto.substring(0, texto.length() - 1);
        }
        if (texto.length() == 0) {
            return "0";
        } else {
            return texto;
        }
    }

    public String cambiarSigno(String texto) {
        if (texto.startsWith("-")) {
            return texto.substring(1);
        } else {
            return "-" + texto;
        }
    }

    public String pushOperador(String operador, String textoPantalla) {
        double operando = Double.parseDouble(textoPantalla);
        operandos.push(operando);
        while (!operadores.isEmpty() && precedencia(operador) <= precedencia(operadores.peek())) {
            String op = operadores.pop();
            double b = operandos.pop();
            double a = operandos.pop();
            operandos.push(evaluar(a, b, op));
        }
        operadores.push(operador);
        reiniciarPantalla = true;
        return String.join(" ", textoPantalla, operador);
    }

    public String calcularResultado(String textoPantalla) {
        double operando = Double.parseDouble(textoPantalla);
        operandos.push(operando);

        while (!operadores.isEmpty()) {
            String operador = operadores.pop();
            double b = operandos.pop();
            double a = operandos.pop();
            operandos.push(evaluar(a, b, operador));
        }

        reiniciarPantalla = true;
        return String.valueOf(operandos.pop());
    }

    private int precedencia(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "x":
            case "/":
                return 2;
            default:
                return -1;
        }
    }

    private double evaluar(double a, double b, String operador) {
        if (operador.equals("+")) {
            return a + b;
        } else if (operador.equals("-")) {
            return a - b;
        } else if (operador.equals("x")) {
            return a * b;
        } else if (operador.equals("/")) {
            if (b == 0) {
                return Double.NaN;
            }
            return a / b;
        } else {
            return Double.NaN;
        }
    }


    public boolean debeReiniciarPantalla() {
        return reiniciarPantalla;
    }

    public String manejarComando(String comando, String textoPantalla) {
        try {
            if ("C".equals(comando)) {
                reiniciar();
                return "0";
            } else if ("<".equals(comando)) {
                return borrarUltimoCaracter(textoPantalla);
            } else if ("+/-".equals(comando)) {
                return cambiarSigno(textoPantalla);
            } else if ("+".equals(comando) || "-".equals(comando) || "x".equals(comando) || "/".equals(comando)) {
                return pushOperador(comando, textoPantalla);
            } else if ("=".equals(comando)) {
                return calcularResultado(textoPantalla);
            } else {
                if (debeReiniciarPantalla()) {
                    setReiniciarPantalla(false);
                    if (comando.equals(".")) {
                        return "0.";
                    } else {
                        return comando;
                    }
                } else {
                    if (textoPantalla.length() < 16) {
                        if ("0".equals(textoPantalla)) {
                            if (comando.equals(".")) {
                                return textoPantalla + comando;
                            } else {
                                return comando;
                            }
                        } else {
                            if (comando.equals(".") && textoPantalla.contains(".")) {
                                return textoPantalla;
                            } else {
                                return textoPantalla + comando;
                            }
                        }
                    } else {
                        return textoPantalla;
                    }
                }
            }

        } catch (NumberFormatException e) {
            return "Error: Número invalido";
        } catch (IllegalArgumentException e) {
            return "Error: Operador desconocido";
        }
    }
}
