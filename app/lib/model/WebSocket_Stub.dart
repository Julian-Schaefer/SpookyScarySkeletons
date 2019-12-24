import 'WebSocket.dart';

/// Implemented in `browser_client.dart` and `io_client.dart`.
WebSocket getWebSocket() => throw UnsupportedError(
    'Cannot create a client without dart:html or dart:io.');
