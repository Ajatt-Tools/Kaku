package ca.fuwafuwa.kaku

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class StartFragment : Fragment()
{
    private lateinit var adView : AdView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val mainActivity = activity as MainActivity
        val rootView = inflater.inflate(R.layout.fragment_start, container, false)
        adView = rootView.findViewById(R.id.adView)
        val progressBar = rootView.findViewById<ProgressBar>(R.id.progress_bar)
        val supportText = rootView.findViewById<TextView>(R.id.support_text)

        if (MainService.IsRunning())
        {
            progressBar.isIndeterminate = false
            progressBar.progress = 100
            supportText.text = getString(R.string.support_text)
        }

        adView.adListener = object: AdListener()
        {
            override fun onAdLoaded()
            {
                super.onAdLoaded()
                mainActivity.startKakuService(progressBar, supportText)
            }

            override fun onAdFailedToLoad(p0: Int)
            {
                super.onAdFailedToLoad(p0)
                mainActivity.startKakuService(progressBar, supportText)
            }
        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        return rootView
    }

    override fun onResume()
    {
        super.onResume()

        if (!MainService.IsRunning())
        {
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }
    }

    companion object
    {
        fun newInstance() : StartFragment {
            return StartFragment()
        }
    }
}
