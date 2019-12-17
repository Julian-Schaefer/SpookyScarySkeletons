class Choice {
  final String message;
  final String firstOption;
  final String secondOption;

  Choice(this.message, this.firstOption, this.secondOption);

  Choice.fromJSON(Map<String, dynamic> json)
      : message = json['message'],
        firstOption = json['firstOption'],
        secondOption = json['secondOption'];

  Map<String, dynamic> toJson() => {
        'message': message,
        'firstOption': firstOption,
        'secondOption': secondOption
      };
}
