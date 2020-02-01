class Scenario {
  final String name;
  String websocketEndpoint;
  final String backgroundImageUrl;

  Scenario(this.name, this.websocketEndpoint, this.backgroundImageUrl);

  Scenario.fromJSON(Map<String, dynamic> json)
      : name = json['name'],
        websocketEndpoint = json['websocketEndpoint'],
        backgroundImageUrl = json['backgroundImageUrl'];

  Map<String, dynamic> toJson() => {
        'name': name,
        'websocketEndpoint': websocketEndpoint,
        'backgroundImageUrl': backgroundImageUrl,
      };
}
