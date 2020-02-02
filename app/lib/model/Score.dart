import 'package:app/model/JsonConvertible.dart';

class Score implements JsonConvertible {
  final String username;
  final String scenario;
  final String duration;
  final String dateTime;

  Score({this.username, this.scenario, this.duration, this.dateTime});

  Score.fromJSON(Map<String, dynamic> json)
      : username = json['username'],
        scenario = json['scenario'],
        duration = json['durationString'],
        dateTime = json['dateTime'];

  Map<String, dynamic> toJSON() => {
        'username': username,
        'scenario': scenario,
        'durationString': duration,
        'dateTime': dateTime,
      };
}
