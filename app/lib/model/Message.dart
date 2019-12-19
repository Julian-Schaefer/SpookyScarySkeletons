import 'Choice.dart';

class Message {
  final int id;
  final String content;
  final Choice firstChoice;
  final Choice secondChoice;

  Message(this.id, this.content, this.firstChoice, this.secondChoice);

  Message.fromJSON(Map<String, dynamic> json)
      : id = json['id'],
        content = json['content'],
        firstChoice = Choice.fromJSON(json['firstChoice']),
        secondChoice = Choice.fromJSON(json['secondChoice']);

  Map<String, dynamic> toJson() => {
        'id': id,
        'content': content,
        'firstChoice': firstChoice,
        'secondChoice': secondChoice
      };
}
