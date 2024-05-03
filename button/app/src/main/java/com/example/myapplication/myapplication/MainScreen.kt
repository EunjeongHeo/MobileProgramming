package com.example.myapplication.myapplication

import android.os.Parcelable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import kotlinx.android.parcel.Parcelize


@Composable
fun ImageWithSlot(imgId:Int, slotBtn:@Composable ()-> Unit) {
    Image(painter = painterResource(id = imgId),
        contentDescription = "이미지",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
        )
    slotBtn()
}

@Composable
fun ImageWithSlot(imgId:String, slotBtn:@Composable ()-> Unit) {
    AsyncImage(model = imgId,
        contentDescription = "이미지",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
        )
    slotBtn()
}


@Composable
fun ButtonWithIcon(counter:Int, onClick:()->Unit) {
    Button(onClick={onClick()}){
        Icon(imageVector = Icons.Default.Favorite,
            contentDescription = "하트",
            tint = Color.Red
        )
        if(counter>0)
            Text("$counter")
        else
            Text("Like")
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconWithBadge(counter:Int, onClick: () -> Unit) {
    BadgedBox(badge = { Badge {
        Text("$counter")
    }}) {
        Icon(imageVector = Icons.Default.Favorite,
            contentDescription = "하트",
            tint = Color.Red,
            modifier = Modifier.clickable { onClick() }
        )

    }
}

@Parcelize
data class ImgData(var img:Int, var counter:Int): Parcelable

data class ImgData2(var img:Int, var counter:Int){
    companion object {
        val imgid = "img"
        val imgcounter = "counter"
        val imgMapSaver = mapSaver(
            save = {mapOf(imgid to it.img, imgcounter to it.counter)},
            restore = { ImgData2(it[imgid] as Int, it[imgcounter] as Int) }
        )
        val imgListSaver = listSaver<ImgData2,Any>(
            save = { listOf(it.img, it.counter) },
            restore = { ImgData2(it[0] as Int, it[1] as Int)}
        )
    }
}


class ImgViewModel : ViewModel(){
    var imglist = mutableStateListOf<ImgData2>()
        private set
    init{
        imglist.add(ImgData2(R.drawable.img1, 10))
        imglist.add(ImgData2(R.drawable.img2, 20))
        imglist.add(ImgData2(R.drawable.img3, 30))
    }
    fun incrementCount(index:Int){
        imglist[index] = imglist[index].copy(counter = imglist[index].counter+1)
    }
}



@Composable
fun MainScreen(){

    val imgViewModel: ImgViewModel = viewModel()

    val context = LocalContext.current
    val imgid = context.resources.getIdentifier("img1", "drawable", context.packageName)

//    var img1 by rememberSaveable(stateSaver = ImgData2.imgListSaver) {
//        mutableStateOf(ImgData2(imgid,10))
//    }
//    var img2 by rememberSaveable(stateSaver = ImgData2.imgListSaver) {
//        mutableStateOf(ImgData2(R.drawable.img2,20))
//    }
//    var img3 by rememberSaveable(stateSaver = ImgData2.imgListSaver) {
//        mutableStateOf(ImgData2(R.drawable.img3,30))
//    }
//    var img4 by rememberSaveable(stateSaver = ImgData2.imgListSaver) {
//        mutableStateOf(ImgData2(R.drawable.img4,40))
//    }
//    var counter5 by remember {
//        mutableStateOf(100)
//    }

    val scrollState = rememberScrollState()




    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ImageWithSlot(imgId = imgViewModel.imglist[0].img){
            ButtonWithIcon(counter = imgViewModel.imglist[0].counter) {
                imgViewModel.incrementCount(0)
            }
        }
        ImageWithSlot(imgId = imgViewModel.imglist[1].img){
            IconWithBadge(counter = imgViewModel.imglist[1].counter) {
                imgViewModel.incrementCount(1)
            }
        }
        ImageWithSlot(imgId = imgViewModel.imglist[2].img){
            ButtonWithIcon(counter = imgViewModel.imglist[2].counter) {
                imgViewModel.incrementCount(2)
            }
        }

//        ImageWithSlot(imgId = img1.img){
//            ButtonWithIcon(counter = img1.counter) {
//                //img1.counter++
//                img1 = img1.copy(counter = img1.counter+1)
//            }
//        }
//        ImageWithSlot(imgId = img2.img){
//            IconWithBadge(counter = img2.counter) {
//                //img2.counter++
//                img2 = img2.copy(counter = img2.counter+1)
//
//            }
//        }
//        ImageWithSlot(imgId = img3.img){
//            ButtonWithIcon(counter = img3.counter) {
//                //img3.counter++
//                img3 = img3.copy(counter = img3.counter+1)
//
//            }
//        }
//        ImageWithSlot(imgId = img4.img){
//            IconWithBadge(counter = img4.counter) {
//                //img4.counter++
//                img4 = img4.copy(counter = img4.counter+1)
//            }
//        }
//        ImageWithSlot(imgId = "https://cdn.lcnews.co.kr/news/photo/202402/69994_77505_2214.jpg"){
//            IconWithBadge(counter = counter5) {
//                counter5++
//            }
//        }
    }
}