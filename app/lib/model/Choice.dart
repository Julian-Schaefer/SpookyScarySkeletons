class Choice {
  final int id;
  final String content;

  Choice(this.id, this.content);

  Choice.fromJSON(Map<String, dynamic> json)
      : id = json['id'],
        content = json['content'];

  Map<String, dynamic> toJson() => {
        'id': id,
        'content': content,
      };
}
