package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.ui.theme.ElectricCyanPrimary
import com.example.ui.theme.SurfaceContainerLowest

data class DrawnLine(
    val path: List<Offset>,
    val color: Color = ElectricCyanPrimary,
    val strokeWidth: Float = 5f
)

@Composable
fun ScratchpadCanvas(
    lines: List<DrawnLine>,
    onAddPointToCurrentLine: (Offset) -> Unit,
    onStartNewLine: (Offset) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerLowest)
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        onStartNewLine(offset)
                    },
                    onDrag = { change, _ ->
                        change.consume()
                        onAddPointToCurrentLine(change.position)
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw subtle blueprint grid dots
            val step = 24.dp.toPx()
            var x = step
            while (x < size.width) {
                var y = step
                while (y < size.height) {
                    drawCircle(
                        color = Color.White.copy(alpha = 0.05f),
                        radius = 1.2f,
                        center = Offset(x, y)
                    )
                    y += step
                }
                x += step
            }

            // Draw paths
            lines.forEach { line ->
                if (line.path.size > 1) {
                    val p = Path().apply {
                        moveTo(line.path.first().x, line.path.first().y)
                        for (i in 1 until line.path.size) {
                            lineTo(line.path[i].x, line.path[i].y)
                        }
                    }
                    drawPath(
                        path = p,
                        color = line.color,
                        style = Stroke(
                            width = line.strokeWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }
        }
    }
}
