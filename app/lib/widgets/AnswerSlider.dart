import 'package:app/model/Choice.dart';
import 'package:flutter/material.dart';

class AnswerSlider extends StatefulWidget {
  final ThemeData themeData;
  final Function(Choice) onChoiceSelected;

  AnswerSlider({Key key, this.themeData, @required this.onChoiceSelected})
      : super(key: key);

  @override
  AnswerSliderState createState() => AnswerSliderState();
}

class AnswerSliderState extends State<AnswerSlider>
    with TickerProviderStateMixin {
  Animation<Offset> _answerAnimation;
  Animation<Offset> _choicesAnimation;

  Choice _firstChoice;
  Choice _secondChoice;

  AnimationController _answerAnimationController;
  AnimationController _choicesAnimationController;

  void setChoices(Choice firstChoice, Choice secondChoice) {
    setState(() {
      _firstChoice = firstChoice;
      _secondChoice = secondChoice;
    });

    if (firstChoice != null || secondChoice != null) {
      _answerAnimationController.forward();
    }
  }

  @override
  void initState() {
    super.initState();

    _answerAnimationController = AnimationController(
      duration: const Duration(milliseconds: 400),
      vsync: this,
    );

    _choicesAnimationController = AnimationController(
      duration: const Duration(milliseconds: 400),
      vsync: this,
    );

    _answerAnimation = Tween<Offset>(
      begin: const Offset(0.0, 2.0),
      end: Offset.zero,
    ).animate(CurvedAnimation(
        parent: _answerAnimationController, curve: new Interval(0, 1)));

    _choicesAnimation = Tween<Offset>(
      begin: const Offset(0.0, 2.0),
      end: Offset.zero,
    ).animate(CurvedAnimation(
        parent: _choicesAnimationController, curve: new Interval(0, 1)));
  }

  @override
  Widget build(BuildContext context) {
    return Stack(
      alignment: Alignment.bottomCenter,
      children: <Widget>[
        SlideTransition(
          position: _answerAnimation,
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
                _answerAnimationController
                    .reverse()
                    .whenComplete(() => _choicesAnimationController.forward());
              },
            ),
          ),
        ),
        if (_firstChoice != null || _secondChoice != null)
          SlideTransition(
            position: _choicesAnimation,
            child: Container(
              width: double.infinity,
              padding: EdgeInsets.fromLTRB(10, 0, 10, 10),
              child: Row(
                children: <Widget>[
                  Expanded(
                    child: FlatButton(
                      padding: EdgeInsets.all(10),
                      child: Text(
                        _firstChoice.content,
                        style: TextStyle(color: Colors.white, fontSize: 15),
                      ),
                      color: widget.themeData.primaryColorDark,
                      onPressed: () {
                        widget.onChoiceSelected(_firstChoice);
                        _choicesAnimationController
                            .reverse()
                            .whenComplete(() => setChoices(null, null));
                      },
                    ),
                  ),
                  if (_secondChoice != null)
                    Padding(
                      padding: EdgeInsets.all(5),
                    ),
                  if (_secondChoice != null)
                    Expanded(
                      child: FlatButton(
                        padding: EdgeInsets.all(10),
                        child: Text(
                          _secondChoice.content,
                          style: TextStyle(color: Colors.white, fontSize: 15),
                        ),
                        color: widget.themeData.primaryColorDark,
                        onPressed: () {
                          widget.onChoiceSelected(_secondChoice);
                          _choicesAnimationController.reverse();
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
    _answerAnimationController.dispose();
    _choicesAnimationController.dispose();
    super.dispose();
  }
}
