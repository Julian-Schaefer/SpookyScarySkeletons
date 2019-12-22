class ScenarioEndpoint {
  final String name;
  final String websocketEndpoint;

  ScenarioEndpoint(this.name, this.websocketEndpoint);

  ScenarioEndpoint.fromJSON(Map<String, dynamic> json)
      : name = json['name'],
        websocketEndpoint = json['websocketEndpoint'];

  Map<String, dynamic> toJson() => {
        'name': name,
        'websocketEndpoint': websocketEndpoint,
      };
}
