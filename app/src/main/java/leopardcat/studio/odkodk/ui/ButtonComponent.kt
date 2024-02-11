package leopardcat.studio.odkodk.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import leopardcat.studio.odkodk.R
import leopardcat.studio.odkodk.domain.KyoboProduct
import leopardcat.studio.odkodk.ui.theme.BlackMain
import leopardcat.studio.odkodk.ui.theme.PinkMain


@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.ss_start),
    backgroundColor: Color = BlackMain,
    foregroundColor: Color = Color.White,
    elevation: ButtonElevation = ButtonDefaults.elevatedButtonElevation(0.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor
    ),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick, modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = elevation,
        colors = colors
    ) {
        Text(
            text = text, style = TextStyle(
                color = foregroundColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun KyoboButtonComponent(
    modifier: Modifier = Modifier,
    product: KyoboProduct,
    text: String = stringResource(id = R.string.ds_detail),
    backgroundColor: Color = Color.White,
    foregroundColor: Color = BlackMain,
    elevation: ButtonElevation = ButtonDefaults.elevatedButtonElevation(0.dp),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor
    ),
    onClick: () -> Unit
) {
    Button(
        onClick = onClick, modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = elevation,
        colors = colors
    ) {
        if(product.productId == null) {
            CircularProgressIndicator(
                color = PinkMain,
                strokeWidth = 5.dp,
                modifier = Modifier.size(30.dp)
            )
        } else {
            Text(
                text = text, style = TextStyle(
                    color = foregroundColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}