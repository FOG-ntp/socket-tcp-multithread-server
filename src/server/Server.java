/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Admin
 */
public class Server {
    //Main Server

    public static volatile ServerThreadBus serverThreadBus;
    public static Socket socketOfServer;

    public static void main(String[] args) {
        ServerSocket listener = null;
        serverThreadBus = new ServerThreadBus();
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;

        // Mở một ServerSocket tại cổng 7000.

        try {
            listener = new ServerSocket(7000);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(//Tùy biến số lượng Thread 
                10, // corePoolSize: Số lượng thread mặc định mà thread pool cho phép chạy cùng lúc
                100, // maximumPoolSize: Số lượng thread có thể chay trong pool
                10,//thread timeout=10seconds          
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8) // queueCapacity
        );
        try {
            while (true) {
                // Chấp nhận một yêu cầu kết nối từ phía Client.
                // Đồng thời nhận được một đối tượng Socket tại server.
                socketOfServer = listener.accept();//Socket của server đó đc accept connection
                ServerThread serverThread = new ServerThread(socketOfServer, clientNumber++);
                //khi connect accept sẽ khởi tạo ServerThread và đồng thời tăng số lượng client(clientNumber) 
                serverThreadBus.add(serverThread);//serverThreadBus thực hiện thêm thread có trong server
                System.out.println("So thread dang chay la: "+serverThreadBus.getLength());
                //In ra số thread đang chạy thông qua số lượng thread có trong sẻverthreadBus
                executor.execute(serverThread);//request từ serverThread và tạo 1 socket phía server
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                listener.close();//Đóng ServerSocket tại cổng 7000
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
