package com.janaushadhi.finder.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.janaushadhi.finder.R
import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.databinding.FragmentDetailBinding
import com.janaushadhi.finder.viewmodel.DetailViewModel
import com.janaushadhi.finder.viewmodel.MyMedicinesViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val myMedsVM: MyMedicinesViewModel by viewModels()

    // Fragment-level field: not re-captured in stale lambdas on re-render
    private var qty: Int = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val medicineId = arguments?.getInt("medicine_id") ?: -1
        if (medicineId != -1) viewModel.loadMedicine(medicineId)

        binding.btnBack.setOnClickListener { findNavController().navigateUp() }

        viewModel.medicine.observe(viewLifecycleOwner) { med ->
            med ?: return@observe
            renderMedicine(med)
        }

        viewModel.genAiResult.observe(viewLifecycleOwner) { result ->
            result ?: return@observe
            binding.cardAi.visibility = View.VISIBLE
            binding.tvAiResult.text = result
        }

        viewModel.isAiLoading.observe(viewLifecycleOwner) { loading ->
            binding.aiProgress.visibility = if (loading) View.VISIBLE else View.GONE
        }

        myMedsVM.message.observe(viewLifecycleOwner) { msg ->
            msg ?: return@observe
            Snackbar.make(binding.root, msg, 2000).show()
            myMedsVM.clearMessage()
        }
    }

    private fun renderMedicine(med: Medicine) {
        binding.apply {
            tvMedIcon.text      = med.icon
            tvBrandName.text    = med.brand
            tvGenericBadge.text = "Generic: ${med.generic}"
            tvManufacturer.text = "by ${med.manufacturer} · ${med.salt}"
            tvBrandedPrice.text = "₹${med.brand_price.toInt()}"
            tvGenericPrice.text = "₹${med.generic_price.toInt()}"
            tvYouSave.text      = "₹${med.savingsAmount.toInt()} (${med.savingsPercent}%)"
            tvUses.text         = med.uses.ifBlank { "—" }

            // Savings calculator — uses fragment-level qty so +/- always reads the correct value
            tvQty.text = qty.toString()
            updateCalc(med, qty)

            btnQtyMinus.setOnClickListener {
                if (qty > 1) { qty--; tvQty.text = qty.toString(); updateCalc(med, qty) }
            }
            btnQtyPlus.setOnClickListener {
                if (qty < 30) { qty++; tvQty.text = qty.toString(); updateCalc(med, qty) }
            }

            btnFindStore.setOnClickListener {
                findNavController().navigate(R.id.action_detail_to_stores)
            }
            btnAddToMyMeds.setOnClickListener {
                val nextMonth = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                    .format(java.util.Calendar.getInstance().also {
                        it.add(java.util.Calendar.DAY_OF_YEAR, 30)
                    }.time)
                myMedsVM.addMed(med.brand, nextMonth, qty * 30)
            }
            btnAskAi.setOnClickListener {
                cardAi.visibility = View.VISIBLE
                tvAiResult.text   = "Asking Gemini AI…"
                viewModel.askGenAi(med.brand)
            }
        }
    }

    private fun updateCalc(med: Medicine, qty: Int) {
        binding.tvMonthlySaving.text = "₹${(med.savingsAmount * qty).toInt()}"
        binding.tvAnnualSaving.text  = "₹${(med.savingsAmount * qty * 12).toInt()}"
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
