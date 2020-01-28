import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class TrustPainter extends CustomPainter {
  final int value;

  TrustPainter({@required this.value});

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
    if (value != (oldDelegate as TrustPainter).value) {
      return true;
    }

    return false;
  }
}
