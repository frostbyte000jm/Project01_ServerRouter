import java.awt.*;
import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {

        // Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
        String routerName = ServerNames.getRouterName(); //JM - 2021.10.2 was "j263-08.cse1.spsu.edu"; // ServerRouter host name
        int SockNum = 5555; // port number

        // Who am I
        InetAddress addr = InetAddress.getLocalHost();
        String localIPAddress = addr.getHostAddress(); // Machine's IP Address
        String localHostName = addr.getCanonicalHostName(); // Machine's Host Name
        System.out.println("Local Host Information\nHost Name: "+localHostName+"\nIP Address: "+localIPAddress);

        // Tries to connect to the ServerRouter
        try {
            Socket = new Socket(routerName, SockNum);
            out = new PrintWriter(Socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about router: " + routerName);
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + routerName);
            System.exit(1);
        }

        // Variables for message passing
        Reader reader = new FileReader("data/file.txt");
        FileWriter fw = new FileWriter(ServerNames.getTextFile());
        BufferedReader fromFile =  new BufferedReader(reader); // reader for the string file
        BufferedWriter toFile = new BufferedWriter(fw);
        String fromServerRouter; // messages received from ServerRouter
        String fromHere; // messages sent to ServerRouter
        String address = ServerNames.getServerAddress(); //JM 2021.10.2 - was: "10.5.2.109"; // destination IP (Server)
        long t0, t1, t;
        boolean doWriteToFile = false;
        boolean doFirstLine = true;

        // Communication process (initial sends/receives
        out.println(address);// initial send (IP of the destination Server)
        fromServerRouter = in.readLine();//initial receive from router (verification of connection)
        System.out.println("ServerRouter: " + fromServerRouter);
        out.println(localIPAddress); // Client sends the IP of its machine as initial send //JM 2021.10.2 revised with updated name
        t0 = System.currentTimeMillis();

        // Communication while loop
        while ((fromServerRouter = in.readLine()) != null) {
            System.out.println("Server Router: " + fromServerRouter);
            //start Timer
            t1 = System.currentTimeMillis();
            if (fromServerRouter.equals("Bye.")) // exit statement
                break;
            if (doWriteToFile){
                if (doFirstLine) {
                    doFirstLine = false;
                } else {
                    toFile.newLine();
                }
                toFile.write(fromServerRouter);
            }
            // stop and print timer
            t = t1 - t0;
            System.out.println("Cycle time: " + t);

            // send to Server
            fromHere = fromFile.readLine(); // reading strings from a file
            doWriteToFile = true;
            if (fromHere != null) {
                System.out.println("Client: " + fromHere);
                out.println(fromHere); // sending the strings to the Server via ServerRouter
                t0 = System.currentTimeMillis();
            }else {
                System.out.println("Client: Good Bye");
                out.println("Bye."); // sending the strings to the Server via ServerRouter
                t0 = System.currentTimeMillis();
            }
        }

        // closing connections
        fromFile.close();
        toFile.close();
        out.close();
        in.close();
        Socket.close();

        //Load File
        File file = new File(ServerNames.getTextFile());
        if (file.exists()){
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }

    }
}
