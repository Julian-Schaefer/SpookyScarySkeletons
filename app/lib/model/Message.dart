import 'package:app/model/JsonConvertible.dart';

import 'Choice.dart';

class Message implements JsonConvertible {
  final int id;
  final String content;
  Choice firstChoice;
  Choice secondChoice;

  Message(this.id, this.content, this.firstChoice, this.secondChoice);

  Message.fromJSON(Map<String, dynamic> json)
      : id = json['id'],
        content = json['content'] {
    if (json['firstChoice'] != null) {
      firstChoice = Choice.fromJSON(json['firstChoice']);
    }
    if (json['secondChoice'] != null) {
      secondChoice = Choice.fromJSON(json['secondChoice']);
    }
  }

  Map<String, dynamic> toJSON() => {
        'id': id,
        'content': content,
        'firstChoice': firstChoice.toJSON(),
        'secondChoice': secondChoice.toJSON()
      };
}
