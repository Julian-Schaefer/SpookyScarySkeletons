class ScenarioEndpoint {
  final String name;
  String websocketEndpoint;
  final String backgroundImageUrl;

  ScenarioEndpoint(this.name, this.websocketEndpoint, this.backgroundImageUrl);

  ScenarioEndpoint.fromJSON(Map<String, dynamic> json)
      : name = json['name'],
        websocketEndpoint = json['websocketEndpoint'],
        backgroundImageUrl = json['backgroundImageUrl'];

  Map<String, dynamic> toJson() => {
        'name': name,
        'websocketEndpoint': websocketEndpoint,
        'backgroundImageUrl': backgroundImageUrl,
      };
}
