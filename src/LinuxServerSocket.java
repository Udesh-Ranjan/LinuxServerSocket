import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author :  dev parzival
 * @date :  11 Apr, 2021
 */
public class LinuxServerSocket {
    private static PrintStream out;
    static{
        out=System.out;
    }
    private Socket socket=null;
    private ServerSocket server=null;
    private DataInputStream din =null;
    private DataOutputStream dout=null;
    public LinuxServerSocket(int port){
        try{
            server=new ServerSocket(port);
            out.println("server>server started");
            out.println("server>waiting for client");
            socket=server.accept();
            din =new DataInputStream(new BufferedInputStream(
                    socket.getInputStream()));
            dout=new DataOutputStream(new BufferedOutputStream(
                    socket.getOutputStream()));
            String line;
            while(true){
                try{
                    line= din.readUTF();
                    out.println("client>"+line);
                    dout.writeUTF(line.toUpperCase());
                    dout.flush();
                    if(line.equalsIgnoreCase("bye"))
                        break;
                }catch(IOException exception){
                    out.println(exception);
                }
            }
            out.println("server>Closing connection");
            socket.close();
            din.close();
        }catch(Exception ex){
            out.println(ex.toString());
        }
    }
    //////////main/////////
    public static void main(String $[]){
        new LinuxServerSocket(54000);
    }
}
