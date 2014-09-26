import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Elvira on 26.09.14.
 */
public class Server {
    private Integer port = new Integer(0);
    private ServerSocket serverSocket;
    Server (Integer port){
        this.port = port;
        try{
            serverSocket = new ServerSocket(this.port);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("It starts on port " + port);
    }

    public void start() throws Throwable {
        MyExecutorService workQueue = new MyExecutorService(Runtime.getRuntime().availableProcessors());
        while (true) {
            Socket socket = this.serverSocket.accept();
            workQueue.execute(new HTTPconnector(socket));
        }
    }

}
