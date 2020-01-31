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
        Container(
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.all(Radius.circular(12)),
            border: Border.all(color: Theme.of(context).primaryColor, width: 5),
            boxShadow: [
              BoxShadow(
                color: Colors.grey.withOpacity(0.7),
                spreadRadius: 4,
                blurRadius: 4,
                offset: Offset(0, 0), // changes position of shadow
              ),
            ],
          ),
          child: CustomPaint(
            size: Size(60, 240),
            // Values reach from 100 to -100!
            foregroundPainter: ScaleWidgetPainter(value: value),
          ),
        ),
        Padding(
          padding: EdgeInsets.all(6),
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
    var backgroundRect = RRect.fromRectAndRadius(
        Rect.fromLTWH(0, size.height, size.width, 0), Radius.circular(15.0));
    canvas.drawRRect(backgroundRect, backgroundPaint);

    Paint foregroundPaint = Paint();
    double top;
    double bottom;
    double halfSize = (size.height) / 2;
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

    canvas.drawRect(Rect.fromLTRB(0, top, size.width, bottom), foregroundPaint);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    if (value != (oldDelegate as ScaleWidgetPainter).value) {
      return true;
    }

    return false;
  }
}
