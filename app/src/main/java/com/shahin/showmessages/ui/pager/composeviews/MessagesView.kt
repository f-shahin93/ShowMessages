package com.shahin.showmessages.ui.pager.composeviews

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.sp
import com.shahin.showmessages.R
import com.shahin.showmessages.datasource.model.Message
import com.shahin.showmessages.ui.pager.MessagesActions
import com.shahin.showmessages.ui.utils.LoadImageByUrl


@Composable
fun MessagesListView(
    messagesList: List<Message>,
    actions: MessagesActions,
) {
    val deleteSelectionItems = mutableListOf<String>()
    val isSelectionItemsDeleteAppeared = remember { mutableStateOf(false) }

    CheckEmptyListView(messagesList.isNullOrEmpty())
    MessageListView(messagesList, actions, deleteSelectionItems, isSelectionItemsDeleteAppeared)

    if (isSelectionItemsDeleteAppeared.value) {
        SelectionButtons(
            onCancelClicked = {
                isSelectionItemsDeleteAppeared.value = false
                deleteSelectionItems.removeAll(deleteSelectionItems)
            },
            onDeletedBtnClicked = {
                val delList = mutableListOf<String>()
                delList.addAll(deleteSelectionItems)
                actions.onDeleteItem.invoke(delList)
                isSelectionItemsDeleteAppeared.value = false
                deleteSelectionItems.removeAll(deleteSelectionItems)
            }
        )
    }

}

@Composable
private fun CheckEmptyListView(isEmpty: Boolean) {
    if (isEmpty) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_empty_list),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.message_empty_list),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun MessageListView(
    messagesList: List<Message>,
    actions: MessagesActions,
    deleteSelectionItems: MutableList<String>,
    isSelectionItemsDeleteAppeared: MutableState<Boolean>
) {
    LazyColumn(
        modifier = Modifier.padding(
            horizontal = dimensionResource(id = R.dimen.margin_medium),
            vertical = dimensionResource(id = R.dimen.margin_small)
        )
    ) {
        items(items = messagesList) { item ->
            ItemMessage(
                message = item,
                actions = actions,
                deleteSelectionItems = deleteSelectionItems,
                isSelectionItemsDeleteAppeared = isSelectionItemsDeleteAppeared
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ItemMessage(
    message: Message,
    actions: MessagesActions,
    deleteSelectionItems: MutableList<String>,
    isSelectionItemsDeleteAppeared: MutableState<Boolean>
) {
    val isExpanded = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CheckBoxItemView(
            isSelectionItemsDeleteAppeared = isSelectionItemsDeleteAppeared,
            deleteSelectionItems = deleteSelectionItems,
            message = message
        )

        Card(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.margin_small))
                .combinedClickable(
                    onLongClick = { isSelectionItemsDeleteAppeared.value = true },
                    onClick = {}
                ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_size_small)),
            backgroundColor = if (message.unread) {
                Color.White
            } else {
                colorResource(id = R.color.gray_color)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.margin_medium))
            ) {
                ShareAndSaveIcons(actions, message)
                TitleView(message)
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.margin_small)))
                DescriptionAndImageView(isExpanded, message)
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(id = R.color.divider_color)
                )
                BottomMessageItemView(message, isExpanded, actions)
            }
        }
    }

}

@Composable
private fun CheckBoxItemView(
    isSelectionItemsDeleteAppeared: MutableState<Boolean>,
    deleteSelectionItems: MutableList<String>,
    message: Message
) {
    val isChecked = remember { mutableStateOf(false) }
    if (isSelectionItemsDeleteAppeared.value) {
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                if (it) {
                    deleteSelectionItems.add(message.id)
                } else {
                    deleteSelectionItems.remove(message.id)
                }
                isChecked.value = it
            },
            colors = CheckboxDefaults.colors(colorResource(id = R.color.secondary_color_variant))
        )
    } else {
        isChecked.value = false
    }
}


@Composable
private fun ShareAndSaveIcons(
    actions: MessagesActions,
    message: Message
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = {
                actions.onShareClicked.invoke(message.title, message.description)
            },
            content = {
                Icon(imageVector = Icons.Outlined.Share, contentDescription = null)
            }
        )

        IconButton(onClick = { actions.onSaveClicked.invoke(message.id) }) {
            Icon(
                painter = painterResource(
                    id = if (message.saved) {
                        R.drawable.ic_bookmark_fill
                    } else {
                        R.drawable.ic_bookmark_outline
                    }
                ),
                tint = colorResource(
                    id = if (message.saved) {
                        R.color.secondary_color
                    } else {
                        R.color.black
                    }
                ),
                contentDescription = null
            )
        }
    }
}


@Composable
private fun TitleView(message: Message) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = message.title,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}


@Composable
private fun DescriptionAndImageView(
    isExpanded: MutableState<Boolean>,
    message: Message
) {
    if (isExpanded.value) {
        if (message.image.isNullOrEmpty().not()) {
            LoadImageByUrl(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.icon_size_120)),
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
                        .width(dimensionResource(id = R.dimen.icon_size_40))
                        .height(dimensionResource(id = R.dimen.icon_size_40)),
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
}


@Composable
private fun BottomMessageItemView(
    message: Message,
    isExpanded: MutableState<Boolean>,
    actions: MessagesActions
) {
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
                .size(dimensionResource(id = R.dimen.icon_size_24)),
            onClick = {
                /**
                 * Checking this condition,because if unread = false,
                 * the message changed position and view changed from sorted query room
                 */
                if (message.unread && isExpanded.value) {
                    isExpanded.value = isExpanded.value.not()
                    actions.onReadStatusUpdateItem.invoke(message.id)
                } else {
                    isExpanded.value = isExpanded.value.not()
                }
            },
            content = {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(
                            if (isExpanded.value) {
                                180f
                            } else {
                                0f
                            }
                        ),
                    painter = painterResource(id = R.drawable.ic_outline_expand_circle_down_24),
                    tint = colorResource(id = R.color.secondary_color_variant),
                    contentDescription = null
                )
            }
        )
    }
}


@Composable
private fun SelectionButtons(
    onDeletedBtnClicked: () -> Unit,
    onCancelClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimensionResource(id = R.dimen.margin_medium),
                end = dimensionResource(id = R.dimen.margin_medium),
                top = dimensionResource(id = R.dimen.margin_medium),
            ),
        horizontalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin_small)),
        verticalAlignment = Alignment.Bottom,
    ) {
        OutlinedButton(
            onClick = { onCancelClicked.invoke() },
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(
                horizontal = dimensionResource(id = R.dimen.margin_tiny),
                vertical = dimensionResource(id = R.dimen.margin_zero)
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.secondary_color)
            ),
            border = null,
            elevation = null,
        ) {
            Text(
                text = stringResource(id = R.string.label_cancel),
                color = Color.White,
                fontSize = 14.sp,
            )
        }
        OutlinedButton(
            onClick = { onDeletedBtnClicked.invoke() },
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(
                horizontal = dimensionResource(id = R.dimen.margin_tiny),
                vertical = dimensionResource(id = R.dimen.margin_zero)
            ),
            elevation = null,
            border = BorderStroke(
                width = dimensionResource(id = R.dimen.margin_one),
                color = colorResource(id = R.color.secondary_color)
            ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_size_tiny)),
        ) {
            Text(
                text = stringResource(id = R.string.label_delete),
                color = colorResource(id = R.color.secondary_color),
                fontSize = 14.sp,
            )
        }
    }
}
