import 'package:web_socket_channel/io.dart';
import 'package:app/model/WebSocket.dart';

class MobileWebSocket implements WebSocket {
  IOWebSocketChannel _channel;

  void connect(String endpoint) {
    _channel = IOWebSocketChannel.connect("ws://10.0.2.2:8080" + endpoint);
  }

  Stream<dynamic> getStream() {
    return _channel.stream;
  }

  void send(dynamic data) {
    _channel.sink.add(data);
  }
}

WebSocket getWebSocket() => new MobileWebSocket();
