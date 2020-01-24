import 'package:shared_preferences/shared_preferences.dart';

class Util {
  static Future<String> loadUsername() async {
    final prefs = await SharedPreferences.getInstance();
    final username = prefs.getString('username') ?? null;
    return username;
  }

  static Future<void> saveUsername(String username) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString("username", username);
    print('saved account: ' + username);
  }
}
