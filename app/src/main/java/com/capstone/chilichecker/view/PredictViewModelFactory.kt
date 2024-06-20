package com.capstone.chilichecker.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.chilichecker.data.repository.PredictRepository
import com.capstone.chilichecker.di.Injection
import com.capstone.chilichecker.view.detail.DetailViewModel
import com.capstone.chilichecker.view.scan.ScanViewModel

class PredictViewModelFactory(private val predictRepository: PredictRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(predictRepository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(predictRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        fun getInstance(context: Context) =
            PredictViewModelFactory(Injection.providePredictRepository(context))
    }
}