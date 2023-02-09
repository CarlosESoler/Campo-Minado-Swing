package br.com.carlos.cm.view;

import br.com.carlos.cm.model.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class main extends JFrame {
    public main() {
        Tabuleiro tabuleiro = new Tabuleiro(16, 30, 20);
        add(new PainelTabuleiro(tabuleiro));

        setTitle("Campo Minado");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
        new main();
    }

}
