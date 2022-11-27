package com.lok.dev.omrchecker.navigator

interface FragmentNavigator {

    fun naviMainScreen(screen : MainScreen)

    fun naviOMRScreen(screen : OMRScreen)

}

sealed class MainScreen {


}

sealed class OMRScreen {
    object OMRInput : OMRScreen()
    object AnswerInput : OMRScreen()
    object ResultView : OMRScreen()
}