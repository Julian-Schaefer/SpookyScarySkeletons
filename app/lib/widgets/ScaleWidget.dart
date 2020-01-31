import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class ScaleWidget extends StatelessWidget {
  final String text;
  final int value;

  ScaleWidget({@required this.text, @required this.value});

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        CustomPaint(
          size: Size(80, 280),
          // Values reach from 100 to -100!
          foregroundPainter: ScaleWidgetPainter(value: value),
        ),
        Text(
          "$text: $value",
          style: TextStyle(color: Colors.white, fontSize: 20),
        ),
      ],
    );
  }
}

class ScaleWidgetPainter extends CustomPainter {
  final int value;

  ScaleWidgetPainter({@required this.value});

  @override
  void paint(Canvas canvas, Size size) {
    final backgroundPaint = Paint()..color = Colors.grey;
    canvas.drawRect(
        Rect.fromLTRB(0, size.height, size.width, 0), backgroundPaint);

    Paint foregroundPaint = Paint();
    double top;
    double bottom;
    double halfSize = (size.height - 20) / 2;
    if (value > 0) {
      top = size.center(Offset.zero).dy - halfSize * (value / 100);
      bottom = size.center(Offset.zero).dy;
      foregroundPaint.color = Colors.green;
    } else {
      top = size.center(Offset.zero).dy;
      double calc = halfSize * (value / -100);
      bottom = size.center(Offset.zero).dy + calc;
      foregroundPaint.color = Colors.red;
    }

    canvas.drawRect(
        Rect.fromLTRB(10, top, size.width - 10, bottom), foregroundPaint);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    if (value != (oldDelegate as ScaleWidgetPainter).value) {
      return true;
    }

    return false;
  }
}
