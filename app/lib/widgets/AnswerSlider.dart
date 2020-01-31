import 'package:app/model/Choice.dart';
import 'package:flutter/material.dart';

class AnswerSlider extends StatefulWidget {
  final ThemeData themeData;
  final Choice firstChoice;
  final Choice secondChoice;
  final Function(Choice) onChoiceSelected;

  const AnswerSlider(
      {this.themeData,
      @required this.firstChoice,
      @required this.secondChoice,
      @required this.onChoiceSelected});

  @override
  _AnswerSliderState createState() => _AnswerSliderState();
}

class _AnswerSliderState extends State<AnswerSlider>
    with SingleTickerProviderStateMixin {
  Animation<Offset> _outAnimation;
  Animation<Offset> _inAnimation;

  AnimationController _animationController;

  @override
  void initState() {
    super.initState();

    _animationController = AnimationController(
      duration: const Duration(milliseconds: 800),
      vsync: this,
    );

    _outAnimation = Tween<Offset>(
      begin: Offset.zero,
      end: const Offset(0.0, 2.0),
    ).animate(CurvedAnimation(
        parent: _animationController, curve: new Interval(0, 0.4)));

    _inAnimation = Tween<Offset>(
      begin: const Offset(0.0, 2.0),
      end: Offset.zero,
    ).animate(CurvedAnimation(
        parent: _animationController, curve: new Interval(0.6, 1)));
  }

  @override
  Widget build(BuildContext context) {
    if (widget.firstChoice == null && widget.secondChoice == null) {
      return SizedBox.shrink();
    }

    return Stack(
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
              color: widget.themeData.primaryColorDark,
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
                    padding: EdgeInsets.all(10),
                    child: Text(
                      widget.firstChoice.content,
                      style: TextStyle(color: Colors.white, fontSize: 15),
                    ),
                    color: widget.themeData.primaryColorDark,
                    onPressed: () {
                      widget.onChoiceSelected(widget.firstChoice);
                      _animationController.reverse();
                    },
                  ),
                ),
                if (widget.secondChoice != null)
                  Padding(
                    padding: EdgeInsets.all(5),
                  ),
                if (widget.secondChoice != null)
                  Expanded(
                    child: FlatButton(
                      padding: EdgeInsets.all(10),
                      child: Text(
                        widget.secondChoice.content,
                        style: TextStyle(color: Colors.white, fontSize: 15),
                      ),
                      color: widget.themeData.primaryColorDark,
                      onPressed: () {
                        widget.onChoiceSelected(widget.secondChoice);
                        _animationController.reverse();
                      },
                    ),
                  ),
              ],
            ),
          ),
        ),
      ],
    );
  }

  @override
  void dispose() {
    super.dispose();
    _animationController.dispose();
  }
}
