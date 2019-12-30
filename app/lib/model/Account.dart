class Account {
  final String username;

  Account(this.username);

  Account.fromJSON(Map<String, dynamic> json) : username = json['username'];

  Map<String, dynamic> toJson() => {
        'username': username,
      };
}
