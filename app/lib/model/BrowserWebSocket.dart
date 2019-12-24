import 'package:web_socket_channel/html.dart';
import 'package:app/model/WebSocket.dart';

class BrowserWebSocket implements WebSocket {
  HtmlWebSocketChannel _channel;

  void connect(String endpoint) {
    _channel = HtmlWebSocketChannel.connect("ws://localhost:8080" + endpoint);
  }

  Stream<dynamic> getStream() {
    return _channel.stream;
  }

  void send(dynamic data) {
    _channel.sink.add(data);
  }
}

WebSocket getWebSocket() => new BrowserWebSocket();
