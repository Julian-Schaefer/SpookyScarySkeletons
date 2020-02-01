import 'package:app/model/JsonConvertible.dart';

class Choice implements JsonConvertible {
  final int id;
  final String content;

  Choice(this.id, this.content);

  Choice.fromJSON(Map<String, dynamic> json)
      : id = json['id'],
        content = json['content'];

  Map<String, dynamic> toJSON() => {
        'id': id,
        'content': content,
      };
}
