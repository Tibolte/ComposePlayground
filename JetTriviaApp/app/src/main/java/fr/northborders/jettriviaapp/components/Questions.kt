package fr.northborders.jettriviaapp.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.northborders.jettriviaapp.data.QuestionItem
import fr.northborders.jettriviaapp.screens.QuestionsViewModel
import fr.northborders.jettriviaapp.util.AppColors
import java.lang.Exception

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()

    val questionIndex = remember {
        mutableStateOf(0)
    }

    if (viewModel.data.value.loading == true) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
        Log.d("MainActivity", "Questions loading...")
    } else {
//        questions?.forEach {
//            Log.d("MainActivity", "Questions: $it")
//        }
        val question = try {
            questions?.get(questionIndex.value)
        } catch (ex: Exception) {
            null
        }
        if (questions != null) {
            QuestionsDisplay(question = question!!, questionIndex = questionIndex,
                viewModel = viewModel,
                onNextClick = {
                    questionIndex.value = questionIndex.value + 1
                })
        }
    }
}

//@Preview
@Composable
fun QuestionsDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionsViewModel,
    onNextClick: (Int) -> Unit
) {

    val choiceState = remember(question) {
        question.choices.toMutableList()
    }
    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choiceState[it] == question.answer
        }
    }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(), color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            if (questionIndex.value >= 3) {
                ShowProgress(score = questionIndex.value)
            }
            QuestionTracker(counter = questionIndex.value, viewModel.data.value.data?.size ?: 0)
            DottedLine(pathEffect = pathEffect)
            Column {
                Text(
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    text = question.question,
                    color = AppColors.mOffWhite,
                    fontSize = 17.sp, fontWeight = FontWeight.Bold, lineHeight = 22.sp
                )
                // Choices
                choiceState.forEachIndexed { index, s ->
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 4.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mOffDarkPurple,
                                        AppColors.mOffDarkPurple
                                    )
                                ), shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topEndPercent = 50,
                                    topStartPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = CenterVertically
                    ) {
                        RadioButton(
                            selected = (answerState.value == index),
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (correctAnswerState.value == true && index == answerState.value) {
                                    Color.Green.copy(alpha = 0.2f)
                                } else {
                                    Color.Red.copy(alpha = 0.2f)
                                }
                            )
                        )
                        val annotatedString = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Light,
                                color = if (correctAnswerState.value == true && index == answerState.value) {
                                    Color.Green
                                } else if (correctAnswerState.value == false && index == answerState.value) {
                                    Color.Red
                                } else {
                                    AppColors.mOffWhite
                                },
                                fontSize = 17.sp)){

                                append(s)
                            }
                        }
                        Text(text = annotatedString, modifier = Modifier.padding(6.dp))
                    }
                }
                Button(
                    onClick = { onNextClick(questionIndex.value) },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppColors.mLightBlue
                    )
                ) {
                    Text(
                        text = "Next",
                        modifier = Modifier.padding(4.dp),
                        color = AppColors.mOffWhite,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DottedLine(pathEffect: PathEffect) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp), onDraw = {
        drawLine(
            color = AppColors.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f), pathEffect = pathEffect
        )
    })
}

@Preview
@Composable
fun QuestionTracker(
    counter: Int = 10,
    outOf: Int = 100
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    )
                ) {
                    append("Question $counter/")
                    withStyle(
                        style = SpanStyle(
                            color = AppColors.mLightGray,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    ) {
                        append("$outOf")
                    }

                }
            }
        },
        modifier = Modifier.padding(20.dp)
    )
}

@Preview
@Composable
fun ShowProgress(score:Int = 12) {

    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075), Color(0xFFBE6BE5)))
    val progressFactor = remember(score) {
        mutableStateOf(score*0.005f)
    }
    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp, brush = Brush.linearGradient(
                colors = listOf(
                    AppColors.mLightPurple, AppColors.mLightPurple
                )
            ), shape = RoundedCornerShape(34.dp)
        )
        .clip(
            RoundedCornerShape(
                topEndPercent = 50,
                topStartPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50
            )
        )
        .background(color = Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            contentPadding = PaddingValues(1.dp),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(progressFactor.value)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            )) {
            Text(text = (score * 10).toString(),
                modifier = Modifier
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp)
                    .clip(RoundedCornerShape(23.dp)),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center)
        }
    }
}