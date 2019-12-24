import 'WebSocket_Stub.dart'
    // ignore: uri_does_not_exist
    if (dart.library.io) 'package:app/model/MobileWebSocket.dart'
    // ignore: uri_does_not_exist
    if (dart.library.html) 'package:app/model/BrowserWebSocket.dart';

abstract class WebSocket {
  void connect(String endpoint);

  Stream<dynamic> getStream();

  void send(dynamic data);

  /// factory constructor to return the correct implementation.
  factory WebSocket() => getWebSocket();
}
