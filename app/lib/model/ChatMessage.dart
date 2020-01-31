import 'package:intl/intl.dart';

class ChatMessage {
  bool isIncoming;
  String content;
  String time;

  ChatMessage(this.isIncoming, this.content) {
    DateTime now = DateTime.now();
    time = DateFormat('HH:mm').format(now);
  }
}
