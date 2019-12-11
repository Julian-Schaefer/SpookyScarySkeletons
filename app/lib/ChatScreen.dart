import 'package:app/ChatMessage.dart';
import 'package:flutter/material.dart';
import 'package:web_socket_channel/io.dart';
import 'package:web_socket_channel/status.dart' as status;

class ChatScreen extends StatefulWidget {
  @override
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
  List<ChatMessage> messages = createChatMessages();

  final TextEditingController textEditingController =
      new TextEditingController();
  final ScrollController listScrollController = new ScrollController();

  void sendToWebSocket(String message) {
    final channel =
        IOWebSocketChannel.connect("ws://10.0.2.2:8080/api/websocket");

    channel.sink.add(message);

    channel.stream.listen((response) {
      setState(() {
        messages.insert(0, ChatMessage(true, response, "now"));
        listScrollController.animateTo(0.0,
            duration: Duration(milliseconds: 300), curve: Curves.easeOut);
      });
      channel.sink.close(status.goingAway);
    });
  }

  void onSendMessage(String message) {
    setState(() {
      messages.insert(0, ChatMessage(false, message, "now"));
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
      body: Stack(
        children: <Widget>[
          Column(
            children: <Widget>[
              // List of messages
              buildListMessage(),
              // Input content
              buildInput(),
            ],
          )
        ],
      ),
    );
  }

  Widget buildListMessage() {
    return Flexible(
      child: ListView.builder(
        padding: EdgeInsets.all(10.0),
        itemBuilder: (context, index) => buildItem(index, messages[index]),
        itemCount: messages.length,
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
    if ((index > 0 && messages != null && messages[index - 1].isIncoming) ||
        index == 0) {
      return true;
    } else {
      return false;
    }
  }

  bool isLastMessageRight(int index) {
    if ((index > 0 && messages != null && !messages[index - 1].isIncoming) ||
        index == 0) {
      return true;
    } else {
      return false;
    }
  }
}
