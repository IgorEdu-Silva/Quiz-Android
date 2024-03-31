package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class quizGeography extends AppCompatActivity {

    private TextView textView;
    private String[] questions;
    private String[][] answers;
    private int currentQuestionIndex = 0;
    private ProgressBar progressBar;
    private final int[] selectedAnswers = new int[10];
    private final int[] correctAnswers = {2, 1, 3, 1, 0, 3, 2, 0, 1, 2};
    private RadioButton[] answerRadioButtons;
    private boolean pontuacaoAtingida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_geography);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(quizGeography.this, SelectGame.class);
            startActivity(intent);
        });


        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);

        questions = getResources().getStringArray(R.array.geography_questions);
        if (questions != null) {
            progressBar.setMax(questions.length);
        }

        Button btnPrevious = findViewById(R.id.btnPrevious);
        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);

        questions = getResources().getStringArray(R.array.geography_questions);
        answers = new String[][]{
                getResources().getStringArray(R.array.geography_answers_1),
                getResources().getStringArray(R.array.geography_answers_2),
                getResources().getStringArray(R.array.geography_answers_3),
                getResources().getStringArray(R.array.geography_answers_4),
                getResources().getStringArray(R.array.geography_answers_5),
                getResources().getStringArray(R.array.geography_answers_6),
                getResources().getStringArray(R.array.geography_answers_7),
                getResources().getStringArray(R.array.geography_answers_8),
                getResources().getStringArray(R.array.geography_answers_9),
                getResources().getStringArray(R.array.geography_answers_10)
        };

        setQuestionAndAnswers();
        btnPrevious.setOnClickListener(v -> goToPreviousQuestion());

        btnNext.setOnClickListener(v -> goToNextQuestion());
    }


    private void setQuestionAndAnswers() {
        textView.setText(questions[currentQuestionIndex]);
        String[] currentAnswers = answers[currentQuestionIndex];

        RadioButton firstAnswerRadioButton = findViewById(R.id.FirstAnswerRadioButton);
        RadioButton secondAnswerRadioButton = findViewById(R.id.SecondAnswerRadioButton);
        RadioButton thirdAnswerRadioButton = findViewById(R.id.ThirdAnswerRadioButton);
        RadioButton forthAnswerRadioButton = findViewById(R.id.ForthAnswerRadioButton);

        firstAnswerRadioButton.setText(currentAnswers[0]);
        secondAnswerRadioButton.setText(currentAnswers[1]);
        thirdAnswerRadioButton.setText(currentAnswers[2]);
        forthAnswerRadioButton.setText(currentAnswers[3]);

        answerRadioButtons = new RadioButton[]{firstAnswerRadioButton, secondAnswerRadioButton,
                thirdAnswerRadioButton, forthAnswerRadioButton};

        firstAnswerRadioButton.setOnClickListener(view -> updateSelectedAnswer(0));
        secondAnswerRadioButton.setOnClickListener(view -> updateSelectedAnswer(1));
        thirdAnswerRadioButton.setOnClickListener(view -> updateSelectedAnswer(2));
        forthAnswerRadioButton.setOnClickListener(view -> updateSelectedAnswer(3));
    }

    private void updateSelectedAnswer(int answerIndex) {
        selectedAnswers[currentQuestionIndex] = answerIndex;

        RadioButton selectedRadioButton = answerRadioButtons[answerIndex];
        selectedRadioButton.setChecked(true);

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(true);
    }


    private void goToPreviousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            setQuestionAndAnswers();
            progressBar.setProgress(currentQuestionIndex + 1);
        }
    }

    private void goToNextQuestion() {
        clearRadioButtons();

        if (currentQuestionIndex < questions.length - 1) {
            currentQuestionIndex++;
            setQuestionAndAnswers();
            progressBar.setProgress(currentQuestionIndex + 1);
        } else {
            int score = calculateScore();
            pontuacaoAtingida = true;
            displayScore(score);
            VisibilityButtons();
        }

        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
    }

    private void VisibilityButtons() {
        Button btnBack = findViewById(R.id.btnBack);
        Button btnNext = findViewById(R.id.btnNext);
        Button btnPrevious = findViewById(R.id.btnPrevious);

        if (pontuacaoAtingida) {
            btnBack.setVisibility(View.VISIBLE);
            btnBack.setEnabled(true);
            btnNext.setVisibility(View.INVISIBLE);
            btnNext.setEnabled(false);
            btnPrevious.setVisibility(View.INVISIBLE);
            btnPrevious.setEnabled(false);
        } else {
            btnBack.setVisibility(View.INVISIBLE);
            btnBack.setEnabled(false);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setEnabled(true);
            btnPrevious.setVisibility(View.VISIBLE);
            btnPrevious.setEnabled(true);
        }
    }


    private void clearRadioButtons() {
        RadioButton firstAnswerRadioButton = findViewById(R.id.FirstAnswerRadioButton);
        RadioButton secondAnswerRadioButton = findViewById(R.id.SecondAnswerRadioButton);
        RadioButton thirdAnswerRadioButton = findViewById(R.id.ThirdAnswerRadioButton);
        RadioButton forthAnswerRadioButton = findViewById(R.id.ForthAnswerRadioButton);

        firstAnswerRadioButton.setChecked(false);
        secondAnswerRadioButton.setChecked(false);
        thirdAnswerRadioButton.setChecked(false);
        forthAnswerRadioButton.setChecked(false);
    }

    private int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.length; i++) {
            if (selectedAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }
        return score;
    }


    private void displayScore(int score) {
        String scoreMessage = getString(R.string.score_message, score, questions.length);
        textView.setText(scoreMessage);
    }
}