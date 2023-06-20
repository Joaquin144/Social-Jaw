package com.devcommop.myapplication.ui.components.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devcommop.myapplication.data.model.Post
import com.devcommop.myapplication.ui.components.homescreen.comments.CommentsScreen
import kotlinx.coroutines.launch

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PostItemWrapper(
//    post: Post = Post(),
//    onLikeButtonClick: () -> Unit = {},
//    onShareClick: () -> Unit = {}
//    //onCommentClick: () -> Unit = {}, --> not needed in wrapper
//) {
//    val bottomSheetState = rememberStandardBottomSheetState(
//        initialValue = SheetValue.Hidden,
//        skipHiddenState = false
//    )
//    val scaffoldState = rememberBottomSheetScaffoldState(
//        bottomSheetState = bottomSheetState
//    )
//    val scope = rememberCoroutineScope()
//    BottomSheetScaffold(scaffoldState = scaffoldState, sheetContent = {
//        CommentsScreen(
//            onClose = {
//                scope.launch {
//                    bottomSheetState.hide()
//                }
//            }//todo: toggle off the bottom sheet
//        )
//    }) {
////        Box(//todo: remove this test button
////            modifier = Modifier
////                .clip(RoundedCornerShape(percent = 50))
////                .background(Color.Cyan)
////                .clickable { scope.launch { bottomSheetState.expand() } }
////        )
//        PostItem(
//            post = post,
//            onLikeButtonClick = onLikeButtonClick,
//            onCommentClick = {
//                scope.launch { bottomSheetState.expand() }
//            },
//            onShareClick = onShareClick
//        )
//    }
//}