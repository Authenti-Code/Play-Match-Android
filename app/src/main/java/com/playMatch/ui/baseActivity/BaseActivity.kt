package com.playMatch.ui.baseActivity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.red
import android.hardware.display.DisplayManager
import android.location.*
import android.location.LocationRequest
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.create
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.playMatch.R
import com.playMatch.controller.constant.AppConstant
import com.playMatch.controller.enum.Generation
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.ui.home.activity.HomeActivity
import com.playMatch.ui.home.activity.OnMapNearbyMatchesActivity
import com.playMatch.ui.location.activity.LocationActivity
import java.io.File
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


@SuppressLint("SimpleDateFormat", "ObsoleteSdkInt", "InflateParams", "SetTextI18n")
@Suppress("DEPRECATION")
open class BaseActivity : AppCompatActivity() {

    var type: String? = ""

    internal var notifications: Boolean? = true


    val bundle = Bundle()
    internal var firebaseToken: String = ""



    internal var file: File? = null
    internal var uri: Uri? = null
    internal var mBitmapImage: Bitmap? = null

    private var destinationRoot: File? = null
    internal var PERMISSION_ALL = 4
    internal var PERMISSION_GALLERY = 5

    //strings
    internal var imageName: String? = ""

    private val permissionId = 2

    private var networkType: String = AppConstant.INTERNET_OFF


    companion object {
        //permission
        @RequiresApi(33)
        private val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        internal const val CAMERA_STORAGE_PERMISSION_ID = 2

        private const val PERMISSION_ID = 1
        private const val PERMISSION_ID_RESOLUTION_REQUIRED = 99
        private val TAG: String by lazy { BaseActivity::class.java.simpleName }
    }

    internal var latitude: Double? = null
    internal var longitude: Double? = null

    internal var _latitude: String? = null
    internal var _longitude: String? = null

    //    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: com.google.android.gms.location.LocationRequest? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var activity: Activity? = null


//    var apiService: ApiService? = null

//    init {
//
//        apiService = ApiClient().getClient().create(ApiService::class.java)
//    }

//    private var networkType: String = AppConstant.INTERNET_OFF
//    internal fun accessToken(): String {
//        Log.e(
//            "Access token---", prefData.getUserDetail(
//                this@BaseActivity,
//                prefData.KEY_BEARER_TOKEN
//            )?.token.toString()
//        )
//        return "Bearer" + " " + prefData.getUserDetail(
//            this@BaseActivity,
//            prefData.KEY_BEARER_TOKEN
//        )?.token.toString()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
//            ApplicationClass.firebaseToken
//            getFirebaseToken()
            locationCall()
//            areNotificationsEnabled()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @Throws(Exception::class)
    private fun locationCall() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = create()
        locationRequest?.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = 10000
        locationRequest?.fastestInterval = (10000 / 2).toLong()
    }


    @Suppress("DEPRECATION")
    fun removeStatusBarFullyBlackIcon() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT
        // this lines ensure only the status-bar to become transparent without affecting the nav-bar
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    fun openUrl(url: String, mActivity: Activity) {
        var mUrl = url
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            mUrl = "http://$url"

        }
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(mUrl)
        mActivity.startActivity(i)
    }

    override fun onResume() {
        super.onResume()
        Log.e("", "")
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        CommonUtils.backPress(this@BaseActivity)
    }


    private fun isScreenOn(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val dm = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
            for (display in dm.displays) {
                if (display.state == Display.STATE_ON) {
                    return true
                }
            }
            false
        } else {
            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
            pm.isScreenOn
        }
    }

//    private fun getFirebaseToken(): String {
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w(
//                    "firebase token exception",
//                    "Fetching FCM registration token failed",
//                    task.exception
//                )
//                return@OnCompleteListener
//            }
//            // Get new FCM registration token
//            firebaseToken = task.result
//            Log.e("firebase token", firebaseToken)
//        })
//        return firebaseToken
//    }


    internal fun isNetworkAvailable(): Boolean {
        return checkNetwork(this@BaseActivity)
    }

    internal fun showNetworkSpeedError() {
        if (networkType == AppConstant.INTERNET_SLOW) {
            showSnackBar(
                findViewById(R.id.rootView),
                getString(R.string.error_speed)
            )
        } else if (networkType == AppConstant.INTERNET_OFF) {
            showSnackBarInternetDisConnected(
                findViewById(R.id.rootView),
                getString(R.string.error_no_internet)
            )
        }
    }

    internal fun showSnackBarInternetDisConnected(view: View?, message: String?) {
        val snackBar: Snackbar = Snackbar.make(view!!, message.toString(), 3500)
        val snackBarView: View = snackBar.view

        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                this@BaseActivity,
                R.color.red_E65D50
            )
        )
        val textView =
            snackBarView.findViewById<View>(R.id.snackbar_text) as AppCompatTextView
        textView.setTextColor(Color.WHITE)
        textView.maxLines = 5

        snackBar.show()
    }

    internal fun showError(resultResponse: ResultResponse) {
        return when (resultResponse) {
            is ResultResponse.Error -> {
                showSnackBar(findViewById(R.id.rootView), resultResponse.error)
            }
            is ResultResponse.HttpErrors.ResourceForbidden -> {
                showSnackBar(
                    findViewById(R.id.rootView), getString(
                        R.string.resourceforbidden
                    )
                )
            }
            is ResultResponse.HttpErrors.ResourceNotFound -> {
                showSnackBar(
                    findViewById(R.id.rootView), getString(
                        R.string.resourceNotFound
                    )
                )
            }
            is ResultResponse.HttpErrors.InternalServerError -> {
                showSnackBar(
                    findViewById(R.id.rootView), getString(
                        R.string.internalServerError
                    )
                )
            }
            is ResultResponse.HttpErrors.BadGateWay -> {
                showSnackBar(
                    findViewById(R.id.rootView), getString(
                        R.string.badGateWay
                    )
                )
            }
            is ResultResponse.HttpErrors.ResourceRemoved -> {
                showSnackBar(
                    findViewById(R.id.rootView), getString(
                        R.string.resourceRemoved
                    )
                )
            }
            is ResultResponse.HttpErrors.RemovedResourceFound -> {
                showSnackBar(
                    findViewById(R.id.rootView), getString(
                        R.string.removedResourceFound
                    )
                )
            }
            is ResultResponse.NetworkException -> {

                showNetworkSpeedError()
                //showSnackBar(findViewById(R.id.rootView), getString(R.string.error_no_internet))
            }
            else -> {
                showSnackBar(
                    findViewById(R.id.rootView), getString(
                        R.string.something_went_wrong
                    )
                )

            }
        }
    }

    private fun checkNetwork(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities: Network = connectivityManager.activeNetwork ?: return false
        val actNw: NetworkCapabilities =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        val info: Int? = connectivityManager.activeNetworkInfo?.subtype
        if (info == null) {
            networkType = AppConstant.INTERNET_OFF
            result = false
        } else {
            val generation = getNetworkGeneration(info)
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    when (generation) {
                        Generation.`2G` -> {
                            result = false
                            networkType = AppConstant.INTERNET_SLOW
                        }
                        Generation.`3G` -> {
                            result = false
                            networkType = AppConstant.INTERNET_SLOW
                        }

                        Generation.`4G` -> {
                            result = true

                        }
                        Generation.`5G` -> {
                            result = true

                        }
                        null -> {
                            networkType = AppConstant.INTERNET_OFF
                            result = false
                        }
                    }
                    result
                }
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> {
                    Log.e("connectionType", "no internet connection")
                    networkType = AppConstant.INTERNET_OFF
                    false
                }
            }
        }
        if (result) {
            networkType = AppConstant.INTERNET_CONNECTED
        }
        return result
    }


    @Throws(Exception::class)
    internal fun convertLatLngToAddress(): Address? {
        /* var addresses: ArrayList<Address>? = ArrayList()
         try {
             val geoCoder = Geocoder(this@BaseActivity, Locale.ENGLISH)
             addresses = geoCoder.getFromLocation(latitude!!, latitude!!, 1) as ArrayList<Address>?
         } catch (e: IOException) {
             e.printStackTrace()
         }
         return addresses?.get(0)*/

        // Reverse-Geocoding starts
        var addresses: Address? = null
        try {
            val geoCoder = Geocoder(this@BaseActivity, Locale.ENGLISH)
            val addressList: MutableList<Address>? =
                latitude?.let { longitude?.let { it1 -> geoCoder.getFromLocation(it, it1, 1) } }

            // use your lat, long value here
            if (addressList != null && addressList.isNotEmpty()) {

                addresses = addressList[0]
            }
        } catch (e: IOException) {

            Toast.makeText(applicationContext, "Unable connect to Geocoder", Toast.LENGTH_LONG)
                .show()
            return null
        }
        return addresses

    }

    private fun getNetworkGeneration(info: Int?): Generation? {
        return when (info) {
            TelephonyManager.NETWORK_TYPE_UNKNOWN -> null
            TelephonyManager.NETWORK_TYPE_GPRS,
            TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA,
            TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN,
            TelephonyManager.NETWORK_TYPE_GSM -> Generation.`2G`

            TelephonyManager.NETWORK_TYPE_UMTS,
            TelephonyManager.NETWORK_TYPE_EVDO_0,
            TelephonyManager.NETWORK_TYPE_EVDO_A,
            TelephonyManager.NETWORK_TYPE_HSDPA,
            TelephonyManager.NETWORK_TYPE_HSUPA,
            TelephonyManager.NETWORK_TYPE_HSPA,
            TelephonyManager.NETWORK_TYPE_EVDO_B,
            TelephonyManager.NETWORK_TYPE_EHRPD,
            TelephonyManager.NETWORK_TYPE_HSPAP,
            TelephonyManager.NETWORK_TYPE_TD_SCDMA -> Generation.`3G`

            TelephonyManager.NETWORK_TYPE_LTE,
            TelephonyManager.NETWORK_TYPE_IWLAN -> Generation.`4G`

            TelephonyManager.NETWORK_TYPE_NR -> Generation.`5G`

            else -> null
        }
    }
    @SuppressLint("NewApi")
    fun checkPermissions(): Boolean {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            return true
//        }
//        return false
        for (permission in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(
                    this@BaseActivity, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    @RequiresApi(33)
    fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ),
//            permissionId
//        )
        ActivityCompat.requestPermissions(
            this, PERMISSIONS, PERMISSION_ID
        )
    }

    @Deprecated("Deprecated in Java")
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        getLastLocation(this@BaseActivity)
                        Log.e("", "")
                    }
                    Activity.RESULT_CANCELED -> {
                        try {
                            locationRequest()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
            }
            PERMISSION_ID_RESOLUTION_REQUIRED -> {
//                if (resultCode == Activity.RESULT_OK) getLastLocation(this@BaseActivity)
//                else finish()
            }
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permission1: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permission1, grantResults)
//
//    }


    @Throws(Exception::class)
    fun locationRequest() {
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true)
        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())


        result.addOnFailureListener {
            if (it is ResolvableApiException) {
                try { // Handle result in onActivityResult()
                    it.startResolutionForResult(
                        this, PERMISSION_ID_RESOLUTION_REQUIRED
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }

        result.addOnCompleteListener {
            try {
                if (it.isSuccessful) {
                    getLastLocation(this@BaseActivity)
                }/* else {
                    Toast.makeText(this, "location not on ", Toast.LENGTH_SHORT).show()
                }*/
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->                             // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            val resolvable = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                this@BaseActivity, 100
                            )
                        } catch (e: IntentSender.SendIntentException) {

                            // Ignore the error.
                        } catch (e: ClassCastException) {
                        }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(activity: Activity) {
        // check if location is enabled
        if (isLocationEnabled()) {
            // getting last
            // location from
            // FusedLocationClient
            // object
            mFusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
                val location = task.result

                if (location == null) {
                    this.activity = activity
                    //requestNewLocationData()
                    getLastLocation(this@BaseActivity)
                } else {
                    latitude = location.latitude
                    longitude = location.longitude
                    Log.e("LiveLocationCurrent", "getLastLocation: $latitude $longitude")
                    Log.e("LiveLocationCurrent", "getLastLocation: $latitude $longitude")
                    when (activity) {
                        is OnMapNearbyMatchesActivity -> {
                            activity.setUpMap()
                        }
                        is LocationActivity -> {
                            activity.setUpMap()
                        }

                    }
                }
                this.activity = activity
                requestNewLocationData()
            }
        } else {
            locationRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            var isNotification = false
            var isLocation = false
            for (permission in permissions) {
                if (permission == Manifest.permission.POST_NOTIFICATIONS) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (grantResults.isNotEmpty() && grantResults[2] == PackageManager.PERMISSION_DENIED) {
                            isNotification = true
                        }
                    }

                } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (permission == Manifest.permission.ACCESS_FINE_LOCATION) {
                        locationRequest()
                    }

                } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    isLocation = true
                }
            }
            if (isNotification) {
//                bottomSheetEnableNotification()
            } else if (isLocation) {
//                bottomSheetEnableLocationService()
            }
        } else if (requestCode == PERMISSION_ALL) {
            openCamera()
        } else if (requestCode == PERMISSION_GALLERY) {
            openGallery()
        }
    }

    @SuppressLint("MissingPermission")
    @Throws(Exception::class)
    internal fun requestNewLocationData() {
        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient?.requestLocationUpdates(
            locationRequest!!, mLocationCallback, Looper.myLooper()!!
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            latitude = mLastLocation?.latitude
            longitude = mLastLocation?.longitude
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @Throws(java.lang.Exception::class)
    internal fun openCamera() {
        makeDirectory()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) == null) {
            return
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            val photoURI = FileProvider.getUriForFile(
                this,
                applicationContext.packageName + ".provider",
                file!!
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        }
//        startActivityForResult(intent, IntentConstant.REQUEST_CODE_CAMERA)
    }

    internal fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(galleryIntent, IntentConstant.REQUEST_CODE_GALLERY)
    }

    private fun makeDirectory() {
        destinationRoot = File(Environment.getExternalStorageDirectory().toString() + "/XAL")
        file = if (!destinationRoot!!.exists()) {
            destinationRoot!!.mkdirs()
            val currentTime = System.currentTimeMillis().toString()
            File(destinationRoot.toString() + "/" + currentTime + ".jpg")
        } else {
            val currentTime = System.currentTimeMillis().toString()
            File(destinationRoot.toString() + "/" + currentTime + ".jpg")
        }
        if (!file?.isFile!!) {
            // If Target API level is 29(Android 10),
            // you should access local path in scoped storage mode.
            val localStorage = getExternalFilesDir(null) ?: return
            val storagePath = localStorage.absolutePath
            val rootPath = "$storagePath/Saetae"
            val currentTime = System.currentTimeMillis().toString() + ".jpg"

            val root = File(rootPath)
            if (!root.mkdirs()) {
                Log.i("Test", "This path is already exist: " + root.absolutePath)
            }
            val fileJ = File(rootPath + currentTime)
            try {
                val permissionCheck = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    if (!fileJ.createNewFile()) {
                        Log.i("Test", "This file is already exist: " + fileJ.absolutePath)
                    }
                }
                file = fileJ
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    //todo crop bg_guest_home
    @Throws(java.lang.Exception::class)
    internal fun beginCrop(source: Uri?) {
        destinationRoot = File(Environment.getExternalStorageDirectory().absolutePath + "/Saetae")
        file = if (!destinationRoot!!.exists()) {
            destinationRoot!!.mkdirs()
            val currentTime = System.currentTimeMillis().toString() + ".jpg"
            File(destinationRoot.toString() + "/" + currentTime)
        } else {
            val currentTime = System.currentTimeMillis().toString() + ".jpg"
            File(destinationRoot.toString() + "/" + currentTime)
        }
        if (file?.isFile!!) {
//            Crop.of(source, Uri.fromFile(file)).asSquare().start(this)
//            Crop.of(source, Uri.fromFile(file)).withMaxSize(1240, 640).start(this)
//            Crop.of(source,output(source).withFixedSize(640, 640).start(this))
        } else {
            // If Target API level is 29(Android 10),
            // you should access local path in scoped storage mode.
            val localStorage = getExternalFilesDir(null) ?: return
            val storagePath = localStorage.absolutePath
            val rootPath = "$storagePath/Saetae"
            val currentTime = System.currentTimeMillis().toString() + ".jpg"

            val root = File(rootPath)
            if (!root.mkdirs()) {
                Log.i("Test", "This path is already exist: " + root.absolutePath)
            }

            val fileJ = File(rootPath + currentTime)
            try {
                val permissionCheck = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    if (!fileJ.createNewFile()) {
                        Log.i("Test", "This file is already exist: " + fileJ.absolutePath)
                    }
                }
                file = fileJ
//                Crop.of(source, Uri.fromFile(file)).asSquare().start(this)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

        }
    }

    @Throws(java.lang.Exception::class)
    internal fun cropImage(ImagePath: String) {
        try {
            mBitmapImage =
                MediaStore.Images.Media.getBitmap(this.contentResolver, Uri.parse(ImagePath))
//            val orientation = ExifUtil.getExifOrientation(this, Uri.parse(ImagePath))
//            mBitmapImage = ExifUtil.rotateBitmap(mBitmapImage!!, orientation)

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Throws(Exception::class)
//    private fun locationCall() {
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        locationRequest = LocationRequest.create()
//        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        locationRequest?.interval = 10000
//        locationRequest?.fastestInterval = (10000 / 2).toLong()
//    }

//    @Throws(Exception::class)
//    fun locationRequest() {
//        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
//        builder.setAlwaysShow(true)
//        val result: Task<LocationSettingsResponse> =
//            LocationServices.getSettingsClient(this).checkLocationSettings(builder.build())
//
//
//        result.addOnFailureListener {
//            if (it is ResolvableApiException) {
//                try { // Handle result in onActivityResult()
//                    it.startResolutionForResult(
//                        this, PERMISSION_ID_RESOLUTION_REQUIRED
//                    )
//                } catch (sendEx: IntentSender.SendIntentException) {
//                }
//            }
//        }
//
//        result.addOnCompleteListener {
//            try {
//                if (it.isSuccessful) {
//                    getLastLocation(this@BaseActivity)
//                }/* else {
//                    Toast.makeText(this, "location not on ", Toast.LENGTH_SHORT).show()
//                }*/
//            } catch (exception: ApiException) {
//                when (exception.statusCode) {
//                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->                             // Location settings are not satisfied. But could be fixed by showing the
//                        // user a dialog.
//                        try {
//                            // Cast to a resolvable exception.
//                            val resolvable = exception as ResolvableApiException
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the result in onActivityResult().
//                            resolvable.startResolutionForResult(
//                                this@BaseActivity, 100
//                            )
//                        } catch (e: IntentSender.SendIntentException) {
//
//                            // Ignore the error.
//                        } catch (e: ClassCastException) {
//                        }
//                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
//
//                    }
//                }
//            }
//        }
//    }

    @SuppressLint("MissingPermission")
//    fun getLastLocation(activity: Activity) {
//        // check if location is enabled
//        if (isLocationEnabled()) {
//            // getting last
//            // location from
//            // FusedLocationClient
//            // object
//            mFusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
//                val location = task.result
//
//                if (location == null) {
//                    this.activity = activity
//                    //requestNewLocationData()
//                    getLastLocation(this@BaseActivity)
//                } else {
//                    latitude = location.latitude
//                    longitude = location.longitude
//                    Log.e("LiveLocationCurrent", "getLastLocation: $latitude $longitude")
//                    Log.e("LiveLocationCurrent", "getLastLocation: $latitude $longitude")
//                    when (activity) {
//                        is HomeActivity -> {
//                            activity.getData()
//                        }
//
//                    }
//                }
//                this.activity = activity
//                requestNewLocationData()
//            }
//        } else {
//            locationRequest()
//        }
//    }

//    @SuppressLint("MissingPermission")
//    @Throws(Exception::class)
//    internal fun requestNewLocationData() {
//        // setting LocationRequest
//        // on FusedLocationClient
//        mFusedLocationClient?.requestLocationUpdates(
//            locationRequest!!, mLocationCallback, Looper.myLooper()!!
//        )
//    }

//    private val mLocationCallback: LocationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            val mLastLocation: Location? = locationResult.lastLocation
//            latitude = mLastLocation?.latitude
//            longitude = mLastLocation?.longitude
//        }
//    }

    fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


//    private fun getNetworkGeneration(info: Int?): Generation? {
//        return when (info) {
//            TelephonyManager.NETWORK_TYPE_UNKNOWN -> null
//            TelephonyManager.NETWORK_TYPE_GPRS,
//            TelephonyManager.NETWORK_TYPE_EDGE,
//            TelephonyManager.NETWORK_TYPE_CDMA,
//            TelephonyManager.NETWORK_TYPE_1xRTT,
//            TelephonyManager.NETWORK_TYPE_IDEN,
//            TelephonyManager.NETWORK_TYPE_GSM -> Generation.`2G`
//
//            TelephonyManager.NETWORK_TYPE_UMTS,
//            TelephonyManager.NETWORK_TYPE_EVDO_0,
//            TelephonyManager.NETWORK_TYPE_EVDO_A,
//            TelephonyManager.NETWORK_TYPE_HSDPA,
//            TelephonyManager.NETWORK_TYPE_HSUPA,
//            TelephonyManager.NETWORK_TYPE_HSPA,
//            TelephonyManager.NETWORK_TYPE_EVDO_B,
//            TelephonyManager.NETWORK_TYPE_EHRPD,
//            TelephonyManager.NETWORK_TYPE_HSPAP,
//            TelephonyManager.NETWORK_TYPE_TD_SCDMA -> Generation.`3G`
//
//            TelephonyManager.NETWORK_TYPE_LTE,
//            TelephonyManager.NETWORK_TYPE_IWLAN -> Generation.`4G`
//
//            TelephonyManager.NETWORK_TYPE_NR -> Generation.`5G`
//
//            else -> null
//        }
//    }

    @SuppressLint("InflateParams")
//    private fun bottomSheetEnableNotification() {
//        val bottomSheetDialog = BottomSheetDialog(
//            this@BaseActivity, R.style.AppBottomSheetDialogTheme
//        )
//        val view = layoutInflater.inflate(R.layout.bottom_sheet_notification, null)
//        bottomSheetDialog.setContentView(view)
//
//        val enableLocationCv: CardView = view.findViewById(R.id.enableLocationCv)
//        enableLocationCv.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            try {
//                //Open the specific App Info page:
//                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                intent.data = Uri.parse("package:$packageName")
//                startActivity(intent)
//            } catch (e: ActivityNotFoundException) {
//                //Open the generic Apps page:
//                val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
//                startActivity(intent)
//            }
//        }
//        bottomSheetDialog.show()
//    }



    /**
     * This method displays provided message on SnackBar
     *
     * @param view
     * @param message
     */
    internal fun showSnackBar(view: View?, message: String?) {
        val snackBar: Snackbar = Snackbar.make(view!!, message.toString(), 3500)
        val snackBarView: View = snackBar.view

        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                this@BaseActivity,
                R.color.red_E65D50
            )
        )
        val textView =
            snackBarView.findViewById<View>(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.maxLines = 5

        snackBar.show()
    }


//    internal fun showCheckBoxError(){
//        showSnackBar(findViewById(R.id.rootView), getString(R.string.error_checkbox))
//    }

//    internal fun showNetworkSpeedError() {
//        if (networkType == AppConstant.INTERNET_SLOW) {
//            showSnackBar(
//                findViewById(R.id.rootView),
//                getString(R.string.error_speed)
//            )
//        } else if (networkType == AppConstant.INTERNET_OFF) {
//            showSnackBarInternetDisConnected(
//                findViewById(R.id.rootView),
//                getString(R.string.error_no_internet)
//            )
//        }
//    }

//    @SuppressLint("InflateParams")
//    open fun showBottomSheetSearchBar(onItemClicked: AddressBookInterface) {
//        val view: View = layoutInflater.inflate(R.layout.bottom_search_bar, null)
//        val locationBottomSheet = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
//        // Fetching API_KEY which we wrapped
//        val apiKey =getString(R.string.api_key)
//
//        // Initializing the Places API
//        // with the help of our API_KEY
//        if (!Places.isInitialized()) {
//            Places.initialize(applicationContext, apiKey)
//        }
//        // Initialize Autocomplete Fragments
//        // from the main activity layout file
//        val autocompleteSupportFragment1 = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment2) as AutocompleteSupportFragment?
//
//
//        // Information that we wish to fetch after typing
//        // the location and clicking on one of the options
//        autocompleteSupportFragment1!!.setPlaceFields(
//            listOf(
//                Place.Field.NAME,
//                Place.Field.ADDRESS,
//                Place.Field.PHONE_NUMBER,
//                Place.Field.LAT_LNG,
//                Place.Field.OPENING_HOURS,
//                Place.Field.RATING,
//                Place.Field.USER_RATINGS_TOTAL
//            )
//        )
//
//        @Throws(Exception::class)
//         fun convertTimeToMoments(dateTime: String): String {
//            var convertedTime = ""
//            val suffix = "ago"
//            try {
//                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
//
//                val pasTime = dateFormat.parse(dateTime)
//
//                val nowTime = Date()
//
//                val dateDiff = nowTime.time - pasTime?.time!!
//
//                val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
//                val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
//                val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
//                //val day = TimeUnit.MILLISECONDS.toDays(dateDiff)
//                when {
//                    second < 60 -> {
//                        convertedTime = "a moment ago"
//                    }
//                    minute < 60 -> {
//
//                        convertedTime = if (minute.toInt() == 1) {
//                            "$minute minute $suffix"
//
//                        } else {
//                            "$minute minutes $suffix"
//
//                        }
//                    }
//                    hour < 24 -> {
//                        convertedTime = if (hour.toInt() == 1) {
//                            "$hour hr $suffix"
//                        } else {
//                            "$hour hrs $suffix"
//                        }
//
//                    }
//                    else -> {
//                        val df = SimpleDateFormat("MM/dd/yyyy hh:mm aaa", Locale.ENGLISH)
//                        convertedTime = df.format(pasTime)
//                    }
//                }
//            } catch (e: ParseException) {
//                e.printStackTrace()
//            }
//
//            return convertedTime
//        }
//        // Display the fetched information after clicking on one of the options
//        autocompleteSupportFragment1.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            @SuppressLint("SetTextI18n")
//            override fun onPlaceSelected(place: Place) {
//                Log.i(ContentValues.TAG, "Place: ${place.name}, ${place.id}")
//                address = place.address?.toString()
//
//                _latitude = place.latLng?.latitude.toString()
//                _longitude = place.latLng?.longitude.toString()
//
//                val latlong = "${place.latLng?.latitude!!}::${place.latLng?.longitude!!}"
//                val resultIntent = Intent()
//                resultIntent.putExtra("location", address)
//                resultIntent.putExtra("latlong", latlong)
//                this@BaseActivity.setResult(200, resultIntent)
//                LocationViewTypeEnum.LOCATION_TYPE.viewTypeEnum.toString()
//                locationBottomSheet.dismiss()
//                onItemClicked.addressSearchClick(place)
//            }
//            //
//            override fun onError(status: Status) {
//                Toast.makeText(applicationContext, "Some error occurred", Toast.LENGTH_SHORT).show()
//            }
//        })
//        locationBottomSheet.setContentView(view)
//        locationBottomSheet.show()
//        locationBottomSheet.setOnCancelListener {
//            if (autocompleteSupportFragment1 != null) {
//                supportFragmentManager.beginTransaction()
//                    .remove(autocompleteSupportFragment1)
//                    .commit()
//            }
//        }
//        locationBottomSheet.setCanceledOnTouchOutside(true)
//    }

//    internal fun setNetworkListener(listener: ConnectivityChangesReceiver.NetworkListener?) {
//        val receiver: ConnectivityChangesReceiver? = ConnectivityChangesReceiver.instance
//        if (listener != null) {
//            receiver?.addNetworkListener(listener)
//        }
//        if (!receiver?.isIsRegistered()!!) {
//            receiver.setIsRegistered(true)
//
//        }
//        val mReceiverRegisterIntent = registerReceiver(
//            receiver, IntentFilter(
//                ConnectivityManager.CONNECTIVITY_ACTION
//            )
//        )
//        ConnectivityChangesReceiver.setBroadcastIntent(mReceiverRegisterIntent)
//    }


//    @SuppressLint("InflateParams")
//    internal fun bottomSheetEnableLocationService() {
//        val bottomSheetDialog = BottomSheetDialog(
//            this@BaseActivity, R.style.AppBottomSheetDialogTheme
//        )
//        val view = layoutInflater.inflate(R.layout.bottom_sheet_location_permission, null)
//        bottomSheetDialog.setContentView(view)
//        bottomSheetDialog.behavior.isHideable = false
//        bottomSheetDialog.behavior.isDraggable = false
//        val enableLocationCv: CardView = view.findViewById(R.id.enableLocationCv)
//        enableLocationCv.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            try {
//                //Open the specific App Info page:
//                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                intent.data = Uri.parse("package:$packageName")
//                startActivity(intent)
//            } catch (e: ActivityNotFoundException) {
//                //Open the generic Apps page:
//                val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
//                startActivity(intent)
//            }
//        }
//        bottomSheetDialog.show()
//    }

    /**
     * this method is for get uuid
     *
     */
//    @SuppressLint("HardwareIds")
    fun getUuid(): String? {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }


    /**
     * This method displays provided message on SnackBar
     *
     * @param view
     * @param message
     */
    @SuppressLint("ShowToast")
//    internal fun showSnackBar(view: View?, message: String?) {
//        val snackBar: Snackbar = Snackbar.make(view!!, message.toString(), 3500)
//        val snackBarView: View = snackBar.view
//
//        snackBarView.setBackgroundColor(
//            ContextCompat.getColor(
//                this@BaseActivity,
//                R.color.orange_FF7F23
//            )
//        )
//        val textView =
//            snackBarView.findViewById<View>(R.id.snackbar_text) as TextView
//        textView.setTextColor(Color.WHITE)
//        textView.maxLines = 5
//
//        snackBar.show()
//    }

    /**
     * this method is validation for phone where digit should be 10
     *
     * @param phone
     */
    fun isValidPhoneNumber(phone: String): Boolean {
        return if (phone.trim { it <= ' ' } != "" && phone.length == 10) {
            Patterns.PHONE.matcher(phone).matches()
        } else false
    }

    /**
     * this method is validation for email is valid or not
     *
     * @param editText
     * @param activity
     */
//    fun validateEmail(editText: EditText?, activity: Activity): Boolean {
//        val email = editText?.text.toString().trim { it <= ' ' }
//
//        if (email.isEmpty() || !isValidEmail(email)) {
//            editText?.error = activity.resources.getString(R.string.error_email)
//            requestFocus(editText, activity)
//            return false
//        } else {
//            editText?.error = null
//        }
//        return true
//    }

    /**
     * this method is validation for email is valid or not
     *
     * @param email
     */
    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * this method is to request focus on edit text for validation purpose
     *
     * @param view
     * @param activity
     */
    fun requestFocus(view: View?, activity: Activity) {
        try {
            if (view?.requestFocus() == true) {
                activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
        } catch (ex: Exception) {
        }
    }

    @Throws(Exception::class)
    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return sdf.format(calendar.time)
    }

    @Throws(Exception::class)
    fun getCurrentDateTime(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        return sdf.format(calendar.time)
    }

    @Throws(Exception::class)
    fun getConvertTimeToTwelve(time: String): String {
        val displayFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        val parseFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val date = parseFormat.parse(time)!!
        return displayFormat.format(date)
    }

    @Throws(Exception::class)
    fun convertTimeToTwentyFour(time: String): String {
        val displayFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val parseFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        val date = parseFormat.parse(time)!!
        return displayFormat.format(date)
    }

    fun convertDateInMillis(date: String): Long {
        var timeInMilliseconds: Long = 0
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        try {
            val date1: Date? = sdf.parse(date)
            timeInMilliseconds = date1?.time!!
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timeInMilliseconds
    }

    fun convertCurrentDateInMillis(): Long {
        var timeInMilliseconds: Long = 0
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        try {
            val calendar = Calendar.getInstance()
            val sdf1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

            val date1: Date? = sdf.parse(sdf1.format(calendar.time))
            timeInMilliseconds = date1?.time!!
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timeInMilliseconds
    }

    fun getDateTimeFromMillis(milliSeconds: Long): String {
        // Create login DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
        // Create login calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    //time
    @Throws(Exception::class)
    fun convertTimeToMoments(dateTime: String): String {
        var convertedTime = ""
        val suffix = "ago"
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

            val pasTime = dateFormat.parse(dateTime)

            val nowTime = Date()

            val dateDiff = nowTime.time - pasTime?.time!!

            val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
            //val day = TimeUnit.MILLISECONDS.toDays(dateDiff)
            when {
                second < 60 -> {
                    convertedTime = "a moment ago"
                }
                minute < 60 -> {

                    convertedTime = if (minute.toInt() == 1) {
                        "$minute minute $suffix"

                    } else {
                        "$minute minutes $suffix"

                    }
                }
                hour < 24 -> {
                    convertedTime = if (hour.toInt() == 1) {
                        "$hour hr $suffix"
                    } else {
                        "$hour hrs $suffix"
                    }

                }
                else -> {
                    val df = SimpleDateFormat("MM/dd/yyyy hh:mm aaa", Locale.ENGLISH)
                    convertedTime = df.format(pasTime)
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return convertedTime
    }

    /**
     * @param number is whats app number
     * */

    fun openWhatsApp(number: String, message: String) {
        try {
            val sendIntent = Intent("android.intent.action.MAIN")
            //sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, message)
            sendIntent.putExtra(
                "jid", "91" +
                        "$number@s.whatsapp.net"
            ) //phone number without "+" prefix
            sendIntent.setPackage("com.whatsapp")
            startActivity(sendIntent)
        } catch (e: java.lang.Exception) {
            Toast.makeText(this, "Whats App not installed in the device.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun showSnackBarToast(text: String?, view: Activity) {
        val snack: Snackbar =
            Snackbar.make(view.window.decorView.rootView, text!!, Snackbar.LENGTH_SHORT)
        val snackBarView = snack.view as Snackbar.SnackbarLayout
        snackBarView.translationY = -170f
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                this@BaseActivity,
                R.color.purple_200
            )
        )

//        val textView =
//            snackBarView.findViewById<TextView>(R.id.snackbar_text)
//        textView.setTextColor(Color.WHITE)
//        textView.maxLines = 5

        snack.show()
    }//SnackBar

    /**
     * This method is used to open phone dialer
     * @param number is phone number
     * */
    @Throws(Exception::class)
    fun openDialer(number: String) {
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")))
    }

    /**
     * This method is used to open phone dialer
     * @param latitude
     * @param longitude
     * both used to open google map app and show its place
     * */
    @SuppressLint("QueryPermissionsNeeded")
    fun openDirection(latitude: Double?, longitude: Double?) {
        val location = "geo:$latitude,$longitude"
        val gmmIntentUri = Uri.parse(location)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        }
    }

    fun createDirection(
        currentLat: Double?,
        currentLong: Double?,
        otherLat: Double?,
        otherLong: Double?
    ) {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "http://maps.google.com/maps?" +
                        "saddr=" + currentLat + "," + currentLong + "&daddr=" + otherLat + "," +
                        otherLong
            )
        )
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
        startActivity(intent)

    }

    /**
     *  share url
     * */
    open fun shareAppUrl(mActivity: Activity) {
        val appPackageName =
            mActivity.packageName // getPackageName() from Context or Activity object
        try {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            //var title = "Let me recommend you this application\n\n"
            val title = "Hey, Check out what's cooking on DailyChef\n" +
                    "\nhttps://play.google.com/store/apps/details?id=$appPackageName".trimIndent()

            i.putExtra(Intent.EXTRA_TEXT, title)
            mActivity.startActivity(Intent.createChooser(i, "Choose one"))
        } catch (e: java.lang.Exception) {
            //e.toString();
        }
    }

    open fun onNetworkChanged() {}
}





