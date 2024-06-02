import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {
    private JPanel panel;
    private JTextField pantalla;
    private String operadorActual;
    private double operando1;
    private boolean reiniciarPantalla;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(211, 211, 211));

        iniciarUI();
    }

    private void iniciarUI() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.add(panel);

        pantalla = new JTextField();
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setPreferredSize(new Dimension(200, 50));
        pantalla.setFont(new Font("Arial", Font.PLAIN, 24));
        pantalla.setEditable(false);
        pantalla.setText("0");
        panel.add(pantalla, BorderLayout.NORTH);

        String[] botones = {
                "C", "<", "+/-", "/",
                "7", "8", "9", "x",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "", "0", ".", "="
        };

        JPanel panelBotones = new JPanel(new GridLayout(5, 4, 5, 5));
        panel.add(panelBotones, BorderLayout.CENTER);

        for (String boton : botones) {
            crearBoton(panelBotones, boton);
        }
    }

    private void crearBoton(JPanel panelBotones, String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setForeground(Color.BLACK);
        if (texto.equals("=")) {
            btn.setBackground(new Color(0, 90, 158));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(Color.WHITE);
        }

        btn.addActionListener(this);
        panelBotones.add(btn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        manejarClickBoton(comando);
    }

    private void manejarClickBoton(String comando) {
        if ("C".equals(comando)) {
            pantalla.setText("0");
            operando1 = 0;
            operadorActual = "";
            reiniciarPantalla = false;
        } else if ("<".equals(comando)) {
            String texto = pantalla.getText();
            if (texto.length() > 0) {
                pantalla.setText(texto.substring(0, texto.length() - 1));
            }
            if (pantalla.getText().length() == 0) {
                pantalla.setText("0");
            }
        } else if ("+/-".equals(comando)) {
            if (pantalla.getText().startsWith("-")) {
                pantalla.setText(pantalla.getText().substring(1));
            } else {
                pantalla.setText("-" + pantalla.getText());
            }
        } else if ("+".equals(comando) || "-".equals(comando) || "x".equals(comando) || "/".equals(comando)) {
            operando1 = Double.parseDouble(pantalla.getText());
            operadorActual = comando;
            reiniciarPantalla = true;
        } else if ("=".equals(comando)) {
            double operando2 = Double.parseDouble(pantalla.getText());
            double resultado = 0;
            if ("+".equals(operadorActual)) {
                resultado = operando1 + operando2;
            } else if ("-".equals(operadorActual)) {
                resultado = operando1 - operando2;
            } else if ("x".equals(operadorActual)) {
                resultado = operando1 * operando2;
            } else if ("/".equals(operadorActual)) {
                resultado = operando1 / operando2;
            }
            pantalla.setText(String.valueOf(resultado));
            operadorActual = "";
            reiniciarPantalla = true;
        } else {
            if (reiniciarPantalla) {
                pantalla.setText(comando);
                reiniciarPantalla = false;
            } else {
                if (pantalla.getText().equals("0")) {
                    pantalla.setText(comando);
                } else {
                    pantalla.setText(pantalla.getText() + comando);
                }
            }
        }
    }

}
