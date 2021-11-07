package ru.donspb.skyslator.view.wordview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import coil.ImageLoader
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import ru.donspb.skyslator.Consts
import ru.donspb.skyslator.R
import ru.donspb.skyslator.databinding.FragmentMainBinding
import ru.donspb.skyslator.databinding.FragmentWordBinding

class WordFragment : Fragment() {

    private var binding: FragmentWordBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentWordBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.wordDescriptionHeader?.text = arguments?.getString(Consts.WORD_HEADER_KEY)
        binding?.wordDescriptionText?.text = arguments?.getString(Consts.WORD_DESCR_KEY)
        showImage(binding?.wordDescriptionImage, arguments?.getString(Consts.WORD_IMAGEURL_KEY))
    }

    fun showImage(imageView: ImageView?, imageLink: String?) {
        val request = LoadRequest.Builder(requireContext())
            .data("https:$imageLink")
            .target(
                onStart = {},
                onSuccess = { result -> imageView?.setImageDrawable(result) },
                onError = { imageView?.setImageResource(R.drawable.ic_no_image) }
            )
            .build()

        ImageLoader(requireContext()).execute(request)
    }

    companion object {
        fun newInstance(bundle: Bundle) =
            WordFragment().apply {
                arguments = bundle
            }
    }
}