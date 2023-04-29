import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private final int porta;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Cliente(int porta) throws IOException {
        this.porta = porta;
        inicia();
//        JOptionPane.showMessageDialog(null,"Cliente TCP em Execução");
    }

    private void inicia() throws IOException {
        Socket client = new Socket("127.0.0.1", porta);
        input = new ObjectInputStream(client.getInputStream());
        output = new ObjectOutputStream(client.getOutputStream());
    }

    private void finaliza() throws IOException {
        input.close();
        output.close();
    }

    public Pacote recebePacote() throws IOException, ClassNotFoundException {
        return (Pacote) input.readObject();
    }

    public void enviaPacote(Pacote pacote) throws IOException {
        output.writeObject(pacote);
    }
}