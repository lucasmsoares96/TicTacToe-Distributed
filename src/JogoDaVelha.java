import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class JogoDaVelha {
    private final int[][] matriz = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    private String jogadorAtual;

    public JogoDaVelha() throws IOException, ClassNotFoundException {
        Servidor servidor = new Servidor();
        jogadorAtual = "X";

        Pacote j = new Pacote(Mensagem.COMECA);
        Mensagem v;

        int partida = JOptionPane.YES_OPTION;
        int i = 0;
        while (i < 9 && partida == JOptionPane.YES_OPTION) {
            i++;
            j = servidor.mensagemPara(j, jogadorAtual); // para o jogador atual;
            v = verifica(j); // a resposta do jogador atual;
            if (v == Mensagem.SUA_VEZ) {
                trocaJogador();
            } else if (v == Mensagem.GANHOU) {
                j = servidor.anunciaVencedor(j, jogadorAtual);
                partida = JOptionPane.showConfirmDialog(null,
                        "Deseja iniciar uma nova partida", "Servidor",
                        JOptionPane.YES_NO_OPTION);
                zerarMatriz();
                i = 0;
            }
            if (i == 8) {
                j = servidor.anunciaVelha(j, jogadorAtual);
                partida = JOptionPane.showConfirmDialog(null,
                        "Deseja iniciar uma nova partida", "Servidor",
                        JOptionPane.YES_NO_OPTION);
                zerarMatriz();
                i = 0;
            }

        }
        servidor.finaliza();
    }

    public static void main(String[] args) {
        try {
            new JogoDaVelha();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void trocaJogador() {
        if (Objects.equals(jogadorAtual, "X")) {
            jogadorAtual = "O";
        } else {
            jogadorAtual = "X";
        }
    }

    private void zerarMatriz() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    public Mensagem verifica(Pacote pacote) {
        int c = pacote.coluna;
        int l = pacote.linha;
        int s = pacote.simbolo;
        int somaLinha = 0;
        int somaColuna = 0;
        int somaDiagonalP = 0;
        int somaDiagonalS = 0;

        if (matriz[l][c] == 0) {
            matriz[l][c] = s;
        } else {
            return Mensagem.ERRO;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                somaLinha += matriz[i][j];
                somaColuna += matriz[j][i];
            }
            somaDiagonalP += matriz[i][i];
            somaDiagonalS += matriz[i][2 - i];
            if (somaLinha == 3
                    || somaLinha == -3
                    || somaColuna == 3
                    || somaColuna == -3
                    || somaDiagonalP == 3
                    || somaDiagonalP == -3
                    || somaDiagonalS == 3
                    || somaDiagonalS == -3) {
                return Mensagem.GANHOU;
            }

            somaLinha = 0;
            somaColuna = 0;
        }
        return Mensagem.SUA_VEZ;
    }
}
