package com.example.gifs.ui.details


import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.gifs.R
import com.example.gifs.utils.setUpImage
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindDetails()
        goFullScreen()
    }

    private fun goFullScreen() {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun bindDetails() {
        gif.setUpImage(args.gif)
        title.text = args.gif.title
        if (!args.gif.username.isNullOrEmpty()) {
            username.apply {
                val userHandle = "@${args.gif.username}"
                text = userHandle
                visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}
