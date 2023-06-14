package com.warriorsdev.dragonballsuper.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.wifi.SupplicantState
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.service.carrier.CarrierIdentifier
import android.service.carrier.CarrierService
import android.telephony.CarrierConfigManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.warriorsdev.dragonballsuper.data.character.CharacterDBS
import com.warriorsdev.dragonballsuper.databinding.ActivityMainBinding
import com.warriorsdev.dragonballsuper.ktx.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
        //setupViews()
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
            ),
            PackageManager.PERMISSION_GRANTED
        )


        getConfigPhone()
        binding.button.setOnClickListener {
            getConfigPhone()
        }
    }

    private fun getConfigPhone() {
        val dataInfo = ""
        val currentTimestamp = System.currentTimeMillis()
        val wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val connManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiInfo: WifiInfo = wifi.connectionInfo
        val telephony: TelephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        //val volte = telephony.isVolteAvailable()


        val mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mData = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        val ssid = if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
            wifiInfo.ssid
        } else {
            "No Data"
        }

        val volteCall = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SampleCarrierConfigService().onLoadConfig(CarrierIdentifier("","","","","",""))
        } else {
            TODO("VERSION.SDK_INT < M")
        }

        var networkType = if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        } else { "NO PERMISSION"}

        networkType = telephony.networkType.toString()
        val carrier = telephony.networkOperatorName

        binding.tvCharacter.text =
            dataInfo
                .plus("currentTimestamp: $currentTimestamp")

                .plus("\ncarrier: $carrier")
                .plus("\nnetworkType: $networkType")

                .plus("\nssid: $ssid")
                .plus("\nRoaming Activado: ${telephony.isNetworkRoaming}")
                //.plus("\nVoLTE Activado: ${telephony.isVolteAvailable()}")
                .plus("\nvolteCall: $volteCall")

                .plus("\nActivado Wifi: ${wifi.isWifiEnabled}")
                .plus("\nActivado MobileData: ${isOnMobileData()}")

                .plus("\nUSANDO TYPE_WIFI: ${mWifi?.isConnected}")
                .plus("\nUSANDO TYPE_MOBILE: ${mData?.isConnected}")

                .plus("\n")
                .plus("\n")
                .plus("\n")

    }

    private fun isOnMobileData(): Boolean {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val all = connectivityManager.allNetworks

        return all.any {
            val capabilities = connectivityManager.getNetworkCapabilities(it)
            capabilities?.hasTransport(TRANSPORT_CELLULAR) == true
        }
    }


    private fun setupViews() {
        showLoading()
        viewModel.getCharacter()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.state.observe(this, ::handle)
    }

    private fun handle(state: MainViewModel.State) {
        when (state) {
            is MainViewModel.State.ShowResults -> {
                showDataCurp(state.response)
            }
            else -> {
                showLoading()
            }
        }.exhaustive
    }

    private fun showLoading() {
        with(binding) {
            progressCircular.container.visible()
        }
    }

    private fun hideLoading() {
        with(binding) {
            progressCircular.container.gone()
        }
    }

    private fun showDataCurp(response: List<CharacterDBS>) {
        var textCharacterDBS = ""
        response.forEach {
            textCharacterDBS += it.name.plus("\n")
            Log.d("Dentro al forEach", "showDataCurp: ${it.name}")
        }

        binding.tvCharacter.text = textCharacterDBS
        Log.d("Dentro al forEach", "showDataCurp: $textCharacterDBS")
        hideLoading()
    }

}


@RequiresApi(Build.VERSION_CODES.M)
open class SampleCarrierConfigService : CarrierService() {

    private val TAG = "SampleCarrierConfigService"

    override fun onLoadConfig(isasasasd: CarrierIdentifier?): PersistableBundle {
        val config = PersistableBundle()
        config.putBoolean(
            CarrierConfigManager.KEY_CARRIER_VOLTE_AVAILABLE_BOOL, true
        )
        config.putBoolean(
            CarrierConfigManager.KEY_CARRIER_VOLTE_TTY_SUPPORTED_BOOL, false
        )
        config.putInt(CarrierConfigManager.KEY_VOLTE_REPLACEMENT_RAT_INT, 6)
        // Check CarrierIdentifier and add more config if needed…
        return config
    }

}