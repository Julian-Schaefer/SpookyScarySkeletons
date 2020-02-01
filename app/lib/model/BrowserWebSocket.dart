import 'package:app/App.dart';
import 'package:web_socket_channel/html.dart';
import 'package:app/model/WebSocket.dart';

class BrowserWebSocket implements WebSocket {
  HtmlWebSocketChannel _channel;

  void connect(String endpoint) {
    _channel = HtmlWebSocketChannel.connect(getBaseUrlWS() + endpoint);
  }

  Stream<dynamic> getStream() {
    return _channel.stream;
  }

  void send(dynamic data) {
    _channel.sink.add(data);
  }

  void close() {
    _channel.sink.close();
  }
}

WebSocket getWebSocket() => new BrowserWebSocket();
