import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {
    private JPanel panel;
    private JTextField pantalla;
    private CalculadoraLogica logica;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(211, 211, 211));

        logica = new CalculadoraLogica();
        iniciarUI();
    }

    private void iniciarUI() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.add(panel);

        pantalla = new JTextField();
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setPreferredSize(new Dimension(200, 75));
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
        String textoPantalla = pantalla.getText();
        pantalla.setText(logica.manejarComando(comando, textoPantalla));
    }
}
