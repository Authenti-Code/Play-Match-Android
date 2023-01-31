package com.playMatch.ui.home.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityOnMapNearbyMatchesBinding
import com.playMatch.ui.baseActivity.BaseActivity

class OnMapNearbyMatchesActivity : BaseActivity(), View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    private lateinit var binding: ActivityOnMapNearbyMatchesBinding
    private  var map: GoogleMap?=null
    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityOnMapNearbyMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            locationRequest()

        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        initView()
    }

    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.menu.setOnClickListener(this)
        binding.filter.setOnClickListener(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.uiSettings?.isZoomControlsEnabled = true
        map?.setOnMarkerClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.menu -> {
            onBackPressed()
            }
            R.id.filter -> {
                CommonUtils.performIntent(this,FilterActivity::class.java)
            }
        }
    }

    internal fun setUpMap() {
        if (longitude!=null&&latitude!=null) {
            val latLngOrigin = LatLng(latitude!!, longitude!!) // Ayala

            this.map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOrigin, 20f))
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
                15f
            )
        )
        map?.addMarker(
            MarkerOptions().position(currentLatLong)
                .icon(
                    getBitmapDescriptorFromVector(this, R.drawable.ic_pin_team)
                ).title("Trojans")
        )
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
    override fun onMarkerClick(p0: Marker): Boolean {
        showBottomSheetShowInterest()
        return false
    }

    private fun showBottomSheetShowInterest() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_show_interest, null)
        val dialog = BottomSheetDialog(this@OnMapNearbyMatchesActivity, R.style.AppBottomSheetDialogTheme)
        val showInterest = view.findViewById<CardView>(R.id.showInterest)

        showInterest.setOnClickListener {
            dialog.dismiss()
        }
        val cancelBtn = view.findViewById<ImageView>(R.id.cancel)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.show()
    }
}