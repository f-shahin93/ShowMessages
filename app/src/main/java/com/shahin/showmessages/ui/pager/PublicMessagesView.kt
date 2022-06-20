package com.shahin.showmessages.ui.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahin.showmessages.R
import com.shahin.showmessages.datasource.model.Message
import com.shahin.showmessages.ui.utils.LoadImageByUrl


@Composable
fun PublicMessages(
    messagesList: List<Message>,
    onShareClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(
            horizontal = dimensionResource(id = R.dimen.margin_medium),
            vertical = dimensionResource(id = R.dimen.margin_small)
        )
    ) {
        items(items = messagesList) { item ->
            ItemMessage(item, onShareClicked = onShareClicked, onSaveClicked = onSaveClicked)
        }
    }

}

@Composable
private fun ItemMessage(
    message: Message,
    onShareClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {
    val isExpanded = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin_small)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_size_small)),
        backgroundColor = if (message.unread) {
            colorResource(id = R.color.gray_color)
        } else {
            Color.White
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.margin_medium))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onShareClicked.invoke() }) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = null
                    )
                }
                IconButton(onClick = { onSaveClicked.invoke() }) {
                    Icon(
                        painter = painterResource(
                            id = if (message.saved) {
                                R.drawable.ic_bookmark_fill
                            } else {
                                R.drawable.ic_bookmark_outline
                            }
                        ),
                        contentDescription = null
                    )
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = message.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.margin_small)))

            if (isExpanded.value) {
                if (message.image.isNullOrEmpty().not()) {
                    LoadImageByUrl(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        url = message.image!!
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_small)))
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = message.description,
                    textAlign = TextAlign.Justify,
                    fontSize = 14.sp
                )
            } else {
                Row {
                    if (message.image.isNullOrEmpty().not()) {
                        LoadImageByUrl(
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp),
                            url = message.image!!
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.margin_small)))
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = message.description,
                        textAlign = TextAlign.Justify,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .padding(vertical = dimensionResource(id = R.dimen.margin_small))
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.margin_very_tiny))
                    .background(color = colorResource(id = R.color.divider_color))
            )
            Row(
                modifier = Modifier
                    .padding(
                        bottom = dimensionResource(id = R.dimen.margin_medium),
                        top = dimensionResource(id = R.dimen.margin_small)
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.message_validity),
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.gray_text_color)
                )
                Text(
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(end = dimensionResource(id = R.dimen.margin_small)),
                    text = "10:22 - 1401/03/30",
                    textAlign = TextAlign.End,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.gray_text_color)
                )
                IconButton(
                    modifier = Modifier
                        .weight(0.25f)
                        .size(24.dp),
                    onClick = {
                        isExpanded.value = isExpanded.value.not()
                    },
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize().rotate(
                            if (isExpanded.value){
                                180f
                            }else{
                                0f
                            }
                        ),
                        painter = painterResource(id = R.drawable.ic_outline_expand_circle_down_24),
                        tint = colorResource(id = R.color.secondary_color_variant),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
