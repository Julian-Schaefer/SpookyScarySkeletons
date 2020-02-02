import 'package:app/model/JsonConvertible.dart';

class Score implements JsonConvertible {
  final String username;
  final String duration;
  final String dateTime;

  Score({this.username, this.duration, this.dateTime});

  Score.fromJSON(Map<String, dynamic> json)
      : username = json['username'],
        duration = json['duration'],
        dateTime = json['dateTime'];

  Map<String, dynamic> toJSON() => {
        'username': username,
        'duration': duration,
        'dateTime': dateTime,
      };
}
