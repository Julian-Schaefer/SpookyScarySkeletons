import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../App.dart';
import '../model/Account.dart';
import 'ChatScreen.dart';
import 'StartScreen.dart';

class AccountScreen extends StatefulWidget {
  @override
  _AccountScreenState createState() => _AccountScreenState();
}

class _AccountScreenState extends State<AccountScreen> {
  final _formKey = GlobalKey<FormState>();
  final _usernameController = TextEditingController();

  Future<Account> _createdAccount;

  Future<Account> _createAccount(String username) async {
    http.Response response;

    Account account = Account(username);

    response = await http.post(baseUrl + '/api/account',
        headers: {"Content-Type": "application/json"},
        body: json.encode(account.toJson()));

    if (response.statusCode == 200) {
      return Account.fromJSON(jsonDecode(response.body));
    } else {
      print('Error processing the request: ' + response.body);
      throw Exception('Failed to load post');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Spooky Scary Skeletons'),
      ),
      body: Center(
        child: FutureBuilder<Account>(
            future: _createdAccount,
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                return Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    RaisedButton(
                      child: Text(snapshot.data.username),
                      onPressed: () {
                        Navigator.pushReplacement(
                            context,
                            MaterialPageRoute(
                                builder: (context) => StartScreen()));
                      },
                    ),
                  ],
                );
              } else if (snapshot.hasError) {
                return Text("${snapshot.error}");
              } else {
                // By default, show a loading spinner.
                return Form(
                    key: _formKey,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        TextFormField(
                          decoration: const InputDecoration(
                            hintText: 'Please enter your username',
                          ),
                          validator: (value) {
                            if (value.isEmpty) {
                              return 'Your username cannot be empty.';
                            }
                            return null;
                          },
                          controller: _usernameController,
                        ),
                        Padding(
                          padding: const EdgeInsets.symmetric(vertical: 16.0),
                          child: RaisedButton(
                            onPressed: () {
                              if (_formKey.currentState.validate()) {
                                _createdAccount =
                                    _createAccount(_usernameController.text);
                              }
                            },
                            child: Text('Submit'),
                          ),
                        ),
                      ],
                    ));
              }
            }),
      ),
    );
  }
}
