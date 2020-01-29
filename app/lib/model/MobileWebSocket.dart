import 'package:app/App.dart';
import 'package:web_socket_channel/io.dart';
import 'package:app/model/WebSocket.dart';

class MobileWebSocket implements WebSocket {
  IOWebSocketChannel _channel;

  void connect(String endpoint) {
    _channel = IOWebSocketChannel.connect(getBaseUrlWS() + endpoint);
  }

  Stream<dynamic> getStream() {
    return _channel.stream;
  }

  void send(dynamic data) {
    _channel.sink.add(data);
  }
}

WebSocket getWebSocket() => new MobileWebSocket();
