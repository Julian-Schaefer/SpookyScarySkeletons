class Scenario {
  final String name;
  String websocketEndpoint;
  final String previewImageUrl;
  final String backgroundImageUrl;

  Scenario(this.name, this.websocketEndpoint, this.previewImageUrl, this.backgroundImageUrl);

  Scenario.fromJSON(Map<String, dynamic> json)
      : name = json['name'],
        websocketEndpoint = json['websocketEndpoint'],
        previewImageUrl = json['previewImageUrl'],
        backgroundImageUrl = json['backgroundImageUrl'];

  Map<String, dynamic> toJson() => {
        'name': name,
        'websocketEndpoint': websocketEndpoint,
        'previewImageUrl': previewImageUrl,
        'backgroundImageUrl': backgroundImageUrl
      };
}
