package com.playMatch.ui.location.activity

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.playMatch.R
import com.playMatch.databinding.ActivityLocationBinding
import com.playMatch.ui.baseActivity.BaseActivity

class LocationActivity : BaseActivity(), OnMapReadyCallback, View.OnClickListener,
    GoogleMap.OnMarkerDragListener{
    private lateinit var binding: ActivityLocationBinding
    private  var map: GoogleMap?=null
    private var address: Address? = null
    private var fullAddress: String? = ""


    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            locationRequest()
        }
        initView()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.confirmLocation.setOnClickListener(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.uiSettings?.isZoomControlsEnabled = true
//        map?.setOnMarkerClickListener(this)
        googleMap.setOnMarkerDragListener(this)
        handleCameraMap(googleMap)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            } R.id.confirmLocation -> {
                onBackPressed()
            }
        }
    }

    internal fun setUpMap() {
        if (longitude!==null&&latitude!==null) {
    val latLngOrigin = LatLng(latitude!!, longitude!!) // Ayala

    this.map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOrigin, 18f))
            this.map!!.animateCamera(CameraUpdateFactory.zoomTo(18f))

            if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
    placeMarkerOnMyLocation(latLngOrigin)
}
    }

    private fun placeMarkerOnMyLocation(currentLatLong: LatLng) {
        map?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                currentLatLong,
                18f
            )
        )
        map?.addMarker(
            MarkerOptions().position(currentLatLong).title("I am here!") )
    }

    private fun getBitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
//    override fun onMarkerClick(p0: Marker): Boolean {
//        return false
//    }

    private fun handleCameraMap(googleMap: GoogleMap) {

        googleMap.setOnCameraMoveListener {
            map?.clear()

            val updatedLatLng = map?.cameraPosition?.target
            latitude = updatedLatLng?.latitude
            longitude = updatedLatLng?.longitude

            _latitude = latitude.toString()
            _longitude = longitude.toString()
            Log.e(TAG, "handleCameraMap: $_latitude $_longitude")
            placeMarkerOnMyLocation(updatedLatLng!!)
        }

        googleMap.setOnCameraIdleListener {
            runOnUiThread {
                try {
                        address = convertLatLngToAddress()
                        if (address != null)
                            fullAddress = address?.getAddressLine(0).toString()
                        binding?.fullAddress?.text = address?.getAddressLine(0).toString()
                        if (address?.locality.isNullOrEmpty()) {
                            binding?.address?.text = getString(R.string.unknown)
                        } else {
                            binding?.address?.text = address?.locality.toString()
                        }


                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
        // handle marker
    }

    override fun onMarkerDragStart(p0: Marker) {
        Log.e("cameraMove", "onMarkerDragStart: ${p0.position}")
    }

    override fun onMarkerDrag(p0: Marker) {
        Log.e("cameraMove", "onMarkerDrag: ${p0.position}")
    }

    override fun onMarkerDragEnd(p0: Marker) {
        Log.e("cameraMove", "onMarkerDragEnd: ${p0.position}")
    }


}