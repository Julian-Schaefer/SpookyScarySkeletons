import 'package:app/model/Message.dart';

class Choice {
  final int id;
  final String content;
  final int value;
  final Message nextMessage;

  Choice(this.id, this.content, this.value, this.nextMessage);

  Choice.fromJSON(Map<String, dynamic> json)
      : id = json['id'],
        content = json['content'],
        value = json['value'],
        nextMessage = json['nextMessage'];

  Map<String, dynamic> toJson() => {
        'id': id,
        'content': content,
        'value': value,
        'nextMessage': nextMessage
      };
}
