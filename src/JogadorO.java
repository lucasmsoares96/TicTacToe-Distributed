import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class JogadorO {
    private JPanel JogadorO;
    private JButton Button11;
    private JButton Button12;
    private JButton Button13;
    private JButton Button21;
    private JButton Button22;
    private JButton Button23;
    private JButton Button31;
    private JButton Button32;
    private JButton Button33;
    JButton[] listButton = {
            Button11, Button12, Button13,
            Button21, Button22, Button23,
            Button31, Button32, Button33
    };
    private Cliente cliente;

    public JogadorO() {
        Button11.addActionListener(actionEvent -> jogada(new Pacote(1, 1, "O")));
        Button12.addActionListener(actionEvent -> jogada(new Pacote(1, 2, "O")));
        Button13.addActionListener(actionEvent -> jogada(new Pacote(1, 3, "O")));
        Button21.addActionListener(actionEvent -> jogada(new Pacote(2, 1, "O")));
        Button22.addActionListener(actionEvent -> jogada(new Pacote(2, 2, "O")));
        Button23.addActionListener(actionEvent -> jogada(new Pacote(2, 3, "O")));
        Button31.addActionListener(actionEvent -> jogada(new Pacote(3, 1, "O")));
        Button32.addActionListener(actionEvent -> jogada(new Pacote(3, 2, "O")));
        Button33.addActionListener(actionEvent -> jogada(new Pacote(3, 3, "O")));

        new Thread(() -> {
            try {
                cliente = new Cliente(7777);
            } catch (IOException e) {
                System.out.println("Erro: ao carregar cliente");
                e.printStackTrace();
            }

            try {
                definirStatusBotaoes(false);
                Pacote j;
                while (true) {
                    j = cliente.recebePacote();
                    if (j.mensagem == Mensagem.COMECA || j.mensagem == Mensagem.SUA_VEZ) {
                        definirStatusBotaoes(true);
                        if (j.mensagem == Mensagem.SUA_VEZ) {
                            marca(j);
                        }
                    } else {
                        cliente.enviaPacote(new Pacote(Mensagem.COMECA));
                        definirStatusBotaoes(false);
                        mostraMensagem(j.mensagem);
                        zerarMatriz();
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro: ao receber pacote no cliente");
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("JogadorO");
        frame.setContentPane(new JogadorO().JogadorO);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void zerarMatriz() {
        for (JButton button : listButton) {
            button.setText("_");
        }
    }

    private void definirStatusBotaoes(boolean staus) {
        for (JButton button : listButton) {
            button.setEnabled(staus);
        }
    }

    private void jogada(Pacote pacote) {
        if (valida(pacote)) {
            try {
                cliente.enviaPacote(pacote);
                marca(pacote);
                definirStatusBotaoes(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error:  Jogada Inválida");
        }
    }

    private void marca(Pacote pacote) {
        String linha = pacote.getStringLinha();
        String coluna = pacote.getStringColuna();
        String botao = linha + coluna;
        String simbolo = pacote.getSimbolo();

        switch (botao) {
            case "11" -> Button11.setText(simbolo);
            case "12" -> Button12.setText(simbolo);
            case "13" -> Button13.setText(simbolo);
            case "21" -> Button21.setText(simbolo);
            case "22" -> Button22.setText(simbolo);
            case "23" -> Button23.setText(simbolo);
            case "31" -> Button31.setText(simbolo);
            case "32" -> Button32.setText(simbolo);
            case "33" -> Button33.setText(simbolo);
        }
    }

    private boolean valida(Pacote pacote) {
        String linha = pacote.getStringLinha();
        String coluna = pacote.getStringColuna();
        String botao = linha + coluna;

        return switch (botao) {
            case "11" -> Objects.equals(Button11.getText(), "_");
            case "12" -> Objects.equals(Button12.getText(), "_");
            case "13" -> Objects.equals(Button13.getText(), "_");
            case "21" -> Objects.equals(Button21.getText(), "_");
            case "22" -> Objects.equals(Button22.getText(), "_");
            case "23" -> Objects.equals(Button23.getText(), "_");
            case "31" -> Objects.equals(Button31.getText(), "_");
            case "32" -> Objects.equals(Button32.getText(), "_");
            case "33" -> Objects.equals(Button33.getText(), "_");
            default -> false;
        };
    }

    public void mostraMensagem(Mensagem mensagem) {
        if (mensagem == Mensagem.GANHOU) {
            JOptionPane.showMessageDialog(null, "Jogador O Venceu");
        } else if (mensagem == Mensagem.PERDEU) {
            JOptionPane.showMessageDialog(null, "Jogador O Perdeu!!");
        } else if (mensagem == Mensagem.VELHA) {
            JOptionPane.showMessageDialog(null, "Deu Velha!!");
        } else if (mensagem == Mensagem.ERRO) {
            JOptionPane.showMessageDialog(null, "Deu Erro!!");
        }
    }
}
