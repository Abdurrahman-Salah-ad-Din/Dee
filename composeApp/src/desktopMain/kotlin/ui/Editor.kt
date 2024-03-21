package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Scanner
import models.TokenType
import ui.theme.codeTextStyle
import ui.theme.consolasFontFamily
import ui.theme.dataTypeColor
import ui.theme.delimiterColor
import ui.theme.errorColor
import ui.theme.numericColor
import ui.theme.operatorColor
import ui.theme.outputColor
import ui.theme.stringLiteralColor
import ui.theme.unfocusedLineNumberTextStyle
import ui.theme.variableColor

@Composable
fun Editor(
    code: String,
    isRunning: Boolean,
    onValueChanged: (String) -> Unit,
    onRunClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(modifier = Modifier.weight(1f).padding(vertical = 4.dp).fillMaxSize()) {
                LazyColumn(Modifier.wrapContentHeight().padding(top = 4.dp)) {
                    items(code.lines().size) {
                        Text(
                            text = it.toString(),
                            modifier = Modifier.padding(start = 32.dp, end = 24.dp),
                            style = unfocusedLineNumberTextStyle
                        )
                    }
                }
                BasicTextField(
                    value = code,
                    onValueChange = onValueChanged,
                    modifier = Modifier.fillMaxSize().padding(4.dp),
                    textStyle = codeTextStyle,
                    visualTransformation = colorizeCode(),
                    cursorBrush = SolidColor(Color.White),
                )
            }
            AnimatedVisibility(visible = isRunning, Modifier.fillMaxWidth().weight(0.35f)) {
                Column {
                    Box(
                        modifier = Modifier.height(2.dp).fillMaxWidth()
                            .background(color = Color(0xFF3C3C4A))
                    )
                    Column(
                        modifier = Modifier.wrapContentHeight().padding(start = 28.dp, top = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Output", style = codeTextStyle, color = outputColor)
                        Box(
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .size(height = 2.dp, width = 16.dp).background(
                                    color = outputColor,
                                    shape = RoundedCornerShape(12.dp)
                                )
                        )
                    }
                    Text(
                        text = buildAnnotatedString {
                            Scanner(code).tokenize().forEach { token ->
                                withStyle(
                                    style = SpanStyle(
                                        color = outputColor,
                                        fontFamily = consolasFontFamily,
                                        fontWeight = FontWeight.Light,
                                        fontStyle = FontStyle.Normal,
                                        fontSize = 18.sp,
                                    )
                                ) {
                                    append(
                                        "${if (token.type == TokenType.WHITESPACE) "▼" else token.value.trim()} is ${
                                            token.type.toString().lowercase()
                                        }\n"
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(start = 28.dp, top = 12.dp)
                            .verticalScroll(rememberScrollState())
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = { onRunClick(isRunning) },
            modifier = Modifier.padding(bottom = 16.dp, end = 16.dp).align(Alignment.BottomEnd),
            containerColor = Color(0xFF1B1E23)
        ) {
            val icon = if (isRunning) Icons.Rounded.Stop else Icons.Rounded.PlayArrow
            Icon(
                icon,
                icon.name,
                tint = if (isRunning) Color(0xFFDF3939) else Color(0xFF98C6FF)
            )
        }
    }
}

@Composable
private fun colorizeCode(): VisualTransformation {
    return VisualTransformation { input ->
        val annotatedString = buildAnnotatedString {
            val tokens = Scanner(input.text).tokenize()
            println(tokens)
            for (token in tokens) {
                when (token.type) {
                    TokenType.KEYWORD -> withStyle(style = SpanStyle(color = dataTypeColor)) {
                        append(token.value)
                    }

                    TokenType.NUMBER -> withStyle(style = SpanStyle(color = numericColor)) {
                        append(token.value)
                    }

                    TokenType.STRING_LITERAL -> withStyle(style = SpanStyle(color = stringLiteralColor)) {
                        append(token.value)
                    }

                    TokenType.IDENTIFIER -> withStyle(style = SpanStyle(color = variableColor)) {
                        append(token.value)
                    }

                    TokenType.OPERATOR -> withStyle(style = SpanStyle(color = operatorColor)) {
                        append(token.value)
                    }

                    TokenType.PUNCTUATION -> withStyle(style = SpanStyle(color = delimiterColor)) {
                        append(token.value)
                    }

                    TokenType.UNKNOWN -> withStyle(style = SpanStyle(color = errorColor)) {
                        append(token.value)
                    }

                    else -> append(token.value)
                }
            }
        }
        TransformedText(annotatedString, OffsetMapping.Identity)
    }
}

@Preview
@Composable
fun EditorPreview() {
    Editor("Int main() {\n     Int x = 10;\n     return 0;\n}", false, {}, {})
}