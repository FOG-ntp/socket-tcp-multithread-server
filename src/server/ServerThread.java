/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class ServerThread implements Runnable {
    //ServerThread: quản lý đóng mở và xử lý đọc và xuất dữ liệu của thread có trong server

    private Socket socketOfServer;
    private int clientNumber;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;

    public BufferedReader getIs() {
        return is;
    }

    public BufferedWriter getOs() {
        return os;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public ServerThread(Socket socketOfServer, int clientNumber) {
        // ServerThread: số thread (clientNumber) chạy trên socket tại server
        
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        isClosed = false;
    }

    @Override
    public void run() {
        try {
            // Mở thread vào ra trên Socket tại Server.
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            // Tạo Thread đầu vào tại Server (Nhận dữ liệu)
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            // Tạo Thread đầu ra tại Server (Gửi dữ liệu)
            System.out.println("Khoi dong thread moi thanh cong, ID la: " + clientNumber);
            write("get-id" + "," + this.clientNumber);
            Server.serverThreadBus.sendOnlineList();
            Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã đăng nhập---");
            //Gửi mess tới màn hình tab nhắn tin về client đã đăng nhập thông qua 
            //chuỗi global-message trong hàm multiCastSend thuộc ServerThreadBus
            String message;
            while (!isClosed) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                String[] messageSplit = message.split(",");
                //Nếu chuỗi mang giá trị send-to-global thì sẽ chạy hàm boardCast thuộc ServerThreadBus 
                //để hiển thị mess tới toàn bộ client
                if(messageSplit[0].equals("send-to-global")){
                    Server.serverThreadBus.boardCast(this.getClientNumber(),"global-message"+","+"Client "+messageSplit[2]+": "+messageSplit[1]);
                }
                //Nếu chuỗi mang giá trị send-to-person thì sẽ chạy hàm sendMessageToPersion thuộc ServerThreadBus 
                //để hiển thị mess tới khung chat client được chọn 
                if(messageSplit[0].equals("send-to-person")){
                    Server.serverThreadBus.sendMessageToPersion(Integer.parseInt(messageSplit[3]),"Client "+ messageSplit[2]+" (tới bạn): "+messageSplit[1]);
                }
            }
        } catch (IOException e) {
            isClosed = true;
            Server.serverThreadBus.remove(clientNumber);
            System.out.println(this.clientNumber+" da thoat");
            Server.serverThreadBus.sendOnlineList();
            Server.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+this.clientNumber+" đã thoát---");
        }
    }
    public void write(String message) throws IOException{
        os.write(message);
        os.newLine();
        os.flush();//xả dữ liệu đc lưu trong bộ đệm
    }
}
