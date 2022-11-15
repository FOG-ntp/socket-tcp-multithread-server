# Chat app with socket using multi-thread and built by client/server model
There are two parts of this project.
Link of client side https://github.com/FOG-ntp/Socket-TCP-MultiThread-Client
-----
Socket packet in Java doesn't support us with thread management so I write some more classes in this project to handle that.
I was inspired by socket.io - a library in the other programming language (Javascript). On it, there are many avaiable methods to handle multi-client connect to server.
### Program functions list
 - Show online users list (update realtime)
 - Chatting with global or someone (on general chat box), notice when someone online/offline
### Demo
 - Show online users list
 <p align="center">
<img width="320" alt="Ảnh chụp màn hình 2022-10-20 082230" src="https://user-images.githubusercontent.com/99815527/196834244-e7d77a42-5503-4e79-9fc9-299247462864.png">
<img width="320" alt="image" src="https://user-images.githubusercontent.com/99815527/196834317-418110fe-be36-4fb8-b2ce-9c65d4851857.png">
<img width="320" alt="image" src="https://user-images.githubusercontent.com/99815527/196834406-86868fab-4ae8-4885-aa34-e5ed0c8a26cd.png">
 </p>

 - Chatting with global or someone (on general chat box), notice when someone online/offline
  <p align="center">
 <img width="320" alt="image" src="https://user-images.githubusercontent.com/99815527/196835263-843cd29f-2a9e-4db0-8495-b53e883ef7ba.png">
 <img width="320" alt="image" src="https://user-images.githubusercontent.com/99815527/196835228-12566627-f473-466d-afcf-07a811ca6914.png">
 <img width="320" alt="image" src="https://user-images.githubusercontent.com/99815527/196835343-2f16a292-0b1a-4c50-b309-238ece27a750.png">
</p>

