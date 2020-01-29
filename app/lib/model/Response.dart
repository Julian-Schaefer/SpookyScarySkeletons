import 'package:app/model/JsonConvertible.dart';
import 'package:app/model/Message.dart';

enum ResponseType { MESSAGE, INFORMATION }

class Response {
  ResponseType type;
  Object content;

  Response({this.type, this.content});

  Response.fromJSON(Map<String, dynamic> json) {
    var typeString = json['type'];

    switch (typeString) {
      case "MESSAGE":
        type = ResponseType.MESSAGE;
        content = Message.fromJSON(json['content']);
        break;
      case "INFORMATION":
        type = ResponseType.INFORMATION;
        content = json['content'];
        break;
    }
  }

  Map<String, dynamic> toJson() {
    if (content is JsonConvertible) {
      return {
        'type': type,
        'content': (content as JsonConvertible).toJSON(),
      };
    }

    return {
      'type': type,
      'content': content,
    };
  }

  Message getMessage() {
    if (type == ResponseType.MESSAGE) {
      return content as Message;
    } else {
      throw new Exception('Is not a Message');
    }
  }

  String getInformation() {
    if (type == ResponseType.INFORMATION) {
      return content as String;
    } else {
      throw new Exception('Is not an Info');
    }
  }
}
