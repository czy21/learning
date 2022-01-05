#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>
 
#define buttonPin D3            // 按钮引脚D3
 
ESP8266WiFiMulti wifiMulti;
 
bool buttonState;
float clientFloatValue;
int clientIntValue;
 
const char* host = "192.168.2.32";
const int httpPort = 5000;
 
void setup(void){
  Serial.begin(9600);
  Serial.println("");
 
  pinMode(buttonPin, INPUT_PULLUP);
  
  wifiMulti.addAP("Bruce-Net-Side", "czy805899926.+-"); 
  Serial.println("Connecting ...");
  
  while (wifiMulti.run() != WL_CONNECTED) {
    delay(250);
    Serial.print('.');
  }
 
  Serial.print("Connected to ");
  Serial.println(WiFi.SSID());
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}
 
void loop(void){
 // 获取按键引脚状态
 buttonState = digitalRead(buttonPin); 
 
 // 改变测试用变量数值用于服务器端接收数据检测
 clientFloatValue += 1.5;
 clientIntValue += 2;
 
 // 发送请求
 wifiClientRequest();
 delay(1000);
}
 
void wifiClientRequest(){
  WiFiClient client;
  String url = "/update?float=" + String(clientFloatValue) + "&int=" + String(clientIntValue) + "&button=" + String(buttonState);
  String httpRequest =  String("GET ") + url + " HTTP/1.1\r\n" +"Host: " + host + "\r\n" +"Connection: close\r\n" +"\r\n";
                        
  Serial.print("Connecting to "); 
  Serial.print(host); 
  
  if (client.connect(host, httpPort)) {
    Serial.println(" Sucess");
    client.print(httpRequest);
    Serial.println("Sending request: ");
    Serial.println(httpRequest);
  } else{
    Serial.println(" Failed");
  }
  
  client.stop();                         
}
