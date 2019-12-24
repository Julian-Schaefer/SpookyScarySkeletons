class ChatMessage {
  bool isIncoming;
  String content;
  String time;

  ChatMessage(this.isIncoming, this.content, this.time);
}

List<ChatMessage> createChatMessages() {
  return [
    ChatMessage(true, "erste", "12:33"),
    ChatMessage(true, "erste2", "23:22"),
    ChatMessage(true, "erste3", "11:23"),
    ChatMessage(false, "zweite", "23:22"),
    ChatMessage(true, "erste4", "23:22"),
    ChatMessage(false, "zweite56", "23:22"),
    ChatMessage(false, "zweite66", "23:22"),
    ChatMessage(true, "erste1", "08:33"),
    ChatMessage(true, "erste2", "07:32"),
  ];
}
