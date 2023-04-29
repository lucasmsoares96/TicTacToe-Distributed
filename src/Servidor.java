import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Servidor {

    private Socket clienteX;
    private ObjectOutputStream outputX;
    private ObjectInputStream inputX;

    private Socket clienteO;
    private ObjectOutputStream outputO;
    private ObjectInputStream inputO;


    public Servidor() throws IOException, ClassNotFoundException {
        iniciaServidores();
//        JOptionPane.showMessageDialog(null,"Servidor TCP em Execução");
    }

    public void finaliza() throws IOException {
        outputX.close();
        outputO.close();
        inputX.close();
        inputO.close();
        clienteX.close();
        clienteO.close();
    }

    public Pacote anunciaVelha(Pacote pacote, String jogadorAtual) throws IOException, ClassNotFoundException {
        pacote.mensagem = Mensagem.VELHA;
        Pacote j = null;
        if (Objects.equals(jogadorAtual, "X")) {
            j = mensagemX(new Pacote(Mensagem.VELHA));
            mensagemO(pacote);
        } else if (Objects.equals(jogadorAtual, "O")) {
            j = mensagemO(new Pacote(Mensagem.VELHA));
            mensagemX(pacote);
        }
        return j;
    }

    public Pacote anunciaVencedor(Pacote pacote, String jogadorAtual) throws IOException, ClassNotFoundException {
        pacote.mensagem = Mensagem.PERDEU;
        Pacote j = null;
        if (Objects.equals(jogadorAtual, "X")) {
            j = mensagemX(new Pacote(Mensagem.GANHOU));
            mensagemO(pacote);
        } else if (Objects.equals(jogadorAtual, "O")) {
            j = mensagemO(new Pacote(Mensagem.GANHOU));
            mensagemX(pacote);
        }
        return j;
    }

    public Pacote mensagemPara(Pacote o, String jogadorAtual) throws IOException, ClassNotFoundException {
        Pacote i = null;
        if (Objects.equals(jogadorAtual, "X")) {
            i = mensagemX(o);
        } else if (Objects.equals(jogadorAtual, "O")) {
            i = mensagemO(o);
        }
        return i;
    }

    public Pacote mensagemX(Pacote j) throws IOException, ClassNotFoundException {
        outputX.flush();
        outputX.writeObject(j);
        return (Pacote) inputX.readObject();
    }

    public Pacote mensagemO(Pacote j) throws IOException, ClassNotFoundException {
        outputO.flush();
        outputO.writeObject(j);
        return (Pacote) inputO.readObject();
    }

    private void iniciaServidores() throws IOException {
        // variáveis de X
        ServerSocket servidorX = new ServerSocket(8888);
        clienteX = servidorX.accept();
        outputX = new ObjectOutputStream(clienteX.getOutputStream());
        inputX = new ObjectInputStream(clienteX.getInputStream());

        // variáveis de O
        ServerSocket servidorO = new ServerSocket(7777);
        clienteO = servidorO.accept();
        outputO = new ObjectOutputStream(clienteO.getOutputStream());
        inputO = new ObjectInputStream(clienteO.getInputStream());
    }

}