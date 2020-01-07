import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import '../App.dart';
import '../model/Account.dart';
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
      Account account = Account.fromJSON(jsonDecode(response.body));
      return account;
    } else if (response.statusCode == 409) {
      throw Exception('Selected Username is already in use.');
    } else {
      print('Error processing the request: ' + response.body);
      throw Exception('Failed to register Account.');
    }
  }

  void onCreateAccount() {
    setState(() {
      _createdAccount = _createAccount(_usernameController.text);
    });
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
                    Text(
                        'Account ${snapshot.data.username} has been successfully created!'),
                    Padding(
                      padding: EdgeInsets.only(top: 20),
                    ),
                    MaterialButton(
                      child: Text(
                        'Show Scenarios',
                        style: TextStyle(color: Colors.white),
                      ),
                      onPressed: () {
                        Navigator.pushReplacement(
                            context,
                            MaterialPageRoute(
                                builder: (context) => StartScreen()));
                      },
                      color: Theme.of(context).primaryColorDark,
                    ),
                  ],
                );
              } else {
                // By default, show a loading spinner.
                return Form(
                    key: _formKey,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        TextFormField(
                          decoration: const InputDecoration(
                            hintText: 'Please select a Username',
                          ),
                          validator: (value) {
                            if (value.isEmpty) {
                              return 'Username cannot be empty.';
                            }
                            return null;
                          },
                          controller: _usernameController,
                        ),
                        if (snapshot.connectionState != ConnectionState.waiting)
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 16.0),
                            child: MaterialButton(
                              onPressed: () {
                                if (_formKey.currentState.validate()) {
                                  onCreateAccount();
                                }
                              },
                              child: Text('Create Account',
                                  style: TextStyle(color: Colors.white)),
                              color: Theme.of(context).primaryColorDark,
                            ),
                          ),
                        if (snapshot.connectionState == ConnectionState.waiting)
                          Center(
                            child: Container(
                              margin: EdgeInsets.only(top: 20),
                              child: CircularProgressIndicator(),
                            ),
                          ),
                        if (snapshot.hasError) Text("${snapshot.error}"),
                      ],
                    ));
              }
            }),
      ),
    );
  }
}
