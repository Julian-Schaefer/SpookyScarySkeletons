class GameOver {
  bool won;
  String message;

  GameOver({this.won, this.message});

  GameOver.fromJSON(Map<String, dynamic> json)
      : won = json['won'],
        message = json['message'];

  Map<String, dynamic> toJSON() => {
        'won': won,
        'message': message,
      };
}
