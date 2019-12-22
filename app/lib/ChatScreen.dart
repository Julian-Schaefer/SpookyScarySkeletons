import 'dart:convert';
import 'package:flutter/foundation.dart';

import 'package:app/ChatMessage.dart';
import 'package:app/model/Message.dart';
import 'package:app/model/ScenarioEndpoint.dart';
import 'package:flutter/material.dart';
import 'package:web_socket_channel/io.dart';
import 'package:web_socket_channel/html.dart';

class ChatScreen extends StatefulWidget {
  final ScenarioEndpoint scenarioEndpoint;

  const ChatScreen({Key key, this.scenarioEndpoint}) : super(key: key);

  @override
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen>
    with SingleTickerProviderStateMixin {
  List<ChatMessage> _messages = createChatMessages();
  String _firstChoice = "";
  String _secondChoice = "";

  var _channel;

  AnimationController _animationController;
  Animation<Offset> _outAnimation;
  Animation<Offset> _inAnimation;

  final TextEditingController textEditingController =
      new TextEditingController();
  final ScrollController listScrollController = new ScrollController();

  @override
  void initState() {
    super.initState();

    if (kIsWeb) {
      _channel = HtmlWebSocketChannel.connect(
          "ws://localhost:8080/" + widget.scenarioEndpoint.websocketEndpoint);
    } else {
      _channel = IOWebSocketChannel.connect(
          "ws://10.0.2.2:8080/" + widget.scenarioEndpoint.websocketEndpoint);
    }

    _animationController = AnimationController(
      duration: const Duration(milliseconds: 800),
      vsync: this,
    );

    _outAnimation = Tween<Offset>(
      begin: Offset.zero,
      end: const Offset(0.0, 1.0),
    ).animate(CurvedAnimation(
        parent: _animationController, curve: new Interval(0, 0.4)));

    _inAnimation = Tween<Offset>(
      begin: const Offset(0.0, 1.0),
      end: Offset.zero,
    ).animate(CurvedAnimation(
        parent: _animationController, curve: new Interval(0.6, 1)));

    _channel.stream.listen((response) {
      var message = Message.fromJSON(jsonDecode(response));

      setState(() {
        _firstChoice = message.firstChoice.content;
        _secondChoice = message.secondChoice.content;

        _messages.insert(0, ChatMessage(true, message.content, "now"));
        listScrollController.animateTo(0.0,
            duration: Duration(milliseconds: 300), curve: Curves.easeOut);
      });
    });
  }

  @override
  void dispose() {
    super.dispose();
    _animationController.dispose();
  }

  void sendToWebSocket(String message) {
    _channel.sink.add(message);
  }

  void onSendMessage(String message) {
    setState(() {
      _messages.insert(0, ChatMessage(false, message, "now"));
    });

    sendToWebSocket(message);

    textEditingController.clear();
    listScrollController.animateTo(0.0,
        duration: Duration(milliseconds: 300), curve: Curves.easeOut);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Unknown number"),
      ),
      body: Container(
        decoration: BoxDecoration(
          image: DecorationImage(
            image: AssetImage("assets/wrongnumber_background.jpg"),
            fit: BoxFit.cover,
          ),
        ),
        child: Column(
          children: <Widget>[
            // List of messages
            buildListMessage(),
            Stack(
              alignment: Alignment.bottomCenter,
              children: <Widget>[
                SlideTransition(
                  position: _outAnimation,
                  child: Container(
                    width: double.infinity,
                    height: 100,
                    child: FlatButton(
                      child: Text(
                        'Antworten',
                        style: TextStyle(
                          color: Colors.white,
                          fontSize: 20,
                        ),
                      ),
                      color: Theme.of(context).primaryColorDark,
                      onPressed: () {
                        _animationController.forward();
                      },
                    ),
                  ),
                ),
                SlideTransition(
                  position: _inAnimation,
                  child: Container(
                    width: double.infinity,
                    padding: EdgeInsets.fromLTRB(10, 0, 10, 10),
                    child: Row(
                      children: <Widget>[
                        Expanded(
                          child: FlatButton(
                            child: Text(
                              _firstChoice,
                              style:
                                  TextStyle(color: Colors.white, fontSize: 15),
                            ),
                            color: Theme.of(context).primaryColorDark,
                            onPressed: () {
                              _animationController.reverse();
                              onSendMessage('hallo');
                            },
                          ),
                        ),
                        Padding(
                          padding: EdgeInsets.all(5),
                        ),
                        Expanded(
                          child: FlatButton(
                            child: Text(
                              _secondChoice,
                              style:
                                  TextStyle(color: Colors.white, fontSize: 15),
                            ),
                            color: Theme.of(context).primaryColorDark,
                            onPressed: () {
                              _animationController.reverse();
                            },
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
            // Input content
            //buildInput(),
          ],
        ),
      ),
    );
  }

  Widget buildListMessage() {
    return Flexible(
      child: ListView.builder(
        padding: EdgeInsets.all(10.0),
        itemBuilder: (context, index) => buildItem(index, _messages[index]),
        itemCount: _messages.length,
        reverse: true,
        controller: listScrollController,
      ),
    );
  }

  Widget buildInput() {
    return Container(
      child: Row(
        children: <Widget>[
          // Edit text
          Flexible(
            child: Container(
              child: TextField(
                style: TextStyle(fontSize: 15.0),
                controller: textEditingController,
                decoration: InputDecoration.collapsed(
                  hintText: 'Type your message...',
                  hintStyle: TextStyle(color: Colors.grey),
                ),
              ),
              padding: EdgeInsets.only(left: 15),
            ),
          ),

          // Button send message
          Material(
            child: new Container(
              margin: new EdgeInsets.symmetric(horizontal: 8.0),
              child: new IconButton(
                icon: new Icon(Icons.send),
                onPressed: () => onSendMessage(textEditingController.text),
                color: Theme.of(context).primaryColor,
              ),
            ),
            color: Colors.white,
          ),
        ],
      ),
      width: double.infinity,
      height: 50.0,
      decoration: new BoxDecoration(
          border:
              new Border(top: new BorderSide(color: Colors.grey, width: 0.5)),
          color: Colors.white),
    );
  }

  Widget buildItem(int index, ChatMessage message) {
    if (!message.isIncoming) {
      // Right (my message)
      return Row(
        children: <Widget>[
          Container(
            child: Column(
              children: <Widget>[
                Container(
                  child: Text(
                    message.content,
                    style: TextStyle(color: Theme.of(context).primaryColor),
                  ),
                  margin: EdgeInsets.only(left: 10.0),
                  width: 200.0,
                ),
                Container(
                  child: Text(
                    message.time,
                    style: TextStyle(
                        color: Theme.of(context).primaryColor,
                        fontSize: 12.0,
                        fontStyle: FontStyle.italic),
                    textAlign: TextAlign.end,
                  ),
                  width: 200.0,
                ),
              ],
            ),
            padding: EdgeInsets.fromLTRB(15.0, 10.0, 15.0, 10.0),
            width: 200.0,
            decoration: BoxDecoration(
                color: Color.fromRGBO(222, 222, 222, 1),
                borderRadius: BorderRadius.circular(8.0)),
            margin: EdgeInsets.only(
                bottom: isLastMessageRight(index) ? 20.0 : 10.0, right: 10.0),
          )
        ],
        mainAxisAlignment: MainAxisAlignment.end,
      );
    } else {
      // Left (peer message)
      return Container(
        child: Column(
          children: <Widget>[
            Container(
              child: Column(
                children: <Widget>[
                  Container(
                    child: Text(
                      message.content,
                      style: TextStyle(color: Colors.white),
                    ),
                    margin: EdgeInsets.only(left: 10.0),
                    width: 200.0,
                  ),
                  Container(
                    child: Text(
                      message.time,
                      style: TextStyle(
                          color: Colors.white,
                          fontSize: 12.0,
                          fontStyle: FontStyle.italic),
                      textAlign: TextAlign.end,
                    ),
                    width: 200.0,
                  ),
                ],
              ),
              padding: EdgeInsets.fromLTRB(15.0, 10.0, 15.0, 10.0),
              width: 200.0,
              decoration: BoxDecoration(
                  color: Theme.of(context).primaryColor,
                  borderRadius: BorderRadius.circular(8.0)),
            )
          ],
          crossAxisAlignment: CrossAxisAlignment.start,
        ),
        margin: EdgeInsets.only(bottom: 10.0),
      );
    }
  }

  bool isLastMessageLeft(int index) {
    if ((index > 0 && _messages != null && _messages[index - 1].isIncoming) ||
        index == 0) {
      return true;
    } else {
      return false;
    }
  }

  bool isLastMessageRight(int index) {
    if ((index > 0 && _messages != null && !_messages[index - 1].isIncoming) ||
        index == 0) {
      return true;
    } else {
      return false;
    }
  }
}
