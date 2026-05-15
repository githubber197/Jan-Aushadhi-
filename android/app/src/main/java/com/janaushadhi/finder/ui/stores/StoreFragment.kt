package com.janaushadhi.finder.ui.stores

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.janaushadhi.finder.databinding.FragmentStoresBinding
import com.janaushadhi.finder.utils.Constants
import com.janaushadhi.finder.viewmodel.StoreViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class StoreFragment : Fragment() {

    private var _binding: FragmentStoresBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StoreViewModel by viewModels()
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
        ) {
            fetchLocationAndStores()
        } else {
            viewModel.loadStores()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        _binding = FragmentStoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        setupMap()
        setupRecyclerView()
        observeViewModel()

        binding.btnRetry.setOnClickListener { checkLocationPermission() }
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) 
            == PackageManager.PERMISSION_GRANTED) {
            fetchLocationAndStores()
        } else {
            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocationAndStores() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val userPoint = GeoPoint(location.latitude, location.longitude)
                binding.mapView.controller.setCenter(userPoint)
                viewModel.loadStores(location.latitude, location.longitude)
            } else {
                viewModel.loadStores()
            }
        }.addOnFailureListener {
            viewModel.loadStores()
        }
    }

    private fun setupMap() {
        binding.mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK) // Reliable OpenStreetMap tiles
            setMultiTouchControls(true)
            controller.setZoom(Constants.DEFAULT_ZOOM)
            controller.setCenter(GeoPoint(Constants.DEFAULT_LAT, Constants.DEFAULT_LNG))
        }
    }

    private fun setupRecyclerView() {
        storeAdapter = StoreAdapter()
        binding.rvStores.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = storeAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.stores.observe(viewLifecycleOwner) { stores ->
            storeAdapter.submitList(stores)
            binding.mapView.overlays.clear()
            stores.forEach { store ->
                val lat = store.lat ?: return@forEach
                val lng = store.lng ?: return@forEach
                val marker = Marker(binding.mapView).apply {
                    position = GeoPoint(lat, lng)
                    title = store.name
                    snippet = store.fullAddress
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                binding.mapView.overlays.add(marker)
            }
            if (stores.isNotEmpty()) {
                val first = stores.first()
                binding.mapView.controller.animateTo(GeoPoint(first.lat ?: Constants.DEFAULT_LAT, first.lng ?: Constants.DEFAULT_LNG))
            }
            binding.mapView.invalidate()
            binding.tvNoStores.visibility = if (stores.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { err ->
            if (err != null) {
                binding.tvError.text = err
                binding.tvError.visibility = View.VISIBLE
                binding.btnRetry.visibility = View.VISIBLE
            } else {
                binding.tvError.visibility = View.GONE
                binding.btnRetry.visibility = View.GONE
            }
        }
    }

    override fun onResume() { super.onResume(); binding.mapView.onResume() }
    override fun onPause() { super.onPause(); binding.mapView.onPause() }
    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
