package com.kkk.unitytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.UnityBannerSize

class MainActivity : AppCompatActivity() {

    companion object {

        private const val UNITY_GAME_ID = "4471331"
        private const val INTERSTITIAL_ID = "Interstitial_Android"
        private const val REWARDED_ID = "Rewarded_Android"
        private const val BANNER_ID = "Banner_Android"

    }

    private val frameLayoutAdContainer : FrameLayout by lazy {
        findViewById(R.id.frameLayoutAdContainer)

    }

    private val textViewCloseBanner : TextView by lazy {
        findViewById(R.id.textViewCloseBanner)

    }


    private var isTestMode = true
    private var isFullScreenAdShown = false

    private var bannerView  : BannerView? = null
    private var isBannerAdShown = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListener()

        UnityAds.initialize(applicationContext, UNITY_GAME_ID,isTestMode)

        UnityAds.addListener(object : IUnityAdsListener {
            override fun onUnityAdsReady(p0: String?) {

                when(p0){

                    INTERSTITIAL_ID -> {

                        //showAd(INTERSTITIAL_ID)
                    }
                    REWARDED_ID -> {

                       // showAd(REWARDED_ID)
                    }

                    BANNER_ID -> {
                      //  showAd(BANNER_ID)
                    }
                }

            }

            override fun onUnityAdsStart(p0: String?) {

            }

            override fun onUnityAdsFinish(p0: String?, p1: UnityAds.FinishState?) {

                when(p1){
                    UnityAds.FinishState.COMPLETED -> {
                        Toast.makeText(this@MainActivity, "completed", Toast.LENGTH_SHORT).show()
                    }
                    UnityAds.FinishState.SKIPPED -> {
                        Toast.makeText(this@MainActivity, "skipped", Toast.LENGTH_SHORT).show()
                    }
                    UnityAds.FinishState.ERROR -> {
                        Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()

                    }
                }

            }
            override fun onUnityAdsError(p0: UnityAds.UnityAdsError?, p1: String?) {
            }
        })




    }

    private fun setListener(){

        findViewById<Button>(R.id.buttonInterstitial).setOnClickListener {
            isFullScreenAdShown = false
            showAd(INTERSTITIAL_ID)
        }

        findViewById<Button>(R.id.buttonRewarded).setOnClickListener {
            isFullScreenAdShown = false
            showAd(REWARDED_ID)
        }

        findViewById<Button>(R.id.buttonBanner).setOnClickListener {
            showAd(BANNER_ID)
        }

        findViewById<TextView>(R.id.textViewCloseBanner).setOnClickListener{
            bannerView?.let {
                frameLayoutAdContainer.removeView(it)
                frameLayoutAdContainer.visibility = View.GONE
                bannerView = null
                isBannerAdShown = false


            }
        }


    }


    private fun showAd(adId :String){

        if(INTERSTITIAL_ID == adId || REWARDED_ID==adId){
            if(isFullScreenAdShown) {
                return
            }

            isFullScreenAdShown ==true

            UnityAds.show(this, adId, object : IUnityAdsShowListener {
                override fun onUnityAdsShowFailure(
                    p0: String?,
                    p1: UnityAds.UnityAdsShowError?,
                    p2: String?
                ) {

                }

                override fun onUnityAdsShowStart(p0: String?) {

                }

                override fun onUnityAdsShowClick(p0: String?) {

                }

                override fun onUnityAdsShowComplete(
                    p0: String?,
                    p1: UnityAds.UnityAdsShowCompletionState?
                ) {

                }

            })

        }
        else {


            if(isBannerAdShown) {
                return
            }

            isBannerAdShown = true

            bannerView = BannerView(this,adId, UnityBannerSize(320,50))

            bannerView!!.listener=object : BannerView.IListener{
                override fun onBannerLoaded(p0: BannerView?) {

                }

                override fun onBannerClick(p0: BannerView?) {

                }

                override fun onBannerFailedToLoad(p0: BannerView?, p1: BannerErrorInfo?) {

                }

                override fun onBannerLeftApplication(p0: BannerView?) {

                }

            }

            bannerView!!.load()

            frameLayoutAdContainer.addView(bannerView!!,0)
            frameLayoutAdContainer.visibility = View.VISIBLE

        }
    }


}